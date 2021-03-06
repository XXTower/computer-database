<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
            <a class="navbar-brand" href="<c:url value="/computers"/>"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${page.nbComputer}"></c:out> <spring:message code="dashboard.nbComputer"/>
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

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="dashboard.searchbox" />" value="<c:out value="${search}"/>" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="dashboard.btnSearch" />"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="<c:url value="/computers/addComputer"/>"><spring:message code="dashboard.btajouter" /></a> 
                    <a class="btn btn-default" id="editComputer" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" method="POST" action='<c:url value="/computers/delete" />'>
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
                            <spring:message code="dashboard.computerName" />
                            <a href="computers?actPage=${page.actPage}&order=ASC<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">
                            	<i class="fa fa-chevron-down"></i>
                            </a>
                            <a href="computers?actPage=${page.actPage}&order=DESC<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
                            
                        </th>
                        <th>
                        <spring:message code="dashboard.computerIntroDate" />
                            
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        <spring:message code="dashboard.computerDisDate" />
                            
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<spring:message code="dashboard.computerCompany" />
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
                            <a href="computers/editComputer/${computer.id}" onclick=""><c:out value="${computer.name}"/></a>
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
	        	<c:if test="${page.actPage+1>2}">
		            <li class="page-item">
		               	<a href="computers?actPage=${page.actPage-1}&limite=${page.limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>" aria-label="Previous">
		               	<span aria-hidden="true">&laquo;</span>
		            	</a>
		            </li>
	            </c:if>
	             
	            <c:if test="${page.actPage > 1 }">
	            	<li class="page-item"><a href="computers?limite=${page.limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="1"/></a></li>
	            </c:if>
	              
	            <c:if test="${page.actPage >4}">
	            	<li class="page-item"><a>...</a></li>
	            </c:if>
	              
	            <c:if test="${page.actPage-2 > 1 }">
	            	<li class="page-item"><a href="computers?actPage=${page.actPage-2}&order=<c:out value="${order}"/>&limite=${page.limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.actPage-2}"/></a></li>
	            </c:if>
	              
	            <c:if test="${page.actPage-1 > 1 }">
	            	<li class="page-item"><a href="computers?actPage=${page.actPage-1}&order=<c:out value="${order}"/>&limite=${page.limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.actPage-1}"/></a></li>
	            </c:if>
	              
	            <li class="page-item active"><a href="computers?actPage=${page.actPage}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.actPage}"/></a></li>
	              
	            <c:if test="${page.actPage+1 < page.maxPage }">
	            	<li class="page-item"><a href="computers?actPage=${page.actPage+1}&order=<c:out value="${order}"/>&limite=${page.limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.actPage+1}"/></a></li>
	            </c:if>
	              
	            <c:if test="${page.actPage+2 < page.maxPage }">
              	<li class="page-item"><a href="computers?actPage=${page.actPage+2}&order=<c:out value="${order}"/>&limite=${page.limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.actPage+2}"/></a></li>
				</c:if>
				<c:if test="${page.actPage+3 < page.maxPage}">
              		<li class="page-item"><a>...</a></li>
              	</c:if>
              	
              	<c:if test="${page.actPage < page.maxPage }">
	            	<li class="page-item"><a href="computers?actPage=${page.maxPage}&order=<c:out value="${order}"/>&limite=${page.limite}<c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>"><c:out value="${page.maxPage}"/></a></li>
	            </c:if>

				<c:if test="${page.actPage < maxPage }">
		            <li class="page-item">
		                <a href="computers?page=${page.actPage+1}&limite=${page.limite}&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>" aria-label="Next">
		                    <span aria-hidden="true">&raquo;</span>
		                </a>
		            </li>
	            </c:if>
	        </ul>
		
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <a type="submit" class="btn btn-default" href="computers?limite=10&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">10</a>
	            <a type="submit" class="btn btn-default" href="computers?limite=50&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">50</a>
	            <a type="submit" class="btn btn-default" href="computers?limite=100&order=<c:out value="${order}"/><c:if test="${not empty search}">&search=<c:out value="${search}"/></c:if>">100</a>
	        </div>
		</div>
    </footer>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/dashboard.js" />"></script>

</body>
</html>