<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="tariff.supplement.success.title"/></title>
    <link rel="stylesheet" href="../../../css/style.css"/>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf"%>


<div class="content">
    <div class="order-complete">
        <h1><fmt:message key="tariff.added.success.title"/></h1>
        <p><fmt:message key="tariff.added.success.message">
            <fmt:param value="${tariff.tariffname}"/>
            <fmt:param value="${tariffTypes}"/>
            <%--<fmt:param value="${tariffType}"/>--%>

<%--
            <jsp:useBean id="tariffName" class="com.epam.inet.provider.entity.Tariff"/>
            <jsp:setProperty name="tariffName" property="tariffname" value="${tariff.tariffname}"/>
            <jsp:getProperty name="tariffName" property="tariffname"/>



            Congrats! You've just added new tariff plan!
            <a href="ifuture?action=manager&lang=${locale}"><fmt:message key="tariff_form.back"/></a>
--%>




        </fmt:message>
        </p>
        <a href="ifuture?action=manager&lang=${locale}"><fmt:message key="order.success.goto"/></a>
    </div>

</div>


<%@include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
