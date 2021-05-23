<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ page import="by.vsu.entities.RequestStatus " %>
<%@ page import="by.vsu.entities.Role" %>

<u:page title="Рабочий план" errorMsg="${param.message}">
    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th>Описание</th>
                <th>Статус</th>
                <th>Начало</th>
                <th>Конец</th>
                <th>Количество часов</th>
                <th>Жилец</th>
                <th>Рабочие</th>
                <c:choose>
                    <c:when test="${session_user.role eq Role.DISPATCHER}">
                        <th>Изменить</th>
                    </c:when>
                    <c:when test="${session_user.role eq Role.WORKER}">
                        <th>Изменить статус</th>
                    </c:when>

                </c:choose>
            </tr>
            </thead>

        </table>

        <div class="scroll-table-body">

            <table>
                <tbody>
                <c:forEach var="work_plan" items="${work_plans}">
                    <c:choose>
                        <c:when test="${work_plan.request.status eq RequestStatus.CREATED}">
                            <c:set var="color" value="red"/>
                        </c:when>
                        <c:when test="${work_plan.request.status eq RequestStatus.PROCESSED}">
                            <c:set var="color" value="yellow"/>
                        </c:when>
                        <c:when test="${work_plan.request.status eq RequestStatus.COMPLETED}">
                            <c:set var="color" value="orange"/>
                        </c:when>
                        <c:when test="${work_plan.request.status eq RequestStatus.CONFIRMED}">
                            <c:set var="color" value="green"/>
                        </c:when>
                    </c:choose>
                    <tr>
                        <td>
                            <c:url var="requestInfoUrl" value="/request/info.html"/>
                            <form action="${requestInfoUrl}" method="get">
                                <input type="hidden" name="request_id" value="${work_plan.request.id}">
                                <button type="submit">Просмотреть</button>
                            </form>
                        </td>
                        <td style="background: ${color};">${work_plan.request.status.name}</td>
                        <td>${work_plan.request.startDay}</td>
                        <td>${work_plan.endDay}</td>
                        <td>${work_plan.workScope}</td>
                        <td>
                            <c:url var="tenantInfoUrl" value="/tenant/info.html"/>
                            <form action="${tenantInfoUrl}" method="get">
                                <input type="hidden" name="tenant_id" value="${work_plan.request.tenant.id}">
                                <button type="submit">Информация</button>
                            </form>
                        </td>
                        <c:url var="workerListByWorkPlanIdUrl" value="/worker/list_by_work_plan_id.html"/>
                        <td>
                            <form action="${workerListByWorkPlanIdUrl}" method="get">
                                <input type="hidden" name="work_plan_id" value="${work_plan.id}">
                                <button type="submit">Просмотреть</button>
                            </form>
                        </td>


                        <c:choose>
                            <c:when test="${session_user.role eq Role.DISPATCHER}">
                                <td>
                                    <c:url var="workplanEditdUrl" value="/workplan/edit.html"/>
                                    <c:choose>
                                        <c:when test="${work_plan.request.status eq RequestStatus.PROCESSED}">
                                            <c:remove var="disabled"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set value="disabled" var="disabled"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <form action="${workplanEditdUrl}" method="get">
                                        <input type="hidden" name="work_plan_id" value="${work_plan.id}">
                                        <input type="hidden" name="request_id" value="${work_plan.request.id}">
                                        <button type="submit" ${disabled}>Изменить</button>
                                    </form>
                                </td>
                            </c:when>
                            <c:when test="${session_user.role eq Role.WORKER}">
                                <td>
                                    <c:url var="requestChangeStatusUrl" value="/request/change_status.html"/>
                                    <c:choose>
                                        <c:when test="${work_plan.request.status eq RequestStatus.PROCESSED}">
                                            <c:remove var="disabled"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set value="disabled" var="disabled"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <form action="${requestChangeStatusUrl}" method="get">
                                        <input type="hidden" name="request_id" value="${work_plan.request.id}">
                                        <button type="submit" ${disabled}>Выполнить</button>
                                    </form>
                                </td>
                            </c:when>
                        </c:choose>


                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <c:choose>
        <c:when test="${session_user.role eq Role.DISPATCHER}">
            <c:url var="workplanCreatedUrl" value="/workplan/create.html"/>
            <form action="${workplanCreatedUrl}" method="get">

                <button type="submit"> Добавить</button>
            </form>
        </c:when>
    </c:choose>

    <%--<a href="${requestCreateUrl}">Создать</a>--%>
</u:page>
