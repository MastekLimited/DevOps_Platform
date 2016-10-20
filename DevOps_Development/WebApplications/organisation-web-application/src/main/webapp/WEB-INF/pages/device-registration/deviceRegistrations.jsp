<title>Organisation : Device Registration List</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/device-registration/device.registration.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="span8 mB20">Device Registrations</h1>
<div class="data-table-wrap">
	<table id="deviceRegistrations" class="display dataTable">
		<thead>
			<tr>
				<th>Registration Id</th>
				<th>Device Id</th>
				<th>Application Id</th>
				<th class="action-column"></th>
			</tr>
		</thead>
	</table>
</div>
<div class="text-right mT20">
	<button class="btn btn-large btn-primary" id="btn_create_device_registration" type="button">Register New Device</button>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />