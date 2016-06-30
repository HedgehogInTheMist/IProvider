<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <style>
        /* Note: Try to remove the following lines to see the effect of CSS positioning */
        .affix {
            top: 20px;
        }
    </style>


    <title><fmt:message key="admin.manager.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.btn-delete').click(function(){
                if (confirm ('<fmt:message key="admin.manager.delete"/>')){
                    return true;
                }
                return false;
            })
        });
    </script>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf"%>

<div class="content">

    <%--<c:if test="${not empty user}">
        <p><fmt:message key="info.auth.success"/> </p>
    </c:if>--%>


    <div class="actions">
        <a class="btn" href="ifuture?action=add_tariff&approve2=yes&lang=${locale}" data-spy="affix" data-offset-top="205"><fmt:message key="tariff_table.add" /></a>
    </div>

    <c:forEach items="${tariffs}" var="tariff">
        <div class="tariff">
            <h3>${tariff.tariffname}
                <c:if test="${tariff.hot}">
                    <span class="hot"><fmt:message key="tariff_table.hot"/></span>
                </c:if>
            </h3>
            <div class="inner">
                <p><b><fmt:message key="tariff_table.price"/>:</b> ${tariff.price} <fmt:message key="currency"/></p>
                <p><b><fmt:message key="tariff_table.type"/>:</b> ${tariff.type}</p>
                <p><b><fmt:message key="tariff_table.details"/>:</b> ${tariff.details}</p>
                <p><b><fmt:message key="tariff_table.discount"/>:</b> ${tariff.regularDiscount}%</p>
            </div>
            <a class="btn btn-blue" href="ifuture?action=update_tariff&id=${tariff.id}&lang=${locale}"><fmt:message key="tariff_table.edit"/></a>
            <a class="btn btn-delete" href="ifuture?action=delete_tariff&id=${tariff.id}&lang=${locale}"><fmt:message key="tariff_table.delete"/></a>
        </div>
    </c:forEach>

    <div class="actions">
        <a class="btn" href="ifuture?action=add_tariff&approve2=yes&lang=${locale}"><fmt:message key="tariff_table.add"/></a>
    </div>



</div>
<%@include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>