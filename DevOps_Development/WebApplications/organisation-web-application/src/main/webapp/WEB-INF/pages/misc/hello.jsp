<title>Organisation : Home</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<div th:if="${param.error}">Invalid username and password.</div>
<div th:if="${param.logout}">You have been logged out.</div>
<h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>
<form th:action="@{/logout}" method="post">
	<input type="submit" value="Sign Out" />
</form>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.HOME);
	});
</script>