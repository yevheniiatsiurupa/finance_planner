<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
    <link rel="stylesheet" href="https://bootswatch.com/4/sandstone/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <spring:message code="label.page.expenses" var="pageExpenses"/>
    <spring:message code="label.page.incomes" var="pageIncomes"/>
    <spring:message code="label.page.accounts" var="pageAccounts"/>
    <spring:message code="label.page.settings" var="pageSettings"/>
    <spring:message code="label.page.home.to" var="pageHome"/>

    <spring:message code="label.page.expenses.add" var="pageAddExpenses"/>
    <spring:message code="label.page.expense" var="labelExpense"/>

    <spring:message code="message.success.add" var="messageAdd"/>
    <spring:message code="${sessionScope.userAccountConfig.currency.sign}" var="currency"/>
    <title>${pageAddExpenses}</title>
    <style>
        body {
            padding-top: 100px;
            padding-left: 50px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-primary" style="padding-left: 50px">
    <a class="navbar-brand" href="#">Planner</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">${pageExpenses}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">${pageIncomes}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">${pageAccounts}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">${pageSettings}</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container" style="margin-left: 50px">
    <div class="col-sm-7">
        <c:if test="${message != null}">
            <div class="row">
                  <h5>${messageAdd} ${labelExpense} ${expense.amount} ${currency} ${expense.categoryName} - ${expense.subCategoryName}.</h5>
            </div>
        </c:if>
        <br/>
        <br/>
        <div class="row">
            <form action="${pageContext.request.contextPath}/expense/add" method="get">
                <button type="submit" class="btn btn-outline-primary">${pageAddExpenses}</button>
            </form>
        </div>
        <div class="row">
            <form action="${pageContext.request.contextPath}/" method="get">
                <button type="submit" class="btn btn-outline-primary">${pageHome}</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>