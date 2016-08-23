<title>Organisation : Logout</title>
<jsp:include page="/WEB-INF/pages/headers/header.jsp" />
<link rel="stylesheet" type="text/css" href="/css/misc.pages.css">
<script type="text/javascript" charset="utf8" src="/js/common/common.ui.util.js"></script>
<div class="shadowDiv">
	<div class="shadowDivTitle">
		<h1 href="#">DevOps Web Application</h1>
	</div>
	<div class="shadowDivContent">
		<h2>You have been logged out of the DevOps application, either because:</h2>
		<div class="shadowDivReason">
			<ul>
				<li>Your session id is invalid</li>
				<li>Your session ended because of logout</li>
				<li>Your session ended due to inactivity</li>
			</ul>
			<p>
				To continue, ensure you have authenticated or your device is registered and click on <a href="/">return to home page</a>.
			</p>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/pages/footers/footer.jsp" />
<script>
	$(document).ready(function() {
		showActiveMenu(NAVIGATION_LINK_MAP.HOME);
	});
</script>