<%@ page import="static ru.javawebinar.topjava.util.MealsUtil.getMealsContent" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <jsp:useBean id="meals" scope="session" type="java.util.List"/>
    <c:forEach items="${meals}" var="m" varStatus="mealList">
        <c:choose>
            <c:when test="${m.excess == true}"><tr bgcolor="#FF0000"></c:when>
            <c:otherwise><tr></c:otherwise>
        </c:choose>
        <td>${mealList.index}</td>
        <td>${m.description}</td>
        <td>${m.calories}</td>
        <td>${m.dateTime}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>