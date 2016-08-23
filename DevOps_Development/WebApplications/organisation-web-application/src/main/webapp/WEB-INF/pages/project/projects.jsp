<title>Organisation : Projects</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/project/projects.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="span8 mB20">Projects</h1>
<c:if test="${savedProject != null}">
	<div class="alert alert-success" role="alert">Your project ${savedProject.projectName} has been saved with id:${savedProject.projectId} and number: ${savedProject.projectCode}.</div>
</c:if>
<div class="data-table-wrap">
	<table id="projects" class="display dataTable">
		<thead>
			<tr>
				<th>Id</th>
				<th>Code</th>
				<th>Name</th>
				<th>Location</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Project Manager</th>
				<th class="action-column"></th>
			</tr>
		</thead>
	</table>
</div>
<div class="text-right mT20">
	<button class="btn btn-large btn-primary" id="btn_create_project" type="button">Create Project</button>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />