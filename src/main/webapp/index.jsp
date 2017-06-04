<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<body>
<h2>Hello World!</h2>
<shiro:principal/> <br>
<shiro:hasRole name="admin">需要管理员角色</shiro:hasRole> <br>
<shiro:hasRole name="guest">需要Guest角色</shiro:hasRole> <br>
<shiro:hasPermission name="query">需要query权限</shiro:hasPermission> <br>
<shiro:hasPermission name="add">需要add权限</shiro:hasPermission> <br>

</body>
</html>
