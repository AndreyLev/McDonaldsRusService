<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDetailsDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ! + Tab - emmet --%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Product Details</title>
    <%@include file="../bootstrap-css.jsp"%>
    <style>
        .wrapper {
        .make-row();
        }
        .content-main {
        .make-lg-column(8);
        }
        .content-secondary {
        .make-lg-column(3);
        .make-lg-column-offset(1);
        }
    </style>
</head>
<body>

<div class="container-fluid">

    <h1>Product details</h1>

    <a class="btn btn-primary" href="/" role="button">Go to product selection</a>

    <% ProductModel item = (ProductModel) request.getAttribute("item");%>

    <h2><p> <img src="<%=item.getImageUrl()%>" class="img-fluid" alt="Responsive image" align = "top"><strong><b><%=item.getName()%></b></strong></p></h2>
</div>

    <h2>About:</h2>
   <h3><p><%=item.getAbout()%></p></h3>


</body>
</html>
