<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${not empty worker}">
        <c:set var="title" value="Редактирование рабочего плана ${worker.login}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Создание рабочего плана"/>
    </c:otherwise>
</c:choose>
<c:url var="workplanSaveUrl" value="/workplan/save.html"/>
<u:page title="${title}">
    <form action="${workplanSaveUrl}" method="post">
        <c:if test="${not empty work_plan}">
            <input type="hidden" name="work_plan_id" value="${work_plan.id}">
        </c:if>
        <input type="hidden" name="request_id" value="${request.id}">
        <div>
            начало:
            <input type="date" name="start_day" min="${request.startDay}" value="${request.startDay}">
        </div>
        <div>
            конец:
            <input type="date" name="end_day" min="${today}" value="${work_plan.endDay}">
        </div>
        <div>
            количество часов:
            <input type="text" name="work_scope" value="${work_plan.workScope}" pattern="[1-9][0-9]?">
        </div>
        <div>
            рабочие:
            <c:forEach var="worker" items="workers">
                <div class="scroll-table">
                    <table>
                        <thead>
                        <tr>
                            <th>Выбрать</th>
                            <th>Имя</th>
                            <th>Специализация</th>
                        </tr>
                        </thead>

                    </table>

                    <div class="scroll-table-body">

                        <table>
                            <tbody>
                            <c:forEach var="worker" items="${workers}">
                                <tr>
                                    <td><input type="checkbox" value="${worker.id}" name="workers_id"></td>
                                    <td>${worker.name}</td>
                                    <td>${worker.specialization.name}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:forEach>

        </div>

        <button type="submit">Сохранить</button>
    </form>
    <c:url var="requestDeleteUrl" value="/request/delete.html"/>
    <c:if test="${empty work_plan}">
        <form action="${requestDeleteUrl}" method="post">
            <input type="hidden" name="request_id" value="${request.id}">
            <button type="submit">Удалить</button>
        </form>
    </c:if>
    <c:url var="workPlanListUrl" value="/workplan/list.html"/>

    <form action="${workPlanListUrl}" method="get">
        <button type="submit">В начало</button>
    </form>

</u:page>