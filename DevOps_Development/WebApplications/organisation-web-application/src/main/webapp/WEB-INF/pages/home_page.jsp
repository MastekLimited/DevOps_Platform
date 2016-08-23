<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Devops</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css">
</head>
<body>
	<div id="page">
		<div id="header">
			<a href="index.html" id="logo"><img src="#" alt="LOGO"></a>
			<ul id="navigation">
				<li class="selected"><a href="index.html">home</a></li>
				<li><a href="blog.html">blog</a></li>
				<li><a href="about.html">about</a></li>
			</ul>
		</div>
		<div id="contents">
			<div id="tab">
				<ul>
					<li class="selected"><a href="index.html">Featured</a></li>
					<li><a href="recent.html">Recent</a></li>
				</ul>
			</div>
			<div class="section">
				<a href="${pageContext.request.contextPath}/login/loginDetails">Get Login Details</a> <a href="${pageContext.request.contextPath}/login/saveLoginDetails">Save Login Details</a>
			</div>
		</div>
		<div class="footer">
			<div>
				<h1>Hello!</h1>
				<div id="connect">
					<h2>Get in touch</h2>
					<a href="#" target="_blank" class="facebook">facebook</a> <a href="#" target="_blank" class="twitter">twitter</a> <a href="#" target="_blank" class="googleplus">google +</a>
				</div>
				<h2>About</h2>
				<p></p>
			</div>
		</div>
	</div>
	<div id="footer">
		<p>© Copyright 2015. All rights reserved.</p>
	</div>
	<span class="extra"></span>
	</div>
</body>
</html>