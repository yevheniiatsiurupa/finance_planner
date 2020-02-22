<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://bootswatch.com/4/simplex/bootstrap.css" media="screen">
<link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
<html>
<head>
    <title>Expenses</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-8 offset-sm-2">
            <br/>
            <table class="table table-hover" id="table">
                <tr class="table-active">
                    <th>ID</th>
                    <th>Amount</th>
                    <th>Comment</th>
                    <th>Group Name</th>
                    <th>Category Name</th>
                    <th>Created</th>
                    <th>Currency</th>
                    <th>Cache</th>
                    <th>User Account ID</th>
                </tr>
                <c:forEach items="${requestScope.expenses}" var="expense">
                    <tr class="table-secondary">
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
</div>
</body>
</html>