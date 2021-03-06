<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 10.09.2018
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <style>
        DIV.table {
            display: table;
            align-content: right;
            width: 80%
        }

        FORM.tr, DIV.tr {
            display: table-row;
        }

        SPAN.td {
            display: table-cell;
        }

        #made {
            padding-top: 2%;
        }
    </style>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        var today;
        var photo;
        var id_brand;
        var myFilter = {today: today, photo: photo, idBrand: id_brand};
        $(document).ready(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
            $.ajax({
                method: 'GET',
                dataType: 'json',
                url: '/filterFlist',
                success: function (data) {
                    var content = '<option value="off" selected>Выберите марку</option>';
                    var array = data.array;
                    myFilter.today = data.today;
                    myFilter.idBrand = data.idBrand;
                    myFilter.photo = data.havePhoto;
                    if (myFilter.today == 'on') {
                        document.getElementById("today").checked = true;
                    }
                    if (myFilter.photo == 'on') {
                        document.getElementById("photo").checked = true;
                    }
                    array.forEach(function (brand, index, array) {
                        if (myFilter.idBrand == brand.id) {
                            content += '<option value=\"' + brand.id + '\" selected>' + brand.brand + '</option>';
                        } else {
                            content += '<option value=\"' + brand.id + '\">' + brand.brand + '</option>';
                        }
                    });
                    $('#brands').empty().html(content);
                },
                async: false,
            })
            $.ajax({
                method: 'POST',
                dataType: 'json',
                url: '/list',
                data: JSON.stringify(myFilter),
                contentType: 'application/json',
                success: function (data) {
                    var cars = data.array;
                    var content = " <div class=\"tr\">\n" +
                        "       <span class=\"td\">\n" +
                        "          Модель\n" +
                        "    </span>\n" +
                        "        <span class=\"td\">\n" +
                        "          Цена\n" +
                        "    </span>\n" +
                        "        <span class=\"td\">\n" +
                        "          Статус\n" +
                        "    </span>\n" +
                        "        <span class=\"td\">\n" +
                        "          Фото\n" +
                        "    </span>\n" +
                        "    </div>";
                    var date = new Date();
                    var compareDate = date.getFullYear() + " " + date.getMonth() + " " + date.getDate();
                    cars.forEach(function (car, index, cars) {
                        content += "<form action=\"${pageContext.servletContext.contextPath}/desc\" method=\"POST\" class=\"tr\">" +
                            "<span class=\"td\"><input type=\"text\" name=\"model\" value=\"" + car.model + "\" disabled /></span>" +
                            "<span class=\"td\"><input type=\"text\" name=\"price\" value=\"" + car.price + "\" disabled /></span>" +
                            "<span class=\"td\"><input type=\"text\" name=\"status\" value=\"" + car.status + "\" disabled /></span>" +
                            "<span class=\"td\"><input type=\"image\" src=\"" + car.photo + "\" width=\"30%\" \n" +
                            "   height=\"25%\"  alt=\"photo\" /></span>" + "<input type=\"hidden\" name=\"carId\" value=\"" +
                            car.id + "\"/> <input type=\"hidden\" name=\"${_csrf.parameterName}\"\n" +
                            "           value=\"${_csrf.token}\" /></form>";
                    })
                    $('#table').empty().html(content);
                }
            })
        })
    </script>
</head>
<body>
<c:url value="/logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
<c:if test="${not empty msg}">
    <div class="msg">${msg}</div>
</c:if>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        User : ${pageContext.request.userPrincipal.name} | <a
            href="javascript:formSubmit()"> Logout</a>
    </h2>
</c:if>
<div style="float: left; margin-top: 200px;margin-right: 10px">
    <form:form action="/filterFlist" method="post">
        <input type="checkbox" name="photo" id="photo" onchange="submit()">only photo<br>
        <input type="checkbox" name="today" id="today" onchange="submit()">only today<br>
        <select id="brands" name="brands" onchange="submit()"></select>
    </form:form>
</div>
<div class="table" id="table">
</div>
</br>
<form:form action="/create" method="get">
    <p><input type="submit" value="Добавить авто" name="add" class="btn btn-default"></p>
</form:form>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

</body>
</html>
