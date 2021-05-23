<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page isELIgnored="false" %>

<c:choose>
    <c:when test="${not empty worker}">
        <c:set var="title" value="Редактирование рабочего ${worker.login}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление нового рабочего"/>
    </c:otherwise>
</c:choose>
<c:url var="workerSaveUrl" value="/worker/save.html"/>
<u:page title="${title}">
    <form action="${workerSaveUrl}" method="post">
        <c:if test="${not empty worker}">
            <input type="hidden" name="id" value="${worker.id}">
        </c:if>
        <div>
            login:
            <input type="text" name="login" value="${worker.login}">
        </div>
        <div>
            password:
            <input type="password" name="password">
        </div>
        <div>
            name:
            <input type="text" name="name" value="${worker.name}" pattern="([А-Яа-яA-Za-z]+ ){2}[А-Яа-яA-Za-z]+">
        </div>
        <div>
            specialization:
            <select name="specialization">
                <c:forEach var="spec" items="${specializations}">
                    <option  value="${spec}">${spec.name}</option>
                </c:forEach>
            </select>
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