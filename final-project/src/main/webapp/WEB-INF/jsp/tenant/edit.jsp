<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${not empty tenant}">
        <c:set var="title" value="Редактирование жильца ${tenant.login}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление нового жильца"/>
    </c:otherwise>
</c:choose>
<c:url var="tenantSaveUrl" value="/tenant/save.html"/>
<u:page title="${title}">
    <form action="${tenantSaveUrl}" method="post">
        <c:if test="${not empty tenant}">
            <input type="hidden" name="id" value="${tenant.id}">
        </c:if>
        <div>
            login:
            <input type="text" name="login" value="${tenant.login}">
        </div>
        <div>
            password:
            <input type="text" name="password" value="${tenant.password}">
        </div>
        <div>
            name:
            <input type="text" name="name" value="${tenant.name}" pattern="([А-Яа-яA-Za-z]+ ){2}[А-Яа-яA-Za-z]+">
        </div>
        <div>
            address:
            <input type="text" name="address" value="${tenant.address}">
        </div>
        <div>
            apartment number:
            <input type="text" name="apartment_number" value="${tenant.apartmentNumber}" pattern="[1-9][0-9]?">
        </div>
        <button type="submit">Сохранить</button>

    </form>
    <c:url var="userDeleteUrl" value="/user/delete.html"/>
    <form action="${userDeleteUrl}" method="get">
        <input type="hidden" name="id" value="${tenant.id}">
        <button type="submit">Удалить</button>
    </form>
    <c:url var="userListUrl" value="/user/list.html"/>
    <form action="${userListUrl}" method="get">
        <button type="submit">Назад</button>
    </form>
</u:page>