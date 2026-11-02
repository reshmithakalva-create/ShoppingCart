
package org.example;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        ArrayList<Integer> cart =
                (ArrayList<Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        int id = Integer.parseInt(request.getParameter("id"));

        cart.add(id);

        session.setAttribute("cart", cart);

        response.sendRedirect("view_cart.jsp");
    }
}
