<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<c:url var="requestSaveUrl" value="/request/save.html"/>
<u:page title="Создание заявки">
    <form action="${requestSaveUrl}" method="post">
       <div>
           описание:
           <input type="text" name="description" maxlength="200">
       </div>

        <div>
            desired day:
            <input type="date" name="desired_day" min="${today}" value="${today}">
        </div>

        <button type="submit">Сохранить</button>
        <c:url var="requestSaveUrl" value="/request/save.html"/>

        <button formaction="${requestSaveUrl}" formmethod="post">Удалить</button>
    </form>
</u:page>