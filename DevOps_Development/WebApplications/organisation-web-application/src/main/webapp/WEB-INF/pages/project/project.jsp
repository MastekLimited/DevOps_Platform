<title>Organisation : Employee</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<jsp:include page="/WEB-INF/pages/headers/datatablesInclude.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form method="post" name="project" action="/project/saveProject">
	<fieldset>
		<div class="accordion-section">
			<div class="accordion-head" id="accordion-head">
				<a href="#" class="on" aria-expanded="true" id="projectDetails">Project Details</a>
			</div>
			<div class="accordion-body form-horizontal" style="display: block">
				<input type="hidden" name="projectId" value="${project.projectId}" />
				<input type="hidden" name="projectCode" value="${project.projectCode}" />
				<div class="form-group">
					<label class="control-label col-sm-3" for="projectName">
						<span class="text-required"><sup>*</sup></span><span>Project name:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="projectName" id="projectName" class="noEnterSubmit" value="${project.projectName}" placeholder="" size="35" maxlength="100" title="Project name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="projectLocation">
						<span class="text-required"><sup>*</sup></span><span>Project location:</span>
					</label>
					<div class="col-sm-9">
						<input type="text" name="projectLocation" id="projectLocation" class="noEnterSubmit" value="${project.projectLocation}" placeholder="" size="35" maxlength="100" title="Project location" />
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="control-label col-sm-3" for="projectStartDate"><span class="text-required"><sup>*</sup></span><span>Start date:</span></label>
					<div class="col-sm-9">
						<input type="text" name="startTime" id="projectStartDate" class="noEnterSubmit" value="${project.startTime}" placeholder="" size="10" maxlength="100" title="Start date"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="projectEndDate"><span class="text-required"><sup>*</sup></span><span>End date:</span></label>
					<div class="col-sm-9">
						<input type="text" name="endTime" id="projectEndDate" class="noEnterSubmit" value="${project.endTime}" placeholder="" size="10" maxlength="100" title="End date"/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="project"><span class="text-required"><sup>*</sup></span><span>Project :</span></label>
					<div class="col-sm-9">
						<input type="text" name="project" id="project" class="noEnterSubmit" value="${project.project}" placeholder="" size="10" maxlength="100" title="Project "/>
					</div>
				</div> --%>
				<div class="span7 pull-right text-right">
					<input class="btn btn-large btn-primary" id="btn_save_project" type="submit" value="Save Project" name="saveProject">
				</div>
			</div>
		</div>
	</fieldset>
</form>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.PROJECT);
	});
</script>