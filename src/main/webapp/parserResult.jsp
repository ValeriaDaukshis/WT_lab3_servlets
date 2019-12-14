<%@ page import ="java.util.List"%>
<%@ page import ="Beans.Book"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>Parse result</h2>
<c:choose>
<c:when test="${list.size() > 0}">
<table>
	    <tr>
		    <td>ID</td>
		    <td>Name</td>
		    <td>Author</td>
		    <td>Pages</td>
		    <td>Price</td>
	    </tr>

	    <c:forEach items="${list}" var = "b">
                <tr>
                    <td>${b.getId()}</td>
                    <td>${b.getName()}</td>
                    <td>${b.getAuthor()}</td>
                    <td>${b.getPages()}</td>
                    <td>${b.getPrice()}</td>
                </tr>
            </c:forEach>

    </table>
 </c:when>
 <c:otherwise>
     <p>There is no books</p>
   </c:otherwise>
</c:choose>
<a href="/index">Back</a>
</body>
</html>