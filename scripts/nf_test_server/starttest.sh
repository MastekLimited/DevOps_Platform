#!/bin/bash
echo "### Running JMeter performance test ###"

WORKSPACE=/opt/jmeter/apache-jmeter-2.13/bin
# Clear out old results
if [ -f $WORKSPACE/jmeter_test_results.jtl ]
  then
  rm $WORKSPACE/jmeter_test_results.jtl
fi

# Run the tests
echo "## Running the tests"
cd "$WORKSPACE/jmeter"

#$WORKSPACE/jmeter -n -t $WORKSPACE/templates/build-webservice-test-plan.jmx -l $WORKSPACE/templates/jmeter_test_results.jtl -p $WORKSPACE/user.properties
$WORKSPACE/jmeter -n -t $WORKSPACE/templates/devops_v1.0.jmx -l $WORKSPACE/templates/jmeter_test_results.jtl -p $WORKSPACE/user.properties
