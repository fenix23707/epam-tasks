<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>


<u:page title="Список рабочих">
    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th>Имя</th>
                <th>Специальность</th>
            </tr>
            </thead>

        </table>
        <div class="scroll-table-body">
            <table>
                <tbody>
                <c:forEach var="worker" items="${workers}">

                    <tr>
                        <td>${worker.name}</td>
                        <td>${worker.specialization.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:url var="workplanListUrl" value="/workplan/list.html"/>
    <form action="${workplanListUrl}" method="get">

        <button type="submit"> Назад</button>
    </form>
</u:page>
