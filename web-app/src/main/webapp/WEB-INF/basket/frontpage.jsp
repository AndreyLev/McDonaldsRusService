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
    <title>Product basket</title>
    <%@include file="../bootstrap-css.jsp"%>
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
    <h1>Product basket</h1>

        <a class="btn btn-primary" href="/" role="button">Go to product selection</a>

    <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute("ordered-items"); %>
        <% int totalAmount = (Integer) request.getAttribute("total-amount"); %>
        <% int totalQuantity = (Integer) request.getAttribute("total-quantity"); %>
        <p><b>Total in basket: </b><%= totalQuantity %><p><b> Total Amount: <%=totalAmount%> rub.</b></p></p>

    <% ProductModel desiredModel = null; %>
    <% List<ProductModel> models = (List<ProductModel>) request.getAttribute("items"); %>
        <div class="row">
            <% for (OrderPositionModel item : (List<OrderPositionModel>) request.getAttribute(Constants.ORDERED_ITEMS)) { %>
            <%
               for (ProductModel mod :models) {
                   if (mod.getId() == item.getProductId()) {
                       desiredModel = mod;
                   }
               }
            %>
            <div class="col-3" align="center">
                <div class="card mt-3">
                    <img src="<%= desiredModel.getImageUrl() %>" class="card-img-top" alt="<%= desiredModel.getName() %>">
                    <div class="card-body">
                        <h5 class="card-title"><a href = "<%= request.getContextPath() %>/product/item?id=<%=desiredModel.getId()%>"><%= desiredModel.getName() %></a>
                        </h5>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Price: <%= item.getProductPrice() %> rub.</li>
                        </ul>
                        <p><form action="<%= request.getContextPath() %>" method="post" align="Center">
                            <input name="order-position-id" type="hidden" value="<%= item.getId() %>">
                            <input name="product-id" type="hidden" value="<%= item.getProductId() %>">
                            <input name="product-name" type="hidden" value="<%= item.getProductName() %>">
                            <input name="product-price" type="hidden" value="<%= item.getProductPrice() %>">
                            <div class="form group">
                                <label for="quantity">Product quantity</label>
                                <input type="number" min="0" id="quantity" name="quantity" value="<%=item.getProductQuantity()%>">
                            </div>
                        <p><button class="btn btn-primary">Change product quantity</button></p>
                    </form>
                        <a href="<%= request.getContextPath() %>/basket/delete?id=<%= item.getId()%>" class="btn btn-primary">Remove product</a>
                    </div>
                </div>
            </div>
            <% } %>
        </div>

</div>

</body>
</html>
