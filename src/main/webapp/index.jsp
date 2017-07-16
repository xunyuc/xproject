<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Hello World!</h2>
<%
    HttpSession s = request.getSession();
    String userName = (String) s.getAttribute("currentUser");
%>
当前Session的ID值是<%=s.getId() %>
<br>
<%if(userName!=null){%>
你好,<%=userName %>
<br>
<a href="/logout">退出登陆</a>
<br>
<%}%>
***************************************
<br>

<shiro:principal/> <br>
<shiro:hasRole name="admin">需要管理员角色</shiro:hasRole> <br>
<shiro:hasRole name="guest">需要Guest角色</shiro:hasRole> <br>
<shiro:hasPermission name="query">需要query权限</shiro:hasPermission> <br>
<shiro:hasPermission name="add">需要add权限</shiro:hasPermission> <br>

</body>
</html>
