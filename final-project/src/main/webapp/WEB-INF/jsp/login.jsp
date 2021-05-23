<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<u:page title="Авторизация" errorMsg="${param.message}">
    <c:url var="loginUrl" value="/login.html"/>
    <form action="${loginUrl}" method="post">
        <div>
            login:
            <input type="text" name="login" value="${user.login}">
        </div>
        <div>
            password:
            <input type="text" name="password" value="${user.password}">
        </div>

        <button type="submit">Войти</button>
       
    </form>
</u:page>
