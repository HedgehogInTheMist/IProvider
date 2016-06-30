<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="order.prepare.title"/></title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf"%>

<div class="content">
    <h1><fmt:message key="order.prepare.hint"/>:</h1>
    <div class="tariff-order">
        <h3>${tariff.tariffname}</h3>
        <p>${tariff.details}</p>
        
        <p class="total"><fmt:message key="order.prepare.total"/>: ${amount} <fmt:message key="currency"/></p>
    </div>

    <div class="actions">
        <a class="btn" href="ifuture?action=order&id=${tariff.id}&confirm=yes&lang=${locale}"><fmt:message key="order.prepare.purchase"/></a>
        <a href="ifuture?action=tariffs&lang=${locale}"><fmt:message key="order.prepare.cancel"/></a>
    </div>

    <%--<form action="ifuture" method="POST">
        <div class="actions">
        <input type="hidden" name="action"   value="order" />
        <input type="hidden" name="id"   value="${tariff.id}" />
        <input type="hidden" name="confirm"   value="yes" />
        <input type="hidden" name="lang"   value="${locale}" />
        <button type="submit"><fmt:message key="order.prepare.purchase"/></button>
            <a href="ifuture?action=tariffs&lang=${locale}"><fmt:message key="order.prepare.cancel"/></a>
        </div>
    </form>--%>

</div>

<%@include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>