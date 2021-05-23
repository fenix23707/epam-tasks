<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<u:page title="Информация о заявке" errorMsg="${param.message}">

    <p>${request.description}</p>
    <c:url var="workPlanListUrl" value="/workplan/list.html"/>
    <form action="${workPlanListUrl}" method="get">
            <button type="submit">Назад</button>
    </form>
</u:page>
