// Getdash application

define([
      'config',
      'app/getdash/getdash.conf',
      'app/plugins/datasource/influxdb/query_builder.js'
    ],
    function getDashApp (grafanaConf, getdashConf, InfluxQueryBuilder) {
  'use strict';

  // Helper Functions

  // log :: any -> any
  var log = function log (a) {
    console.log('DEBUG LOG: ', arguments);
    return a;
  };


  // isError :: valueAny -> Bool
  var isError = function isError (value) {
    return Object.prototype.toString.call(value) === '[object Error]';
  };


  // genRandomColor :: -> colorStr
  var genRandomColor = function genRandomColor () {
    return '#' + ('00' + (Math.random() * 4096 << 0).toString(16)).substr(-3);
  };


  // endsWith :: Str, targetStr -> Bool
  var endsWith = function endsWith (string, target) {
    var position = string.length - target.length;
    return position >= 0 && string.indexOf(target, position) === position;
  };


  // startsWith :: Str, targetStr -> Bool
  var startsWith = function startsWith (string, target) {
    return string.indexOf(target) === 0;
  };


  // startsOrEndsWith :: Str, targetStr -> Bool
  var startsOrEndsWith = function (string, target) {
    return startsWith(string, target) || endsWith(string, target);
  };


  // Variables
  var plugins = getdashConf.plugins;
  var datasourcesAll = grafanaConf.datasources;
  var datasources = _.filter(datasourcesAll, function (ds) {
    return !ds.grafanaDB && startsWith(ds.type, 'influxdb');
  });


  // Object prototypes
  var dashboardProto = {
    rows: [],  // [rowProto]
    services: {},
    time: {},  // dashboardTimeProto
    title: ''
  };

  var dashboardTimeProto = {
    from: 'now-6h',
    to: 'now'
  };

  var seriesProto = {
    source: '',
    name: ''
  };

  var metricProto = {
    name: '',
    plugin: '',
    pluginAlias: undefined,  // Str
    regexp: undefined,  // Str
    separator: '',
    graph: {},
    panel: {}
  };

  var pluginProto = {
    name: '',
    config: {},
    metrics: []  // [metricProto]
  };

  var fieldProto = {};

  var tagProto = {};

  var targetProto = {
    measurement: '',
    alias: '',
    fields: [],  // [fieldProto]
    tags: [],  // [tagProto]
    interval: '1m',
    query: '',
    groupBy: [
      {
        type: 'time',
        interval: 'auto'
      }
    ]
  };

  var panelProto = {
    title: 'default',
    height: '300px',
    type: 'graphite',
    span: 12,
    y_formats: [ 'none' ],
    grid: {
      max: null,
      min: 0,
      leftMin: 0
    },
    lines: true,
    fill: 1,
    linewidth: 1,
    nullPointMode: 'null',
    targets: [],  // [targetProto]
    aliasColors: {}
  };

  var rowProto = {
    title: 'default',
    height: '300px',
    panels: []  // [panelProto]
  };


  // Application Functions

  // seriesFilter :: metricConfObj, metricNameObj, seriesObj -> Bool
  var seriesFilter = _.curry(function seriesFilter (metricConf, metricName, series) {
    var type_instance = (_.isUndefined(series.type_instance) || _.isEmpty(series.type_instance))
        ? 'UNDEFINED'
        : series.type_instance;
    return startsWith(series.name, metricConf.plugin) && (startsOrEndsWith(series.name, metricName) ||
        startsOrEndsWith(type_instance, metricName)) && (_.isUndefined(metricConf.regexp) ||
        metricConf.regexp.test(series.instance));
  });


  // initMetric :: metricConfObj, metricNameStr -> metricObj
  var initMetric = function initMetric (metricConf, metricName) {
    return _.merge({}, metricProto, metricConf, { name: metricName });
  };


  // addProperty :: keyStr, valueAny, Obj -> new Obj{key: value}
  var addProperty = _.curry(function addProperty (k, v, obj) {
    var o = {};
    o[k] = v;
    return _.merge({}, obj, o);
  });


  // mergeSeries :: [Str], [seriesObj] -> mod [seriesObj]
  var mergeSeries = function (series, delKeys) {
    return _.uniq(_.map(series, function (s) {
      _.map(delKeys, function (k) {
        if (_.isUndefined(s[k]))
          return s;

        delete(s[k]);
        return s;
      });
      return s;
    }), JSON.stringify);
  };
  //console.assert(_.isEqual(
  //   mergeSeries([{
  //       source: 'ops',
  //       name: 'cpu_value',
  //       instance: '0',
  //       interval: '1m',
  //       host: 'vagrant-ubuntu-trusty-64',
  //       type: 'cpu',
  //       type_instance: 'system'
  //     }, {
  //       source: 'ops',
  //       name: 'cpu_value',
  //       instance: '1',
  //       interval: '1m',
  //       host: 'vagrant-ubuntu-trusty-64',
  //       type: 'cpu',
  //       type_instance: 'system'
  //     }], [ 'instance', 'type' ]),
  //   [{
  //     source: 'ops',
  //     name: 'cpu_value',
  //     interval: '1m',
  //     host: 'vagrant-ubuntu-trusty-64',
  //     type_instance: 'system'
  //   }]
  // ), "mergeSeries is broken.");


  // addSeriesToMetricGraphs :: seriesObj, metricConfObj -> new metricConfObj
  var addSeriesToMetricGraphs = _.curry(function addSeriesToMetricGraphs (series, metricConf) {
    // seriesThisFilter :: graphNameStr -> [seriesObj]
    var seriesThisFilter = seriesFilter(metricConf);
    var graphSeries = _.reduce(metricConf.graph, function (newConf, graphConf, graphName) {
      var matchedSeries = _.filter(series, seriesThisFilter(graphName));
      var readySeries = (_.isUndefined(metricConf.merge))
          ? matchedSeries
          : mergeSeries(matchedSeries, metricConf.merge);

      if (_.isEmpty(readySeries))
        return newConf;

      if (_.isArray(graphConf))
        newConf.graph[graphName] = _.map(_.range(graphConf.length), function () {
          return {
            series: readySeries
          };
        });
      else
        newConf.graph[graphName] = {
          series: readySeries
        };

      return newConf;
    }, { graph: {} });

    return _.merge({}, metricConf, graphSeries);
  });


  // moveUpToMetric :: keyStr, metricConfObj -> new metricConfObj
  var moveUpToMetric = _.curry(function moveUpToMetric (key, metricConf) {
    var keys = key + 's';
    var o = {};
    o[keys] =  _.union(_.flatten(_.map(metricConf.graph, function (graph) {
      var g = (_.isArray(graph))
          ? graph[0]
          : graph;
      return _.pluck(g.series, key);
    })));
    return _.merge({}, metricConf, o);
  });


  // addSourcesToMetric :: metricConfObj -> new metricConfObj
  var addSourcesToMetric = moveUpToMetric('source');


  // addInstancesToMetric :: metricConfObj -> new metricConfObj
  var addInstancesToMetric = moveUpToMetric('instance');


  // addHostsToMetric :: metricConfObj -> new metricConfObj
  var addHostsToMetric = moveUpToMetric('host');


  // getMetric :: [seriesObj], pluginObj -> func
  var getMetric = _.curry(function getMetric (series, plugin) {
    // :: metricConfObj, metricNameStr -> metricObj
    return _.compose(addHostsToMetric,
                     addInstancesToMetric,
                     addSourcesToMetric,
                     addSeriesToMetricGraphs(series),
                     addProperty('merge', plugin.config.merge),
                     addProperty('regexp', plugin.config.regexp),
                     addProperty('separator', plugin.config.separator),
                     addProperty('pluginAlias', plugin.config.alias),
                     addProperty('plugin', plugin.name),
                     initMetric);
  });


  // setupTarget :: metricConfObj, graphConfObj, metricStr, seriesObj -> targetObj
  var setupTarget = _.curry(function setupTarget (metricConf, graphConf, series) {
    var field = {
      name: graphConf.column || 'value',
      func: graphConf.apply || 'mean'
    };

    if (graphConf.math)
        field.mathExpr = graphConf.math;

    var tagObjs = _.omit(series, function (v, n) {
      return _.indexOf([ 'name', 'source', 'key' ], n) !== -1;
    });
    var tags = _.map(tagObjs, function (v, k) {
      return {
        condition: 'AND',
        key: k,
        value: v,
        operator: '='
      };
    });
    delete tags[0].condition;
    var target = {
      alias: (metricConf.pluginAlias || series.type || series.name) +
        (series.instance ? '.' + series.instance : '') + '.' +
        (graphConf.alias || series.type_instance || series.name || series.type),
      color: graphConf.color || genRandomColor(),
      measurement: series.name,
      fields: [ field ],
      tags: tags,
      interval: graphConf.interval
    };
    var readyTarget = _.merge({}, targetProto, target);

    // FIXME: I hate to do this… but sometimes you have to do what you have to do
    // in order to get what you want… and I really want my Grafana >=2.5.0 dash
    // to work with Influxdb version >=0.9.4. I promise to make it nice after
    // https://github.com/grafana/grafana/issues/2802 is fixed.
    if (graphConf.apply == 'derivative') {
        if (graphConf.math)
            delete readyTarget.fields[0].mathExpr;

        readyTarget.fill = 'none';
        var queryBuilder = InfluxQueryBuilder.prototype;
        queryBuilder.target = readyTarget;
        var rawQuery = queryBuilder._buildQuery();
        var rawQueryArr = rawQuery.split(' ');
        rawQueryArr[1] = 'derivative(mean(\"value\"), 1s)' + (graphConf.math || '');
        rawQuery = rawQueryArr.join(' ');
        readyTarget.query = rawQuery;
        readyTarget.rawQuery = 'true';
    }

    return readyTarget;
  });


  // transformObj :: keyKeyStr, valueKeyStr, {}, Obj -> Obj{key: value}
  var transformObj = _.curry(function transformObj (k, v, o, obj) {
    o[obj[k]] = obj[v];
    return o;
  });


  // setupAlias :: targetsObj -> aliasColorsObj
  var setupAlias = transformObj('alias', 'color');


  // initPanel :: metricConfObj, datasourceStr, instanceStr, hostnameStr -> panelObj
  var initPanel = _.curry(function initPanel (metricConf, datasource, instance, host) {
    if (_.isUndefined(datasource) || _.isUndefined(metricConf)) {
      return new Error('undefined argument in initPanel function.');
    }
    var panel = {
      datasource: datasource,
      config: {
        instance: instance,
        host: host,
        metric: metricConf
      }
    };

    return _.merge({}, panel, metricConf.panel);
  });


  // getTargetsForPanel :: panelObj, graphConfObj -> [targetObj]
  var getTargetsForPanel = _.curry(function getTargetsForPanel (panel, graphConf) {
    var grepBy = {
      source: panel.datasource,
      host: panel.config.host
    };
    if (!_.isUndefined(panel.config.instance)) {
      grepBy['instance'] = panel.config.instance;
    }
    if (!_.isUndefined(graphConf.type)) {
      grepBy['type'] = graphConf.type;
    }

    var graphSeries = _.where(graphConf.series, grepBy);
    if (_.isEmpty(graphSeries))
      return [];

    // setupThisTarget :: graphConfObj -> [targetObj]
    var setupThisTarget = setupTarget(panel.config.metric, graphConf);
    return _.map(graphSeries, setupThisTarget);
  });


  // addTargetsToPanel :: panelObj -> new panelObj
  var addTargetsToPanel = function addTargetsToPanel (panel) {
    if (isError(panel))
      return panel;

    // getTargetsForThisPanel :: graphConfObj -> [targetObj]
    var getTargetsForThisPanel = getTargetsForPanel(panel);
    var targets = _.flatten(_.map(panel.config.metric.graph, function (graphConf) {
      if (_.isArray(graphConf))
        return _.flatten(_.map(graphConf, getTargetsForThisPanel));

      return getTargetsForThisPanel(graphConf);
    }));

    if (_.isEmpty(targets))
      return new Error('targets for ' + panel.config.metric.plugin + '.' +
        panel.config.metric.plugin + ' are empty.');

    return _.merge({}, panel, {
      targets: targets
    });
  };


  // addAliasColorsToPanel :: panelObj -> new panelObj
  var addAliasColorsToPanel = function addAliasColorsToPanel (panel) {
    if (isError(panel))
      return panel;

    return _.merge({}, panel, {
      aliasColors: _.reduce(panel.targets, setupAlias, {})
    });
  };


  // addTitleToPanel :: panelObj -> new panelObj
  var addTitleToPanel = function addTitleToPanel (panel) {
    if (isError(panel))
      return panel;

    panel.title = panel.config.host + ': ' + panel.title;
    if (panel.title.match('@metric')) {
      var metric = (_.isUndefined(panel.config.instance))
          ? panel.config.metric.instances[0]
          : panel.config.instance;
      return _.merge({}, panel, {
        title: panel.title.replace('@metric', metric)
      });
    }
    return panel;
  };


  // setupPanel :: panelObj -> new panelObj
  var setupPanel = function setupPanel (panel) {
    if (isError(panel))
      return panel;

    var p = _.merge({}, panelProto, panel);
    delete p.config;  // no need in config after panel complected.
    return p;
  };


  // getPanel :: metricConfigObj, datasourceStr, instanceStr, hostnameStr -> panelObj
  var getPanel = _.compose(setupPanel,
                           addTitleToPanel,
                           addAliasColorsToPanel,
                           addTargetsToPanel,
                           initPanel);


  // setupRow :: [panelObj] -> rowObj
  var setupRow = function setupRow (panels) {
    if (_.isEmpty(panels))
      return [];

    return _.merge({}, rowProto, {
      title: ('title' in panels[0])
          ? panels[0].title.toUpperCase()
          : 'Default Title',
      panels: panels
    });
  };


  // stripErrorPanels :: [panelObj] -> new [panelObj]
  var stripErrorsFromPanels = function stripErrorsFromPanels (panels) {
    var errors = _.filter(panels, isError);
    if (!_.isEmpty(errors))
      _.map(errors, function (e) {
        console.warn(e.toString());
      });
    return _.reject(panels, isError);
  };


  // getPanelsForPlugin :: pluginObj -> [panelObj]
  var getPanelsForPlugin = function getPanelsForPlugin (plugin) {
    return _.flatten(_.map(plugin.metrics, getPanelsForMetric(plugin.config)));
  };


  // getDatasources :: pluginConfObj, metricConfObj -> [Str]
  var getDatasourcesForPanel = _.curry(function getDatasourcesForPanel (pluginConf, metricConf) {
    return (_.isArray(pluginConf.datasources) && !_.isEmpty(pluginConf.datasources))
        ? pluginConf.datasources
        : metricConf.sources;
  });


  // getInstances :: pluginConfObj, metricConfObj -> [Str]
  var getInstancesForPanel = _.curry(function getInstancesForPanel (pluginConf, metricConf) {
    return (_.has(pluginConf, 'multi') && pluginConf.multi)
        ? metricConf.instances
        : [ undefined ];
  });


  // getPanelsForMetric :: metricConfObj, [datasourceStr], [instanceStr] -> [panelObj]
  var getPanelsForMetric = _.curry(function getPanelsForMetric (pluginConf, metricConf) {
    var datasources = getDatasourcesForPanel(pluginConf, metricConf);
    var hosts = metricConf.hosts;

    if (_.isEmpty(datasources))
      return new Error('Datasources for ' + metricConf.plugin + '.' +
          metricConf.name + ' are empty.');

    var instances = getInstancesForPanel(pluginConf, metricConf);
    return _.flatten(_.map(hosts, function (host) {
      return _.map(datasources, function (source) {
        return _.map(instances, function (instance) {
          return getPanel(metricConf, source, instance, host);
        });
      });
    }));
  });


  // setupPlugin :: [seriesObject], pluginConfObj, pluginNameStr -> pluginObj
  var setupPlugin = _.curry(function setupPlugin (series, pluginConf, pluginName) {
    var plugin = {
      name: pluginName,
      config: pluginConf.config
    };
    plugin.metrics = _.map(pluginConf, getMetric(series, plugin));
    return _.merge({}, pluginProto, plugin);
  });


  // getRowsForPlugin :: [seriesObj] -> func
  var getRowsForPlugin = function getRowsForPlugin (series) {
    // curry doesn't work inside compose... probably lodash issue
    // :: pluginConfObj, pluginNameStr -> rowObj
    return _.compose(setupRow,
                     stripErrorsFromPanels,
                     getPanelsForPlugin,
                     setupPlugin(series));
  };


  // getRows :: seriesObj, pluginsObj -> [rowObj]
  var getRows = _.curry(function getRows (plugins, series) {
    return (_.isArray(series) && !_.isEmpty(series))
        ? _.flatten(_.map(plugins, getRowsForPlugin(series)))
        : [];
  });


  // getDBData :: [datasourcePointsObj] -> Promise -> [queryResultObj]
  var getDBData = function getDBData (dsQueries) {
    var gettingDBData = _.map(dsQueries, function (query) {
      return $.getJSON(query.url);
    });

    return Promise.all(gettingDBData);
  };


  // getQueriesForDDash :: [datasourcesObj], [queriesStr] -> [queryObj]
  var getQueriesForDDash = _.curry(function getQueriesForDDash (datasources, queries) {
    return _.flatten(_.map(datasources, function (ds) {
      return _.map(queries, function (query) {
        if (_.isUndefined(ds.database))
          return {
            datasource: ds.name,
            url: ds.url + '/query?q=' + encodeURIComponent('SHOW TAG VALUES WITH KEY = host;')
          };

        return {
          datasource: ds.name,
          url: ds.url + '/query?db=' + ds.database + '&u=' + ds.username + '&p=' + ds.password +
            '&q=' + encodeURIComponent('SHOW TAG VALUES WITH KEY = host;')
        };
      });
    }));
  });


  // setupDefaultDashboard :: [seriesObj], dashboardObj -> mod dashboardObj
  var setupDefaultDashboard = function setupDefaultDashboard (hostsAll, dashboard) {
    var hostsLinks = _.reduce(hostsAll, function (string, host) {
      return string + '\n\t\t\t<li>\n\t\t\t\t<a href="' +
        window.location.href + '?host=' + host +
        '" onclick="window.location.href=this.href;">' +
        host + '</a>\n\t\t\t</li>';
    }, '');

    var rowProto = {
      'title': 'Default',
      'height': '30px',
      'panels': [
        {
          'title': '',
          'span': 12,
          'type': 'text',
          'mode': 'html',
          'content': 'default'
        }
      ]
    };
/*
    var rowDocs = _.merge({}, rowProto, {
      'title': 'Docs',
      'panels': [
        {
          'content': '<div class="row-fluid">\n\t<div class="span12">\n\t\t<a href="https://github.com/anryko/grafana-influx-dashboard"><h4>Grafana InfluxDB Scripted Dashboard Documentation</h4></a>\n\t</div>\n</div>'
        }
      ]
    });

    var rowHosts = _.merge({}, rowProto, {
      'title': 'Hosts',
      'panels': [
        {
          'content': '<div class="row-fluid">\n\t<div class="span6">\n\t\t<h4>Available Hosts</h4>\n\t\t<ul>' + hostsLinks + '\n\t\t</ul>'
        }
      ]
    });

    dashboard.title = 'Scripted Dashboard';
    dashboard.rows = [
      rowHosts,
      rowDocs
    ];
    return dashboard;
  };
*/

    var rowHosts = _.merge({}, rowProto, {
      'title': 'Hosts',
      'panels': [
        {
          'content': '<div class="row-fluid">\n\t<div class="span6">\n\t\t<h4>Available Hosts</h4>\n\t\t<ul>' + hostsLinks + '\n\t\t</ul>'
        }
      ]
    });
    dashboard.title = 'Scripted Dashboard';
    dashboard.rows = [
      rowHosts
    ];
    return dashboard;
  };

  // parseTime :: timeStr -> [timeStr]
  var parseTime = function parseTime (time) {
    var regexpTime = /(\d+)(m|h|d)/;
    return regexpTime.exec(time);
  };


  // getDashboardTime :: timeStr -> dashboardTimeObj
  var getDashboardTime = function getDashboardTime (time) {
    if (!time || !parseTime(time))
      return _.merge({}, dashboardTimeProto);

    return _.merge({}, dashboardTimeProto, { from: 'now-' + time });
  };


  // getInterval :: timeStr -> intervalStr
  var getInterval = function getInterval (time) {
    var rTime = parseTime(time);
    if (!rTime)
      return '1m';

    var timeM = 0;
    if (rTime[2] === 'm')
      timeM = parseInt(rTime[1]);
    else if (rTime[2] === 'h')
      timeM = parseInt(rTime[1]) * 60;
    else if (rTime[2] === 'd')
      timeM = parseInt(rTime[1]) * 60 * 24;

    if (timeM > 360)
      return  (Math.ceil((Math.floor(timeM / 360) + 1) / 5) * 5).toString() + 'm';
    else if (timeM === 360)
      return '1m';
    else
      return '30s';
  };


  // getMetricArr :: pluginsObj, displayMetricStr -> [metricStr]
  var getMetricArr = _.curry(function getMetricArr (plugins, displayMetric) {
    if (!displayMetric)
      return _.keys(plugins);

    var displayMetrics = displayMetric.split(',');
    return _.uniq(_.reduce(displayMetrics, function (arr, metric) {
      if (metric in plugins) {
        arr.push(metric);
        return arr;
      } else if (metric in plugins.groups) {
        return _.union(arr, plugins.groups[metric]);
      }
    }, []));
  });


  // getQueryConfigs :: [datasourceObj], pluginsObj -> [queryConfigObj]
  var getQueryConfigs = _.curry(function getQueryConfigs (datasources, plugins) {
    var queryConfigsAll = _.map(plugins, function (plugin, name) {
      return {
        name: name,
        separator: plugin.config.separator,
        prefix: plugin.config.prefix,
        datasources: plugin.config.datasources || _.pluck(datasources, 'name')
      };
    });

    var qSeparator = '@SEPARATOR@';
    var queryConfigsGrouped = _.groupBy(queryConfigsAll, function (qConf) {
      return qConf.prefix + qSeparator +
        qConf.separator + qSeparator +
        qConf.datasources;
    });

    // TODO: remove prefix.
    return _.map(queryConfigsGrouped, function (qConf, pfxSepaDS) {
      var pfxSepaDSArr = pfxSepaDS.split(qSeparator);
      var prefix = pfxSepaDSArr[0];
      var separator = pfxSepaDSArr[1];
      var qDS = qConf[0].datasources;
      return {
        prefix: (prefix === 'undefined')
            ? undefined
            : prefix,
        separator: (separator === 'undefined')
            ? undefined
            : separator,
        regexp: '(' + _.pluck(qConf, 'name').join('|') + ')',
        datasources: _.flatten(_.map(qDS, function (ds) {
            return _.where(datasources, { name: ds });
          }))
      };
    });
  });


  // fixedEncodeURIComponent :: Str -> Str
  function fixedEncodeURIComponent (str) {
    return encodeURIComponent(str).replace(/[!'()*]/g, function(c) {
      return '%' + c.charCodeAt(0).toString(16);
    });
  }


  // getDSQueryArr :: hostNameStr, [queryConfigObj] -> [urlDatasourceObj]
  var getDSQueryArr = _.curry(function getDSQueryArr (hostName, queryConfigs) {
    var hostQuery = (!hostName)
                    ? ''
                    : 'WHERE host = \'' + hostName + '\'';

    return _.flatten(_.map(queryConfigs, function (qConf) {
      return _.map(qConf.datasources, function (ds) {
        if (_.isUndefined(ds.database))
          return {
            datasource: ds.name,
            url: ds.url + '/query?q=' + fixedEncodeURIComponent('SHOW SERIES FROM /' +
                qConf.regexp + '.*/ ' + hostQuery + ';')
          };

        return {
          datasource: ds.name,
          url: ds.url + '/query?db=' + ds.database + '&u=' + ds.username +
            '&p=' + ds.password + '&q=' + fixedEncodeURIComponent('SHOW SERIES FROM /' +
              qConf.regexp + '.*/ ' + hostQuery + ';')
        };
      });
    }));
  });


  // stripPlugins :: pluginsObj, [metricsStr] -> new pluginsObj
  var stripPlugins = _.curry(function stripPlugins (plugins, metrics) {
    var newPlugins = _.merge({}, plugins);
    // have to use this ugly thing because reduce will strip unenumerable 'config'
    _.each(_.keys(plugins), function (pluginName) {
      if (!_.contains(metrics, pluginName)) {
        delete newPlugins[pluginName];
      }
    });
    return newPlugins;
  });


  // getQueries :: hostNameStr, datasourcesObj, pluginsObj -> [queryStr]
  var getQueries = function getQueries (hostName, datasources, plugins) {
    return _.compose(getDSQueryArr(hostName), getQueryConfigs(datasources))(plugins);
  };


  // splitKey :: keyStr -> [keyPartStr]
  var splitKey = function splitKey (key) {
    return ('name=' + key).split(',');
  };
  // console.assert(_.isEqual(
  //   splitKey("load_longterm,host=vagrant,type=load"),
  //   [ "name=load_longterm", "host=vagrant", "type=load" ]
  // ));


  // objectKey :: [keyPartStr] -> keyObj
  var objectKey = function objectKey (keys) {
    return _.reduce(keys, function(result, expr) {
      var vars = expr.split('=');
      result[vars[0]] = vars[1];
      return result;
    }, {});
  };
  // console.assert(_.isEqual(
  //   objectKey([ "name=load_longterm", "host=vagrant", "type=load" ]),
  //   { host: "vagrant", name: "load_longterm", type: "load"}
  // ));


  // convertKey :: keyStr -> keyObj
  var convertKey = _.compose(objectKey, splitKey);
  //  console.assert(_.isEqual(
  //    convertKey("load_longterm,host=vagrant,type=load"),
  //    { host: "vagrant", name: "load_longterm", type: "load"}
  //  ));


  // setupSeries :: seriesObj -> new seriesObj
  var setupSeries = function (series) {
    return _.merge({}, seriesProto, series);
  };


  // getSeries :: keyStr -> seriesObj
  var getSeries = _.compose(setupSeries, convertKey);


  // pickPlugins :: pluginObj, metricsStr -> new pluginsObj
  var pickPlugins = function pickPlugins (plugins, metrics) {
    return _.compose(stripPlugins(plugins), getMetricArr(plugins))(metrics);
  };


  // parseResp :: [jsonObj] -> [Str]
  var parseResp = function parseResp (resp) {
    return _.map(resp, function (res) {
      var series = res.results[0].series;
      if (_.isUndefined(series))
        return;

      return _.pluck(series, 'values');
    });
  };


  // getDashboard :: [datasources], pluginsObj, dashConfObj,
  //                 grafanaCallbackFunc -> grafanaCallbackFunc(dashboardObj)
  var getDashboard = _.curry(function getDashboard (datasources, plugins, dashConf, callback) {
    var dashboard = {
      title: dashConf.title,
      time: getDashboardTime(dashConf.time)
    };

    if (!dashConf.host && !dashConf.metric) {
      var queriesForDDash = getQueriesForDDash(datasources, dashConf.defaultQueries);
      getDBData(queriesForDDash).then(function (resp) {
        var hosts = _.uniq(_.flatten(_.compact(parseResp(resp))));

        return callback(setupDefaultDashboard(hosts, dashboard));
      });
      return;
    }

    var dashPlugins = pickPlugins(plugins, dashConf.metric);
    var dashQueries = getQueries(dashConf.host, datasources, dashPlugins);

    getDBData(dashQueries).then(function (resp) {
      var datasources = _.pluck(dashQueries, 'datasource');
      var values = parseResp(resp);

      var keys = _.map(values, function (val) {
        if (_.isUndefined(val))
            return;

        return _.flatten(_.map(val, function (v) {
          return _.map(v, _.first);
        }));
      });

      var dsKeys = _.zip(datasources, keys);
      var series = _.flatten(_.map(dsKeys, function (dsKey) {
        var ds = dsKey[0];
        var kk = dsKey[1];
        return _.map(kk, function (k) {
          var series = {
            source: ds
          };
          return _.merge({}, getSeries(k), series);
        });
      }));

      // Object prototypes setup
      targetProto.interval = getInterval(dashConf.time);
      panelProto.span = dashConf.span;

      dashboard.rows = getRows(dashPlugins, series);
      return callback(dashboard);
    });
  });


  return {
    // get :: dashConfObj, grafanaCallbackFunc -> grafanaCallbackFunc(dashboardObj)
    get: getDashboard(datasources, plugins)
  };
});
