<title>Organisation : Employee</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="form-horizontal" method="post" name="employee" action="/employee/saveEmployee">
	<fieldset>
		<div class="accordion-section">
			<div class="accordion-head" id="accordion-head">
				<a href="#" class="on" aria-expanded="true" id="employeeDetails">Personal Details</a>
			</div>
			<div class="accordion-body form-horizontal" style="display: block">
				<input type="hidden" name="employeeId" id="employeeId" value="${employee.employeeId}" />
				<input type="hidden" name="employeeNumber" id="employeeNumber" value="${employee.employeeNumber}" />
				<input type="hidden" name="googleAuthenticatorKey" id="employeeGoogleAuthenticatorKey" value="${employee.googleAuthenticatorKey}" />
				<div class="form-group">
					<label class="control-label col-sm-3" for="employeeNumber">Employee Number:</label>
					<label class="control-label" for="employeeNumber" id="employeeNumber">${employee.employeeNumber}</label>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="firstName">
						<span class="text-required"><sup>*</sup></span><span>First name:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="firstName" id="firstName" class="noEnterSubmit" value="${employee.firstName}" placeholder="" size="35" maxlength="100" title="First name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="lastName">
						<span class="text-required"><sup>*</sup></span><span>Last name:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="lastName" id="lastName" class="noEnterSubmit" value="${employee.lastName}" placeholder="" size="35" maxlength="100" title="Last name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="salary">
						<span>Salary:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="salary" id="salary" class="noEnterSubmit" value="${employee.salary}" placeholder="" size="35" maxlength="100" title="Salary" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="mobileNumber">
						<span>Mobile number:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="mobileNumber" id="mobileNumber" class="noEnterSubmit" value="${employee.mobileNumber}" placeholder="" size="35" maxlength="100" title="Mobile number" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="emailAddress">
						<span class="text-required"></span><span>Email address:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="emailAddress" id="emailAddress" class="noEnterSubmit" value="${employee.emailAddress}" placeholder="" size="35" maxlength="100" title="Email address" />
					</div>
				</div>
			</div>
		</div>
		<div class="accordion-section">
			<div class="accordion-head" id="accordion-head">
				<a href="#" class="on" aria-expanded="true" id="employeeDetails">Address Details</a>
			</div>
			<div class="accordion-body form-horizontal" style="display: block">
				<div class="form-group">
					<label class="control-label col-sm-3" for="address.addressLine1">
						<span class="text-required"></span><span>Line 1:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.addressLine1" id="addressLine1" class="noEnterSubmit" value="${employee.address.addressLine1}" placeholder="" size="35" maxlength="100" title="Line 1" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="addressLine2">
						<span class="text-required"></span><span>Line 2:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.addressLine2" id="addressLine2" class="noEnterSubmit" value="${employee.address.addressLine2}" placeholder="" size="35" maxlength="100" title="Line 2" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="postCode">
						<span class="text-required"></span><span>Postcode:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.postCode" id="postCode" class="noEnterSubmit" value="${employee.address.postCode}" placeholder="" size="35" maxlength="100" title="Postcode" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="city">
						<span class="text-required"></span><span>City:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.city" id="city" class="noEnterSubmit" value="${employee.address.city}" placeholder="" size="35" maxlength="100" title="City" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="state">
						<span class="text-required"></span><span>State/ County/ Province:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.state" id="state" class="noEnterSubmit" value="${employee.address.state}" placeholder="" size="35" maxlength="100" title="State/ County/ Province" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="country">
						<span class="text-required"></span><span>Country:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="address.country" id="country" class="noEnterSubmit" value="${employee.address.country}" placeholder="" size="35" maxlength="100" title="Country" />
					</div>
				</div>
				<div class="span7 pull-right text-right">
					<input class="btn btn-large btn-primary" id="btn_save_employee" type="submit" value="Save Employee" name="saveEmployee">
				</div>
			</div>
		</div>
	</fieldset>
</form>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.EMPLOYEE);
	});
</script>