<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${not empty dispatcher}">
        <c:set var="title" value="Редактирование диспетчера ${dispatcher.login}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление нового диспетчера"/>
    </c:otherwise>
</c:choose>
<c:url var="dispatcherSaveUrl" value="/dispatcher/save.html"/>
<u:page title="${title}">
    <form action="${dispatcherSaveUrl}" method="post">
        <c:if test="${not empty dispatcher}">
            <input type="hidden" name="id" value="${dispatcher.id}">
        </c:if>
        <div>
            login:
            <input type="text" name="login" value="${dispatcher.login}">
        </div>
        <div>
            password:
            <input type="password" name="password">
        </div>
        <div>
            name:
            <input type="text" name="name" value="${dispatcher.name}" pattern="([А-Яа-яA-Za-z]+ ){2}[А-Яа-яA-Za-z]+">
        </div>
        <button type="submit">Сохранить</button>
        <c:url var="userDeleteUrl" value="/user/delete.html"/>
        <button formaction="${userDeleteUrl}" formmethod="post">Удалить</button>
    </form>
    </form>
    <c:url var="userListUrl" value="/user/list.html"/>
    <form action="${userListUrl}" method="get">
        <button type="submit">Назад</button>
    </form>
</u:page>