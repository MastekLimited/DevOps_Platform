<title>Organisation : Employees List</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/employee/employees.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <h1 class="span8 mB20">Employees</h1> -->
<h1 class="span8 mB20">Employees build test</h1>
<c:if test="${savedEmployee != null}">
	<div class="alert alert-success" role="alert">Your employee ${savedEmployee.firstName} ${savedEmployee.lastName} has been saved with id:${savedEmployee.employeeId} and number: ${savedEmployee.employeeNumber}.</div>
</c:if>
<div class="data-table-wrap">
	<table id="employees" class="display dataTable">
		<thead>
			<tr>
				<th>Id</th>
				<th>Number</th>
				<th>Name</th>
				<th>Salary</th>
				<th>Mobile</th>
				<th>Email</th>
				<th>Address</th>
				<th class="action-column"></th>
			</tr>
		</thead>
	</table>
</div>
<div class="text-right mT20">
	<button class="btn btn-large btn-primary" id="btn_create_employee" type="button">Create Employee</button>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
