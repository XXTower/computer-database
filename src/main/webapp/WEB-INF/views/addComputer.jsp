<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
	<link href='<c:url value="/resources/css/bootstrap.min.css" />' rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" media="screen">
</head>
<style>
.error{color: #900;}
</style>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/dashboard"/>"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">	
            <div class="row">
            <c:if test="${not empty response}">
	            <div class="alert alert-danger">
					<c:out value="${response}"/>
					<br/>
				</div>
			</c:if>
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form:form action="addComputer" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <form:label for="computerName" path="name">Computer name</form:label>
                                <form:input path="name" required="required" type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name"/>
                            	<div class="error">${errors['computerName'] }</div>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced" for="introduced">Introduced date</form:label>
                                <form:input path="introduced" type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date" value='<c:out value="${param.introduced}"/>'/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued" for="discontinued">Discontinued date</form:label>
                                <form:input path="discontinued" type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date"  value='<c:out value="${param.discontinued}"/>'/>
                                <div class="error">${errors['discontinued'] }</div>
                                <div class="error" id="checkdate" style="display:none">The discontinued date must be before the introduced date  </div>
                            </div>
                            <div class="form-group">
                                <form:label path="companyName" for="companyId">Company</form:label>
                                <form:select path="companyName" class="form-control" name="company" id="company" >
                                	<form:option value="">---</form:option>
                                	<c:forEach var="company" items="${listCompany}">
                                    	<form:option value="${company.name}"><c:out value="${company.name}" /></form:option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary" id="validButton">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/addcomputer.js" />"></script>

</html>