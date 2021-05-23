<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag language="java" pageEncoding="UTF-8" %>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="errorMsg" required="false" rtexprvalue="true" type="java.lang.String" %>

<c:url var="cssUrl" value="/css/style.css"/>
<c:url var="logoutUrl" value="/logout.html"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${cssUrl}">
    <title>${title}</title>

</head>
<body>
<div class="rightSide">
    <c:if test="${not empty session_user}">
        <form  action="${logoutUrl}" method="get">
            <button class="logoutButton" type="submit">Выйти</button>
        </form>
    </c:if>
</div>
<div class="content">
    <h1>${title}</h1>


    <c:if test="${not empty errorMsg}">
        <p class="errorMsg">
                ${errorMsg}
        </p>
    </c:if>

    <jsp:doBody/>

</div>
</body>
</html>