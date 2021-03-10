<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>My Home Page</title>
</head>
<body>
<div class="container-lg">
    <form action="${pageContext.request.contextPath}/register.do" method="post">
        <!-- Content here -->
        ID：<input name="id" type="text" value="${param.id}" placeholder="请输入id"/></br>
        用户名：<input name="username" type="text" value="${param.usename}" placeholder="请输入用户名"/></br>
        登录密码：<input name="password" type="password" value="${param.password}" placeholder="请输入登录密码"/></br>
        电话：<input name="phoneNumber" type="text" value="${param.phoneNumber}"/></br>
        <input title="注册" value="注册" name="submit" type="submit"/>
    </form>
    <hr/>
    <label style="color: red;">${msg}</label>
</div>
</body>