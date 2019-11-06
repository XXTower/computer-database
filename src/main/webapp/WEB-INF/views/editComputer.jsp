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
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/dashboard"/>"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${computer.id}"/>
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form action="editComputer" method="POST" modelAttribute="computer">
                        <form:input path="id" type="hidden" name="id" value='${computer.id}' id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name" for="computerName">Computer name</form:label>
                                <form:input path="name" type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name" value='${computer.name}' required="required"/>
                            	<div class="error">${errors['computerName'] }</div>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced" for="introduced">Introduced date</form:label>
                                <form:input path="introduced" type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date" value='${computer.introduced }'/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued" for="discontinued">Discontinued date</form:label>
                                <form:input path="discontinued" type="date" name="discontinued" class="form-control" id="discontinued" placeholder="Discontinued date" value='${computer.discontinued }'/>
                                <div class="error">${errors['discontinued'] }</div>
                            	<div class="error" id="checkdate" style="display:none">The discontinued date must be before the introduced date  </div>
                            </div>
                            <div class="form-group">
                                <form:label path="companyName" for="companyId">Company</form:label>
                                <form:select path="companyName" class="form-control" id="company" name="company" >
                                    <form:option value="0">--</form:option>
                                    <c:forEach var="company" items="${listCompany}">
                                    <c:choose>
	                                    <c:when test="${company.name == computer.companyName}">
	                                    	<form:option selected="selected" value='${company.name}'> <c:out value='${company.name}'/> </form:option>
	                                    </c:when>
	                                    <c:otherwise>
	                                    	<form:option value='${company.name}'> <c:out value='${company.name}'/> </form:option>
	                                    </c:otherwise>
                                    </c:choose>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
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