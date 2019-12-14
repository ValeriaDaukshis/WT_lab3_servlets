<%@ page import ="java.util.List"%>
<%@ page import ="Beans.Book"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Parse result</h2>
<table>
	    <tr>
		    <td>ID</td>
		    <td>Name</td>
		    <td>Author</td>
		    <td>Pages</td>
		    <td>Price</td>
	    </tr>
    <%
    List<Book> list = (List<Book>)request.getAttribute("list");
    if (list != null && !list.isEmpty()) {
                for (Book b : list) {
                    out.println("<tr>");
                    out.println("<td>" + b.getId() + "</td>");
                    out.println("<td>" + b.getName() + "</td>");
                    out.println("<td>" + b.getAuthor() + "</td>");
                    out.println("<td>" + b.getPages() + "</td>");
                    out.println("<td>" + b.getPrice() + "</td>");
                    out.println("<tr>");
                }
            }
           else {
                out.println("<p>There are no books in file</p>");
           }
    %>
    </table>
<a href="/index">Back</a>
</body>
</html>