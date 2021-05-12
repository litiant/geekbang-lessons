<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<form method="get" action="/oauth/login">
		<div class="container-lg">
			<!-- Content here -->
			<input name="type" type="submit" value="gitee">
		</div>
	</form>
</body>