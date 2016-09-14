<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<title>Login Details : Update Login Details</title>
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--css media queries for ie8 / ie 7-->
<!--[if lt IE 9]>
<script src="plugins/respond.min.js"></script>
<![endif]-->
</head>
<!-- END header -->
<body>
	<h4>${message}</h4>
	<form method="post" action="${pageContext.request.contextPath}/login/saveLoginDetails">
		<table>
			<tr>
				<td>User UUID:</td>
				<td>
					<input type="text" name="userUUID">
				</td>
			</tr>
			<tr>
				<td>Last Logged in time:</td>
				<td>
					<input type="text" name="loggedInTime">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Save Login Info" name="updateLoginInfo">
				</td>
			</tr>
		</table>
	</form>
	<a href="${pageContext.request.contextPath}/login/loginDetails">Get Login Details</a>
</body>
</html>