<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<u:page title="Users list">
    <h1>Users list</h1>
    <ul>
        <c:forEach var="user" items="${users}">
            <c:url var="userEditUrl" value="/user/edit.html">
                <c:param name="id" value="${user.id}"/>
            </c:url>
            <li>
                    ${user.login} [${user.role.name}]
                <a href="${userEditUrl}">Изменить</a>
            </li>
        </c:forEach>
    </ul>
    <c:url var="userEditUrl" value="/user/edit.html"/>
    <a href="${userEditUrl}">Добавить</a>
</u:page>
