<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://bootswatch.com/4/sandstone/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <spring:url value="/resources/scripts/i18n/datepicker-en-GB.js" var="dateEN" />
    <spring:url value="/resources/scripts/i18n/datepicker-ru.js" var="dateRU" />
    <script src="${dateEN}"></script>
    <script src="${dateRU}"></script>

    <script>
        $( function() {
            $.datepicker.setDefaults(
                $.datepicker.regional[getDatepickerLocale()]
            );

            $( "#created" ).datepicker({
                dateFormat: "D, d MM"
            }).datepicker('setDate', new Date());
        } );
    </script>

    <script>
        function getDatepickerLocale() {
            var loc = "${pageContext.request.locale}";
            if (loc === 'ru_RU') {
                return 'ru';
            } else {
                return 'en-GB';
            }
        }
    </script>

    <spring:message code="label.page.expenses" var="pageExpenses"/>
    <spring:message code="label.page.incomes" var="pageIncomes"/>
    <spring:message code="label.page.accounts" var="pageAccounts"/>
    <spring:message code="label.page.settings" var="pageSettings"/>

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
    <spring:message code="${sessionScope.userAccountConfig.currency.sign}" var="currency"/>
    <title>${labelAddExpenses}</title>
    <style>
        body {
            padding-top: 100px;
            padding-left: 50px;
        }
    </style>

    <script>
        $(document).ready(function () {
            $.ajax({
                url: './../configs/expense-categories',
                dataType: 'json',
                success: function (data, status, settings) {
                    getCategories(data);
                }
            })
        })
    </script>

    <script>
        function getCategories(data) {
            var arr = data;
            for (i = 0; i < arr.length; i++){
                var category = arr[i];
                var opt = document.createElement('option');
                opt.innerHTML = category['categoryName'];
                opt.value = category['categoryName'];
                document.getElementById('category').appendChild(opt);
            }
        }
    </script>

    <script>
        function getSubcategories() {
            deleteOptions();
            var countryVal = $('#category').val();
            $.ajax({
                type: 'POST',
                url:'./../configs/expense-subcategories',
                data: countryVal,
                contentType: "text/plain",
                datatype: 'json',
                success: function (data, status, settings) {
                    var arr = data;
                    for (var i = 0; i < arr.length; i++) {
                        var subcategory = arr[i];
                        var opt = document.createElement('option');
                        opt.innerHTML = subcategory['subCategoryName'];
                        opt.value = subcategory['subCategoryName'];
                        document.getElementById('subCategory').appendChild(opt);
                    }
                }
            })
        }

        function deleteOptions() {
            var parent = document.getElementById('subCategory');
            var nodes = parent.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var elem = nodes[i];
                if (elem.nodeValue != '') {
                    parent.removeChild(elem);
                    i--;
                }
            }
        }
    </script>
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
        <div class="card bg-light mb-3" style="max-width: 50rem;">
            <div class="card-header"><h5><b>${labelAddExpenses}</b></h5></div>
            <div class="card-body">

                <form:form action="${pageContext.request.contextPath}/expense/add" modelAttribute="expense" method="post">
                    <form:input path="id" name="id" type="hidden"/>
                    <div class="row">
                        <div class="col-sm-5">
                            <div class="form-group">
                                <label for="amount">${labelAmount}</label>
                                <div class="input-group mb-3">
                                    <form:input path="amount" id="amount" type="text" class="form-control" placeholder="${messageAmount}"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">${currency}</span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="category">${labelCategory}</label>
                                <form:select path="categoryName" class="form-control" id="category" onchange="getSubcategories()">
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