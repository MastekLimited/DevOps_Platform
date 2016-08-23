<title>Organisation : Employee</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="accordion-section">
	<div class="accordion-head" id="accordion-head">
		<a href="#" class="on" aria-expanded="true" id="projectDetails">Project Details</a>
	</div>
	<div class="accordion-body form-horizontal" style="display: block">
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectId">Project Id:</label>
			<label class="control-label" for="projectId" id="projectId">${project.projectId}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectCode">Project code:</label>
			<label class="control-label" for="projectCode" id="projectCode">${project.projectCode}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectName">Project name:</label>
			<label class="control-label" for="projectName" id="projectName">${project.projectName}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectLocation">Project Location:</label>
			<label class="control-label" for="projectLocation" id="projectLocation">${project.projectLocation}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectStartDate">Start date:</label>
			<label class="control-label" for="projectCode" id="projectStartDate">${project.startTime}</label>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3" for="projectEndDate">End date:</label>
			<label class="control-label" for="projectEndDate" id="projectEndDate">${project.endTime}</label>
		</div>
		<div class="span7 pull-right text-right">
			<input class="btn btn-large btn-primary" id="btn_modify_project" type="submit" value="Modify Project" name="modifyProject" onclick="window.location.href='/project?projectId=${project.projectId}'">
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.PROJECT);
	});
</script>