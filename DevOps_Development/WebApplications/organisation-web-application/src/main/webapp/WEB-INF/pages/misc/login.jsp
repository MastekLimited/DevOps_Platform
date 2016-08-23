<title>Organisation : Home</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<div th:if="${param.error}">Invalid username and password.</div>
<div th:if="${param.logout}">You have been logged out.</div>
<form th:action="@{/login}" method="post">
	<div>
		<label>
			User Name :
			<input type="text" name="username" />
		</label>
	</div>
	<div>
		<label>
			Password:
			<input type="password" name="password" />
		</label>
	</div>
	<div>
		<input type="submit" value="Sign In" />
	</div>
</form>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.HOME);
	});
</script>