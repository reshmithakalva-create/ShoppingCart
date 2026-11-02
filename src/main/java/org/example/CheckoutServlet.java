
package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        ArrayList<Integer> cart =
                (ArrayList<Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        response.setContentType("text/html");

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                for (int id : cart) {

                    double price;

                    try (PreparedStatement ps = con.prepareStatement(
                            "SELECT price, quantity FROM products WHERE id=? FOR UPDATE")) {

                        ps.setInt(1, id);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next() || rs.getInt("quantity") < 1) {
                                throw new SQLException("Product unavailable: " + id);
                            }
                            price = rs.getDouble("price");
                        }
                    }

                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO orders(product_id, quantity, total) VALUES(?,?,?)")) {

                        ps.setInt(1, id);
                        ps.setInt(2, 1);
                        ps.setDouble(3, price);
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE products SET quantity=quantity-1 WHERE id=?")) {

                        ps.setInt(1, id);
                        ps.executeUpdate();
                    }
                }

                con.commit();
                session.removeAttribute("cart");

                PrintWriter out = response.getWriter();
                out.println("<h2>Checkout Successful!</h2>");
                out.println("<a href='index.jsp'>Continue Shopping</a>");

            } catch (Exception e) {
                con.rollback();
                throw e;
            }

        } catch (Exception e) {
            throw new ServletException("Checkout failed", e);
        }
    }
}
