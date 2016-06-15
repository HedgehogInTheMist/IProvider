<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="auth" uri="http://showstopper.net/taglib/auth" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="add_tariff.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.paid').click(function() {
                var paid = $(this).is(":checked");
                    var link = "ifuture?action=orders&id="+$(this).attr('data-id') + "&paid="+paid;
                    window.location.href = link;
            });
        });
    </script>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf"%>


<div class="content">
    <h1><fmt:message key="orders.title"/></h1>
    <table style="width: 100%">
        <thead>
        <tr>
            <th><fmt:message key="orders.id"/></th>
            <th><fmt:message key="orders.tariffname"/></th>
            <th><fmt:message key="orders.client"/></th>
            <th><fmt:message key="orders.price"/></th>
            <th><fmt:message key="orders.date"/></th>
            <th><fmt:message key="orders.paid"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.tariff.tariffname}</td>
            <td>${order.user.username}</td>
            <td><fmt:formatNumber maxFractionDigits="1" type="number"  groupingUsed="false" value="${order.amount}"/> <fmt:message key="currency"/></td>
            <td><fmt:formatDate value="${order.dateTime}" pattern="d.M.Y H:m"/> </td>
            <td><input data-id="${order.id}" data-paid=${not empty order.paid} class="paid" type="checkbox" <c:if test="${order.paid}">checked="true"</c:if>/> </td>
        </tr>

        </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>