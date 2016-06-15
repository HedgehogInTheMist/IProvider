<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="auth.page.title"/></title>
    <link rel="stylesheet" href="css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%--Bootstrap inclusion--%>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<div class="content">

    <h1><fmt:message key="auth.page.title"/></h1>

    <c:if test="${not empty userCreated}">
        <div class="alert alert-success fade in msg-registration_successful">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>
                <c:out value="${userCreated}"/>
            </strong>
        </div>
    </c:if>

    <p><fmt:message key="auth.page.message"/></p>

    <c:if test="${not empty lastEnteredLogin and not empty errorLoginOrPass}">
        <div class="alert alert-warning fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong><c:out value="${errorLoginOrPass}" /></strong>
        </div>
    </c:if>

    <form method="post" id="loginForm">
        <input type="hidden" name="command" value="login"/>
        <label for="login"><fmt:message key="auth.page.login_form.login"/> :</label>
        <input id="login" type="text" name="login" value="${lastEnteredLogin}"/>
        <label for="password"><fmt:message key="auth.page.login_form.password"/>:</label>
        <input id="password" type="password" name="password"/>
        <input type="submit" value="<fmt:message key="auth.page.login_form.submit"/>"/>
    </form>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>