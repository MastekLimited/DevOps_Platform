var EMPLOYEES_TABLE_ID = "employees";
$(document).ready(function() {
	initialiseEmployeesDatatable();
	showActiveMenu(NAVIGATION_LINK_MAP.EMPLOYEE);
});

/*******************************************************************************
 * START: initialise datatable.
 ******************************************************************************/
$.fn.dataTable.ext.errMode = 'none';
function initialiseEmployeesDatatable() {
	$('#' + EMPLOYEES_TABLE_ID).on('error.dt', function(e, settings, techNote, message) {
		handleAjaxError(e, settings, techNote, message);
	}).dataTable(
	{
		"sAjaxSource": "/employee/employeesJSON",
		"sAjaxDataProp": "employees",
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
			"sZeroRecords": defaultDataTableConstants.sZeroRecords.replaceAll("{recordType}", EMPLOYEES_TABLE_ID),
			"sLengthMenu": defaultDataTableConstants.sLengthMenu.replaceAll("{datatableName}", EMPLOYEES_TABLE_ID)
		},
		"aoColumns": [
		{
			"mData": "employeeId"
		},
		{
			"mData": "employeeNumber"
		},
		{
			"mData": null,
			"mRender": function(data, type, employee) {
				if(type === 'display') {
					return '<a href="/employee/' + employee.employeeId + '" >' + employee.firstName + " " + employee.lastName + '</a>';
				} else {
					return employee.firstName + " " + employee.lastName;
				}
			}
		},
		{
			"mData": "salary"
		},
		{
			"mData": "mobileNumber"
		},
		{
			"mData": null,
			"mRender": function(data, type, employee) {
				if(type === 'display') {
					if(employee.emailAddress != null && employee.emailAddress != "") {
						return '<a href="mailto:' + employee.emailAddress + '" >' + employee.emailAddress + '</a>';
					} else {
						return "---";
					}
				} else {
					return employee.emailAddress;
				}
			}
		},
		{
			"mData": null,
			"mRender": function(data, type, employee) {
				var address = new Array();
				var addressString = "";
				if(employee.address.addressLine1 != null && !employee.address.addressLine1.isEmpty()) {
					address.push(employee.address.addressLine1);
				}
				if(employee.address.addressLine2 != null && !employee.address.addressLine2.isEmpty()) {
					address.push(employee.address.addressLine2);
				}
				if(employee.address.postCode != null && !employee.address.postCode.isEmpty()) {
					address.push(employee.address.postCode);
				}
				if(employee.address.city != null && !employee.address.city.isEmpty()) {
					address.push(employee.address.city);
				}
				if(employee.address.state != null && !employee.address.state.isEmpty()) {
					address.push(employee.address.state);
				}
				if(employee.address.country != null && !employee.address.country.isEmpty()) {
					address.push(employee.address.country);
				}
				for(var i = 0; i < address.length; i++) {
					addressString += address[i];
					if(i + 1 != address.length) {
						addressString += ", ";
					}
				}
				return addressString;
			}
		},
		{
			"mData": null,
			"mRender": function(data, type, employee) {
				if(type === 'display') {
					return "<a href=\"/employee?employeeId=" + employee.employeeId + "\" class=\"ic-btn ic-btn-edit mR5\" title=\"Edit\" id=\"btn_edit_employee\">Edit</a><a href=\"#\" class=\"ic-btn ic-btn-delete\" id =\"btn_delete_employee\" title=\"Delete\">Delete</a><input type=\"hidden\" name=\"employee_" + employee.employeeId + "\" id=\"employee_" + employee.employeeId + "\" value=\"" + employee.employeeId + "\"/>";
				} else {
					return "";
				}
			}
		}],
		"sDom": defaultDataTableConstants.sDom
	});
}

$(document).on('keyup', '#' + EMPLOYEES_TABLE_ID + '_wrapper input', function() {
	_changeDropDownValueAfterFilter(EMPLOYEES_TABLE_ID);
});

$(document).on('focus', "#sbox_" + EMPLOYEES_TABLE_ID, function(event) {
	_updateDropDown(EMPLOYEES_TABLE_ID);
});
/*******************************************************************************
 * END: initialise datatable.
 ******************************************************************************/

$(document).on('click', "#btn_create_employee", function(event) {
	window.location.href = "/employee/"
});