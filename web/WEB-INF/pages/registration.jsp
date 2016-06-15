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

    <h1><fmt:message key="registration.page.title"/></h1>

    <p>
    <div class="err" style="color:red">${errorRegistrationMessage}</div>
    </p>


    <form name="RegistrationForm" method="post" action="ifuture">
        <input type="hidden" name="action" value="registration"/>


        <c:if test="${not empty userExists or not empty incorrectLogin}">
            <div class="alert alert-warning fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <%--<strong><fmt:message key="registration.message.incorrect.login"/></strong>--%>
                <strong><c:out value="${userExists}"/></strong>
                <strong><c:out value="${incorrectLogin}"/></strong>
            </div>
        </c:if>

        <c:if test="${not empty nonConfirmPass or not empty incorrectPass}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>
                    <c:out value="${nonConfirmPass}"/>
                    <c:out value="${incorrectPass}"/>
                </strong>
            </div>

        </c:if>

        <p align="center"><fmt:message key="registration.user.login"/>:<br>
            <input type="text" name="login" value=""/>


            <br><br><fmt:message key="registration.user.password"/>:<br>
            <input type="password" name="password" value=""/>
            <br><br><fmt:message key="registration.password.repeat"/>:<br>
            <input type="password" name="confirmPassword" value=""/>
            <br></p>
        <%--<div class="err">${errorRegistrationMessage}</div>--%>
        <p align="center">
            <input type="submit" value="<fmt:message key="button.register"/>"/>
        </p>
    </form>


</div>

<%@include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>