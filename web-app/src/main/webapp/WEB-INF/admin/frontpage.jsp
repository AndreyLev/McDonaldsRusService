<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Admin panel</title>
  <%@include file="../bootstrap-css.jsp" %>
</head>
<body>

<div class="container">
  <h1>Admin panel</h1>

    <a class="btn btn-primary" href="/" role="button">Go to home page</a>

  <div class="row">
    <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.ITEMS)) { %>
    <div class="col-3" align="center">
      <div class="card mt-3">
        <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="<%= item.getName() %>">
        <div class="card-body">
          <h5 class="card-title"><a href = "<%= request.getContextPath() %>/product/item?id=<%=item.getId()%>"><%= item.getName() %></a>
          </h5>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Price: <%= item.getPrice() %> rub.</li>
            <li class="list-group-item">Quantity: <%= item.getQuantity() %></li>
          </ul>
          <a href="<%= request.getContextPath() %>/admin/edit?id=<%= item.getId()%>" class="btn btn-primary">Edit</a>
        </div>
      </div>
    </div>
    <% } %>
  </div>


  <% if (request.getAttribute(Constants.ITEM) == null) { %>
  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input name="id" type="hidden" value="0">
    <div class="form group">
      <label for="name">Product Name</label>
      <input type="text" id="name" name="name">
    </div>
    <div class="form group">
      <label for="price">Product Price</label>
      <input type="number" min="0" id="price" name="price">
    </div>
    <div class="form group">
      <label for="quantity">Product Quantity</label>
      <input type="number" min="0" id="quantity" name="quantity">
    </div>
      <div class="form group">
          <label for="price">Product ImageUrl</label>
          <input type="text" min="0" id="imageUrl" name="image-url">
      </div>
      <div class="form group">
          <label for="price">Product About</label>
          <input type="text" min="0" id="about" name="about">
      </div>
    <button class="btn btn-primary">Add</button>
  </form>
  <% } %>

  <% if (request.getAttribute(Constants.ITEM) != null) { %>
  <% ProductModel item = (ProductModel) request.getAttribute(Constants.ITEM); %>
  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input name="id" type="hidden" value="<%= item.getId() %>">
    <div class="form group">
      <label for="name">Product Name</label>
      <input type="text" id="name" name="name" value="<%= item.getName() %>">
    </div>
    <div class="form group">
      <label for="price">Product Price</label>
      <input type="number" min="0" id="price" name="price" value="<%= item.getPrice() %>">
    </div>
    <div class="form group">
      <label for="quantity">Product Quantity</label>
      <input type="number" min="0" id="quantity" name="quantity" value="<%= item.getQuantity() %>">
    </div>
      <div class="form group">
          <label for="price">Product ImageUrl</label>
          <input type="text" min="0" id="imageUrl" name="image-url" value="<%=item.getImageUrl()%>">
      </div>
      <div class="form group">
          <label for="price">Product About</label>
          <input type="text" min="0" id="about" name="about" value="<%=item.getAbout()%>">
      </div>
    <button class="btn btn-primary">Save</button>
  </form>
  <% } %>
</div>

</body>
</html>
