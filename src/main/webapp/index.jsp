
<%@ page import="java.sql.*" %>
<%@ page import="org.example.DBConnection" %>

<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>

<h2>Available Products</h2>

<%
try (Connection con = DBConnection.getConnection();
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery("SELECT * FROM products")) {

    while (rs.next()) {
%>

<p>
    <p>
        Product ID: <%= rs.getInt("id") %><br>
        Product: <%= rs.getString("name") %><br>
        Price: Rs. <%= rs.getDouble("price") %><br>
        Available Quantity: <%= rs.getInt("quantity") %>
    </p>
</p>

<form action="cart" method="post">
    <input type="hidden" name="id"
           value="<%= rs.getInt("id") %>">
    <input type="submit" value="Add to Cart">
</form>

<hr>

<%
    }
} catch (Exception e) {
    out.println("Error loading products.");
}
%>

<a href="view_cart.jsp">View Cart</a>
<br><br>
<a href="logout">Logout</a>
<br><a href="add_product.jsp">Add New Product</a>
<br><a href="delete_product.jsp">Delete Product</a>
<br><a href="update_product.jsp">Update Product</a>

</body>
</html>
