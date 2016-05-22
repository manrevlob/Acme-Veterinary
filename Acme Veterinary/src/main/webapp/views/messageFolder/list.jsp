<%--
 * messageFolder.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<a class="btn btn-primary" href="message/actor/sendMsg.do"> <spring:message
			code="message.sendAMessage" />
	</a>
</div>

<display:table name="messageFolders" pagesize="10" class="displaytag"
	requestURI="${requestURI}" id="row">

	<spring:message code="messageFolder.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<display:column>
		<a href="message/actor/list.do?messageFolderId=${row.id}"> <spring:message
				code="messageFolder.viewMessages" />
		</a>
	</display:column>
	
	<display:column>
		<a href="messageFolder/actor/details.do?messageFolderId=${row.id}">
			<spring:message code="messageFolder.details" />
		</a>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.system == false}">
		
			<a href="messageFolder/actor/edit.do?messageFolderId=${row.id}">
				<spring:message code="messageFolder.edit" />
			</a>
		</jstl:if>
	</display:column>
	
	
</display:table>

<div>
	<a class="btn btn-primary" href="messageFolder/actor/create.do"> <spring:message
			code="messageFolder.create" />
	</a>
</div>