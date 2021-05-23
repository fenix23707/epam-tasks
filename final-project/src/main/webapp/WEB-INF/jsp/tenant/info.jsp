<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>

<u:page title="Информация о жильце" errorMsg="${param.message}">

    <div class="scroll-table">
        <table>
            <thead>
            <tr>
                <th>Имя</th>
                <th>Адрес</th>
                <th>Номер квартиры</th>
            </tr>
            </thead>
        </table>

        <div class="scroll-table-body">
            <table>
                <tbody>


                <tr>
                    <c:url var="userEditUrl" value="/user/edit.html">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <td>${tenant.name}</td>
                    <td>${tenant.address}</td>
                    <td>${tenant.apartmentNumber}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <c:url var="workPlanListUrl" value="/workplan/list.html"/>
    <form action="${workPlanListUrl}" method="get">
            <button type="submit">Назад</button>
        </div>

    </form>
</u:page>
