<title>Organisation : Device Registration</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${savedDeviceRegistration != null}">
	<div class="alert alert-success" role="alert">Your device has been registered.</div>
</c:if>
<div class="accordion-section">
	<div class="accordion-head" id="accordion-head">
		<a href="#" class="on" aria-expanded="true" id="deviceRegistrationDetails">Device Registration Details</a>
	</div>

	<div class="accordion-body form-horizontal" style="display: block">
		<div class="form-group">
			<label class="control-label col-sm-3" for="deviceRegistrationId">Device Registration Id:</label>
			<label class="control-label" for="deviceRegistrationId" id="deviceRegistrationId">${deviceRegistration.registrationId}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="deviceRegistrationQRCode">Registration QR Code:</label>
			<img alt="" height="300px" width="300px" src="/getDeviceRegistrationQRCode/${deviceRegistration.registrationId}">
		</div>
	</div>
</div>
<div class="text-right mT20">
	<button class="btn btn-large btn-primary" id="btn_create_device_registration" type="button">Register New Device</button>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.DEVICE_REGISTRATION);

		$(document).on('click', "#btn_create_device_registration", function(event) {
			window.location.href = "/deviceRegistration/"
		});
	});
</script>