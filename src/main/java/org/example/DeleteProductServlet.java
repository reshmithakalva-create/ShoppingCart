package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM products WHERE id=?");

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            con.close();

            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}