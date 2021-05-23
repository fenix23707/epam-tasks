<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ page import="by.vsu.entities.RequestStatus " %>

<u:page title="Список необработанных заявок">
    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th>Описание</th>
                <th>Дата</th>
                <th>Обработать</th>
            </tr>
            </thead>

        </table>

        <div class="scroll-table-body">

            <table>
                <tbody>
                <c:forEach var="request" items="${requests}">
                    <tr>
                        <td>${request.description}</td>
                        <td>${request.startDay}</td>
                        <c:url var="workPlanEditUrl" value="/workplan/edit.html"/>
                        <td>
                            <form action="${workPlanEditUrl}" method="get">
                                <input type="hidden" name="request_id" value="${request.id}">
                                <button type="submit">Обработать</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:url var="workPlanListUrl" value="/workplan/list.html"/>
    <form action="${workPlanListUrl}" method="get">
        <button type="submit">Назад</button>
    </form>

    <%--<a href="${requestCreateUrl}">Создать</a>--%>
</u:page>
