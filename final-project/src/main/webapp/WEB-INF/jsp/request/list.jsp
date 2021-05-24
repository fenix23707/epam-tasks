<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ page import="by.vsu.entities.RequestStatus " %>

<u:page title="Список заявок для пользователя ${session_user.name}" errorMsg="${param.message}">
    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th>Описание</th>
                <th>Дата</th>
                <th>Статус</th>
                <th>Изменить статус</th>
            </tr>
            </thead>

        </table>

        <div class="scroll-table-body">

            <table>
                <tbody>
                <c:forEach var="request" items="${requests}">
                    <c:choose>
                        <c:when test="${request.status eq RequestStatus.CREATED}">
                            <c:set var="color" value="red"/>
                        </c:when>
                        <c:when test="${request.status eq RequestStatus.PROCESSED}">
                            <c:set var="color" value="yellow"/>
                        </c:when>
                        <c:when test="${request.status eq RequestStatus.COMPLETED}">
                            <c:set var="color" value="orange"/>
                        </c:when>
                        <c:when test="${request.status eq RequestStatus.CONFIRMED}">
                            <c:set var="color" value="green"/>
                        </c:when>
                    </c:choose>
                    <tr>


                        <td>${request.description}</td>
                        <td>${request.startDay}</td>
                        <td style="background: ${color};">${request.status}</td>
                        <td>
                            <c:url var="requestChangeStatusUrl" value="/request/change_status.html"/>
                            <form action="${requestChangeStatusUrl}" method="get">
                                <input type="hidden" name="request_id" value="${request.id}">
                                <c:choose>
                                    <c:when test="${request.status eq RequestStatus.COMPLETED.name}">
                                        <c:remove var="disabled"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set value="disabled" var="disabled"/>
                                    </c:otherwise>
                                </c:choose>
                                <button type="submit" ${disabled}>Подтвердить</button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:url var="requestAddUrl" value="/request/add.html"/>
    <form action="${requestAddUrl}" method="get">

        <button type="submit"> Создать</button>
    </form>
</u:page>
