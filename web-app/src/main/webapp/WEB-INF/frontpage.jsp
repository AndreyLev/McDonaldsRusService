<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ! + Tab - emmet --%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Rosfood service</title>
  <%@include file="bootstrap-css.jsp"%>
</head>
<body>
<%-- Jasper --%>
<%-- tag + Tab --%>
<%-- tag{Content} + Tab --%>
<%-- tag{Content} + Tab --%>
<%-- tag#id - уникальный идентификатор на странице --%>
<%-- tag.class - строка, позволяющая логически группировать элементов --%>
<%-- tag[attr=value] - все остальные атрибуты --%>
<%-- null -> for non-existent attribute --%>
<div class="container">
  <%-- ul>li + Tab --%>
  <h1>Rosfood service</h1>

    <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute("ordered-items"); %>
    <% int totalAmount = (Integer) request.getAttribute("total-amount"); %>
    <% int totalQuantity = (Integer) request.getAttribute("total-quantity"); %>
    <p><b>Total in basket: <%= totalQuantity %> products</b><p><b> Total Amount: <%=totalAmount%> rub.</b></p></p>

    <% if (positions.size() > 0) {%>
    <a class="btn btn-primary" href="/basket" role="button">Go to product basket</a>
    <% }%>


  <div class="row">
  <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.ITEMS)) { %>
    <div class="col-3" align="center">
      <div class="card mt-3">
        <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="<%= item.getName() %>">
        <div class="card-body">
          <input name="id" type="hidden" value="<%=item.getId()%>">
          <h5 class="card-title"><a href = "<%= request.getContextPath() %>/product/item?id=<%=item.getId()%>"><%= item.getName() %></a>
          </h5>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Price: <%= item.getPrice() %> rub.</li>
          </ul>
          <p><form action="<%= request.getContextPath() %>" method="post" align="Center">
            <input name="id" type="hidden" value="<%= item.getId() %>">
            <div class="form group">
              <label for="quantity">Product quantity</label>
              <input type="number" min="0" id="quantity" name="quantity" value="1">
            </div>
            <p><button class="btn btn-primary" style="margin: 5px">Add to basket</button></p>
          </form></p>
        </div>
      </div>
    </div>
  <% } %>
  </div>
</div>

</body>
</html>
