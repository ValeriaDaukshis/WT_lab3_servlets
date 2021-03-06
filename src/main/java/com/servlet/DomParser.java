package com.servlet;

import Beans.Book;
import Parsers.XmlDeserializer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dom")
public class DomParser extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fileName = (String) session.getAttribute("fileName");
        List<Book> list = new XmlDeserializer().DomParser(fileName);

        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/parserResult.jsp");
        requestDispatcher.forward(request, response);
    }
}
