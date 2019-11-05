<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href='<c:url value="/resources/css/bootstrap.min.css" />' rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/dashboard"/>"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nbcomputer}"></c:out> Computers found 
            </h1>
            <c:if test="${not empty response}">
	            <div class="alert alert-danger">
					<c:out value="${response}"/>
					<br/>
				</div>
			</c:if>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="<c:out value="${search}"/>" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                            <a href="dashboard?page=${actPage}&order=ASC<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">
                            	<i class="fa fa-chevron-down"></i>
                            </a>
                            <a href="dashboard?page=${actPage}&order=DESC<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
                            
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach items="${listComputer}" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value='<c:out value="${computer.id}"/>'>
                        </td>
                        <td>
                            <a href="editComputer?computer=${computer.id}" onclick=""><c:out value="${computer.name}"/></a>
                        </td>
                        <td><c:out value="${computer.introduced}"/></td>
                        <td><c:out value="${computer.discontinued}"/></td>
                        <td><c:out value="${computer.company.name}"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
	        	<c:if test="${actPage+1>2}">
		            <li class="page-item">
		               	<a href="dashboard?page=${actPage-1}&limite=${limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>" aria-label="Previous">
		               	<span aria-hidden="true">&laquo;</span>
		            	</a>
		            </li>
	            </c:if>
	             
	            <c:if test="${actPage > 1 }">
	            	<li class="page-item"><a href="dashboard?limite=${limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="1"/></a></li>
	            </c:if>
	              
	            <c:if test="${actPage >4}">
	            	<li class="page-item"><a>...</a></li>
	            </c:if>
	              
	            <c:if test="${actPage-2 > 1 }">
	            	<li class="page-item"><a href="dashboard?page=${actPage-2}&order=<c:out value="${order}"/>&limite=${limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${actPage-2}"/></a></li>
	            </c:if>
	              
	            <c:if test="${actPage-1 > 1 }">
	            	<li class="page-item"><a href="dashboard?page=${actPage-1}&order=<c:out value="${order}"/>&limite=${limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${actPage-1}"/></a></li>
	            </c:if>
	              
	            <li class="page-item active"><a href="dashboard?page=${actPage}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${actPage}"/></a></li>
	              
	            <c:if test="${actPage+1 < nbPage }">
	            	<li class="page-item"><a href="dashboard?page=${actPage+1}&order=<c:out value="${order}"/>&limite=${limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${actPage+1}"/></a></li>
	            </c:if>
	              
	            <c:if test="${actPage+2 < nbPage }">
              	<li class="page-item"><a href="dashboard?page=${actPage+2}&order=<c:out value="${order}"/>&limite=${limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${actPage+2}"/></a></li>
				</c:if>
				<c:if test="${actPage+3 < nbPage}">
              		<li class="page-item"><a>...</a></li>
              	</c:if>
              	
              	<c:if test="${actPage < nbPage }">
	            	<li class="page-item"><a href="dashboard?page=${nbPage}&order=<c:out value="${order}"/>&limite=${limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${nbPage}"/></a></li>
	            </c:if>

				<c:if test="${actPage< nbPage }">
		            <li class="page-item">
		                <a href="dashboard?page=${actPage+1}&limite=${limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>" aria-label="Next">
		                    <span aria-hidden="true">&raquo;</span>
		                </a>
		            </li>
	            </c:if>
	        </ul>
		
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <a type="submit" class="btn btn-default" href="dashboard?limite=10&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">10</a>
	            <a type="submit" class="btn btn-default" href="dashboard?limite=50&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">50</a>
	            <a type="submit" class="btn btn-default" href="dashboard?limite=100&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">100</a>
	        </div>
		</div>
    </footer>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/dashboard.js" />"></script>

</body>
</html>