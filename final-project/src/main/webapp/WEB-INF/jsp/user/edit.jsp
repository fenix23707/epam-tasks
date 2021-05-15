<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${not empty user}">
        <c:set var="title" value="Редактирование пользователя ${user.login}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление нового пользователя"/>
    </c:otherwise>
</c:choose>

<u:page title="${title}">
    <h1>${title}</h1>
    <c:url var="userSaveUrl" value="/user/save.html"/>
    <form action="${userSaveUrl}" method="post">
        <c:if test="${not empty user}">
            <input type="hidden" name="id" value="${user.id}">
        </c:if>
        <div>
            login:
            <input type="text" name="login" value="${user.login}">
        </div>
        <div>
            password:
            <input type="text" name="password" value="${user.password}">
        </div>
        <div>
            role:
            <select name="role">
                <c:forEach var="role" items="${roles}">
                    <c:choose>
                        <c:when test="${role == user.role}">
                            <c:set var="selected" value="selected"/>
                        </c:when>
                        <c:otherwise>
                            <c:remove var="selected"/>
                        </c:otherwise>
                    </c:choose>
                    <option value="${role}" ${selected} >${role.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit">Сохранить</button>
        <c:url var="userDeleteUrl" value="/user/delete.html"/>
        <button formaction="${userDeleteUrl}" formmethod="post">Удалить</button>
    </form>
</u:page>
