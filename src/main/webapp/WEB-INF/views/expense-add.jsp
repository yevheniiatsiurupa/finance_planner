<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://bootswatch.com/4/sandstone/bootstrap.css" media="screen">
<link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
<html>
<head>
    <spring:message code="label.page.expenses.add" var="labelAddExpenses"/>
    <spring:message code="label.expense.amount" var="labelAmount"/>
    <spring:message code="label.expense.categoryName" var="labelCategory"/>
    <spring:message code="label.expense.subcategoryName" var="labelSubCategory"/>
    <spring:message code="label.expense.comment" var="labelComment"/>
    <spring:message code="label.expense.cache" var="labelCache"/>
    <spring:message code="label.expense.payment" var="labelPayment"/>
    <spring:message code="label.expense.created" var="labelCreated"/>
    <spring:message code="message.placeholder.amount" var="messageAmount"/>
    <spring:message code="message.placeholder.dropdown" var="messageDropdown"/>
    <spring:message code="message.placeholder.comment" var="messageComment"/>
    <spring:message code="message.add" var="messageAdd"/>
    <title>${labelAddExpenses}</title>
    <style>
        body {
            padding-top: 50px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="col-sm-7 offset-sm-2">
        <div class="card bg-light mb-3" style="max-width: 50rem;">
            <div class="card-header"><h5><b>${labelAddExpenses}</b></h5></div>
            <div class="card-body">

                <form:form action="${pageContext.request.contextPath}/expense/add" modelAttribute="expense" method="post">
                    <form:input path="id" name="id" type="hidden"/>
                    <div class="row">
                        <div class="col-sm-5">
                            <div class="form-group">
                                <label for="amount">${labelAmount}</label>
                                <form:input path="amount" id="amount" type="text" class="form-control" placeholder="${messageAmount}"/>
                            </div>

                            <div class="form-group">
                                <label for="category">${labelCategory}</label>
                                <form:select path="categoryName" class="form-control" id="category">
                                    <option value="" disabled selected>${messageDropdown}</option>
                                </form:select>
                            </div>

                            <div class="form-group">
                                <label for="subCategory">${labelSubCategory}</label>
                                <form:select path="subCategoryName" class="form-control" id="subCategory">
                                    <option value="" disabled selected>${messageDropdown}</option>
                                </form:select>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary">${messageAdd}</button>
                        </div>

                        <div class="col-sm-1"></div>

                        <div class="col-sm-6">

                            <div class="row">
                                <div class="col-sm-5">
                                    <div class="form-group">
                                        <label for="cache">${labelPayment}</label>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                            <form:checkbox path="cache" id="cache" class="form-check-input" value=""/>
                                            ${labelCache}
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-7">
                                    <div class="form-group">
                                        <label for="created">${labelCreated}</label>
                                        <form:input path="created" id="created" type="text" class="form-control"/>
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="comment">${labelComment}</label>
                                <form:textarea class="form-control" path="comment" id="comment" rows="5" placeholder="${messageComment}"/>
                            </div>

                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>