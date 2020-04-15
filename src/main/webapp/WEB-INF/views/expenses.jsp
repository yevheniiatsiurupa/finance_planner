<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://bootswatch.com/4/sandstone/bootstrap.css" media="screen">
<link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
<html>
<head>
    <spring:message code="label.id" var="labelId"/>
    <spring:message code="label.expense.amount" var="labelAmount"/>
    <spring:message code="label.expense.comment" var="labelComment"/>
    <spring:message code="label.expense.categoryName" var="labelGroupName"/>
    <spring:message code="label.expense.subcategoryName" var="labelCategoryName"/>
    <spring:message code="label.expense.created" var="labelCreated"/>
    <spring:message code="label.currency.name" var="labelCurrencyName"/>
    <spring:message code="label.expense.cache" var="labelCache"/>
    <%--<spring:message code="label.userAccount.id" var="labelUserId"/>--%>
    <spring:message code="label.page.expenses" var="labelMyExpenses"/>
    <title>${labelMyExpenses}</title>
</head>
<body>
<div class="container">
    <div class="row">
        <table class="table table-hover" id="table">
            <tr class="table-active">
                <th>${labelId}</th>
                <th>${labelAmount}</th>
                <th>${labelComment}</th>
                <th>${labelGroupName}</th>
                <th>${labelCategoryName}</th>
                <th>${labelCreated}</th>
                <th>${labelCurrencyName}</th>
                <th>${labelCache}</th>
                <th>${labelUserId}</th>
            </tr>
            <c:forEach items="${requestScope.expenses}" var="expense">
                <tr class="table-default">
                    <td><c:out value="${expense.id}"/></td>
                    <td><c:out value="${expense.amount}"/></td>
                    <td><c:out value="${expense.comment}"/></td>
                    <td><c:out value="${expense.groupName}"/></td>
                    <td><c:out value="${expense.categoryName}"/></td>
                    <td><c:out value="${expense.created}"/></td>
                    <td><c:out value="${expense.currency.name}"/></td>
                    <td><c:out value="${expense.cache}"/></td>
                    <td><c:out value="${expense.userAccount.id}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>