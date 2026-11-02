
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.example.DBConnection" %>

<html>
<head>
    <title>My Shopping Cart</title>
</head>
<body>

<h2>My Shopping Cart</h2>

<%
ArrayList<Integer> cart =
    (ArrayList<Integer>) session.getAttribute("cart");

if (cart != null && !cart.isEmpty()) {

    double total = 0;

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
             "SELECT name, price FROM products WHERE id=?")) {

        for (int id : cart) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double price = rs.getDouble("price");
                    total += price;
%>

<p>
    Product: <%= rs.getString("name") %><br>
    Price: Rs. <%= price %>
</p>
<hr>

<%
                }
            }
        }
%>

<h3>Total Amount: Rs. <%= total %></h3>

<form action="checkout" method="post">
    <input type="submit" value="Confirm Checkout">
</form>

<%
    } catch (Exception e) {
        out.println("Error loading cart items.");
    }

} else {
%>

<p>Your cart is empty!</p>

<%
}
%>

<br>
<a href="index.jsp">Continue Shopping</a>

</body>
</html>
