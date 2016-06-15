<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="tariff.title"/></title>
    <link rel="stylesheet" href="css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf"%>

<div class="content" id="tariffs">
    <c:forEach var="tariff" items="${tariffs}">
        <div class="tariff-view">
            <h3>${tariff.tariffname}</h3>
            <c:set var="type" value="${tariff.type.displayName}"/>
            <p class="type type-${type}"><fmt:message key="tariff_type.${type}"/><c:if test="${tariff.hot}"><span class="hot"><fmt:message key="tariff_table.hot"/></span></c:if></p>
            <p class="details">${tariff.details}</p>

            <c:set var="discounted" value="${tariff.price - (tariff.price * tariff.regularDiscount * 0.01)}"/>
            <c:choose>

                <c:when test="${regular eq true and discounted < tariff.price}">

                    <p class="price"><span class="discounted">${tariff.price} <fmt:message key="currency"/></span> ${discounted} <fmt:message key="currency"/></p>
                </c:when>
                <c:otherwise>
                    <p class="price">${tariff.price} <fmt:message key="currency"/></p>
                </c:otherwise>

            </c:choose>

            <c:choose>
                <c:when test="${not empty rolename and rolename eq 'client'}">
                    <a class="btn" href="ifuture?action=order&id=${tariff.id}&lang=${locale}"><fmt:message key="tariff.order"/></a>
                </c:when>
                <c:otherwise>
                    <p class="not-logged"><fmt:message key="tariff.not_logged"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>