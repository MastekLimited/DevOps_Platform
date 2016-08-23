var DEVICE_REGISTRATION_TABLE_ID = "deviceRegistrations";
$(document).ready(function() {
	initialiseDeviceRegistrationDatatable();
	showActiveMenu(NAVIGATION_LINK_MAP.DEVICE_REGISTRATION);
});

/*******************************************************************************
 * START: initialise datatable.
 ******************************************************************************/
$.fn.dataTable.ext.errMode = 'none';
function initialiseDeviceRegistrationDatatable() {
	$('#' + DEVICE_REGISTRATION_TABLE_ID).on('error.dt', function(e, settings, techNote, message) {
		handleAjaxError(e, settings, techNote, message);
	}).dataTable(
	{
		"sAjaxSource": "/deviceRegistration/deviceRegistrationsJSON",
		"sAjaxDataProp": "deviceRegistrations",
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
			"sZeroRecords": defaultDataTableConstants.sZeroRecords.replaceAll("{recordType}", DEVICE_REGISTRATION_TABLE_ID),
			"sLengthMenu": defaultDataTableConstants.sLengthMenu.replaceAll("{datatableName}", DEVICE_REGISTRATION_TABLE_ID)
		},
		"aoColumns": [
		{
			"mData": null,
			"mRender": function(data, type, deviceRegistration) {
				if(type === 'display') {
					return '<a href="/deviceRegistration/' + deviceRegistration.deviceRegistrationId + '" >' + deviceRegistration.registrationId + '</a>';
				} else {
					return deviceRegistration.registrationId;
				}
			}
		},
		{
			"mData": "deviceId"
		},
		{
			"mData": "applicationId"
		},
		{
			"mData": null,
			"mRender": function(data, type, deviceRegistration) {
				if(type === 'display') {
					return "<a href=\"/deviceRegistration?deviceRegistrationId=" + deviceRegistration.deviceRegistrationId + "\" class=\"ic-btn ic-btn-edit mR5\" title=\"Edit\" id=\"btn_edit_device_registration\">Edit</a><a href=\"#\" class=\"ic-btn ic-btn-delete\" id =\"btn_delete_device_registration\" title=\"Delete\">Delete</a><input type=\"hidden\" name=\"device_registration_" + deviceRegistration.deviceRegistrationId + "\" id=\"device_registration_" + deviceRegistration.deviceRegistrationId + "\" value=\"" + deviceRegistration.deviceRegistrationId + "\"/>";
				} else {
					return "";
				}
			}
		}],
		"sDom": defaultDataTableConstants.sDom
	});
}

$(document).on('keyup', '#' + DEVICE_REGISTRATION_TABLE_ID + '_wrapper input', function() {
	_changeDropDownValueAfterFilter(DEVICE_REGISTRATION_TABLE_ID);
});

$(document).on('focus', "#sbox_" + DEVICE_REGISTRATION_TABLE_ID, function(event) {
	_updateDropDown(DEVICE_REGISTRATION_TABLE_ID);
});
/*******************************************************************************
 * END: initialise datatable.
 ******************************************************************************/

$(document).on('click', "#btn_create_device_registration", function(event) {
	window.location.href = "/deviceRegistration/"
});