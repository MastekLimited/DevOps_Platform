var PROJECTS_TABLE_ID = "projects";
$(document).ready(function() {
	initialiseProjectsDatatable();
	showActiveMenu(NAVIGATION_LINK_MAP.PROJECT);
});

/*******************************************************************************
 * START: initialise datatable.
 ******************************************************************************/
$.fn.dataTable.ext.errMode = 'none';
function initialiseProjectsDatatable() {
	$('#' + PROJECTS_TABLE_ID).on('error.dt', function(e, settings, techNote, message) {
		handleAjaxError(e, settings, techNote, message);
	}).dataTable(
	{
		"sAjaxSource": "/project/projectsJSON",
		"sAjaxDataProp": "projects",
		"sServerMethod": "GET",
		"sAjaxDataProp": "",
		"bProcessing": true,
		"bDeferRender": true,
		"sPaginationType": "full_numbers",
		"aaSorting": [[0, 'asc']],
		"oLanguage":
		{
			"sLengthMenu": defaultDataTableConstants.sLengthMenu,
			"sInfo": defaultDataTableConstants.sInfo,
			"sInfoEmpty": defaultDataTableConstants.sInfoEmpty,
			"sSearch": defaultDataTableConstants.sSearch,
			"sZeroRecords": defaultDataTableConstants.sZeroRecords.replaceAll("{recordType}", PROJECTS_TABLE_ID),
			"sLengthMenu": defaultDataTableConstants.sLengthMenu.replaceAll("{datatableName}", PROJECTS_TABLE_ID)
		},
		"aoColumns": [
		{
			"mData": "projectId"
		},
		{
			"mData": "projectCode"
		},
		{
			"mData": null,
			"mRender": function(data, type, project) {
				if(type === 'display') {
					return '<a href="/project/' + project.projectId + '" >' + project.projectName + '</a>';
				} else {
					return project.projectName;
				}
			}
		},
		{
			"mData": "projectLocation"
		},
		{
			"mData": "startTime"
		},
		{
			"mData": "endTime"
		},
		{
			"mData": "projectManagerId"
		},
		{
			"mData": null,
			"mRender": function(data, type, project) {
				if(type === 'display') {
					return "<a href=\"/project?projectId=" + project.projectId + "\" class=\"ic-btn ic-btn-edit mR5\" title=\"Edit\" id=\"btn_edit_project\">Edit</a><a href=\"#\" class=\"ic-btn ic-btn-delete\" id =\"btn_delete_project\" title=\"Delete\">Delete</a><input type=\"hidden\" name=\"project_" + project.projectId + "\" id=\"project_" + project.projectId + "\" value=\"" + project.projectId + "\"/>";
				} else {
					return "";
				}
			}
		}],
		"sDom": defaultDataTableConstants.sDom
	});
}

$(document).on('keyup', '#' + PROJECTS_TABLE_ID + '_wrapper input', function() {
	_changeDropDownValueAfterFilter(PROJECTS_TABLE_ID);
});

$(document).on('focus', "#sbox_" + PROJECTS_TABLE_ID, function(event) {
	_updateDropDown(PROJECTS_TABLE_ID);
});
/*******************************************************************************
 * END: initialise datatable.
 ******************************************************************************/

$(document).on('click', "#btn_create_project", function(event) {
	window.location.href = "/project/"
});