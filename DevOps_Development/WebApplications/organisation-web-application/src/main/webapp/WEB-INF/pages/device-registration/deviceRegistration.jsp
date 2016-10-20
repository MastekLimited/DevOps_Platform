<title>Organisation : Device Registration</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="form-horizontal" method="post" name="deviceRegistration" action="/deviceRegistration/saveDeviceRegistration">
	<fieldset>
		<div class="accordion-section">
			<div class="accordion-head" id="accordion-head">
				<a href="#" class="on" aria-expanded="true" id="deviceRegistrationDetails">Device Registration Details</a>
			</div>
			<div class="accordion-body form-horizontal" style="display: block">
				<input type="hidden" name="registrationId" id="registrationId" value="${deviceRegistration.registrationId}" />
				<div class="form-group">
					<label class="control-label col-sm-3" for="deviceRegistrationId">Device Registration Id:</label>
					<label class="control-label" for="deviceRegistrationId" id="deviceRegistrationId">${deviceRegistration.registrationId}</label>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="Secret challenge">
						<span class="text-required"><sup>*</sup></span><span>Secret challenge:</span>
					</label>
					<div class="col-sm-9">
						<input type="password" name="challenge" id="secretChallenge" class="noEnterSubmit" value="${deviceRegistration.challenge}" placeholder="" size="35" maxlength="20" title="Secret challenge" />
					</div>
				</div>
			</div>
		</div>
		<div class="span7 pull-middle text-right">
			<input class="btn btn-large btn-primary" id="btn_save_deviceregistration" type="submit" value="Save Device Registration" name="saveDeviceRegistration">
		</div>
	</fieldset>
</form>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.DEVICE_REGISTRATION);
	});
</script>