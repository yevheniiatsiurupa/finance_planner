<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:message code="message.welcome" var="welcomeMessage"/>
    <spring:message code="label.page.expenses" var="labelExpensesPage"/>
    <spring:message code="label.page.welcome" var="labelWelcomePage"/>
    <spring:message code="label.page.expenses.add" var="labelAddExpenses"/>
    <title>${labelWelcomePage}</title>

</head>
<body>
<br/>
<br/>
<h2>${welcomeMessage}</h2>
<br/>
<a href="${pageContext.request.contextPath}/expense/all">${labelExpensesPage}</a>
<br/>
<a href="${pageContext.request.contextPath}/expense/add">${labelAddExpenses}</a>
</body>
</html>