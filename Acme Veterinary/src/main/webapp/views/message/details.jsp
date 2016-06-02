<%--
 * message.jsp
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

<b><spring:message code="message.recipient" />:</b>
<jstl:out value="${msg.recipient.userAccount.username}" />
<br />

<b><spring:message code="message.sender" />:</b>
<jstl:out value="${msg.sender.userAccount.username}" />
<br />

<b><spring:message code="message.subject" />:</b>
<jstl:out value="${msg.subject}" />
<br />

<b><spring:message code="message.body" />:</b>
<jstl:out value="${msg.body}" />
<br />

<b><spring:message code="message.moment" />:</b>
<fmt:formatDate value="${msg.moment}" pattern="dd-MM-yyyy" />
<br />
<br />

<form:form action="message/actor/details.do" modelAttribute="msg">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipient" />
	<form:hidden path="sender" />
	<form:hidden path="messageFolder" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="moment" />

	<jstl:choose>
		<jstl:when test="${msg.messageFolder.name == 'Trash box'}">
			<input type="submit" name="delete"
				value="<spring:message code="message.delete" />"
				onclick="return confirm('<spring:message code="message.deletePermanently" />')" />&nbsp;
		</jstl:when>
		<jstl:otherwise>
			<input type="submit" name="delete"
				value="<spring:message code="message.delete" />"
				onclick="return confirm('<spring:message code="message.deleteMessage" />')" />&nbsp;
		</jstl:otherwise>
	</jstl:choose>
	<jstl:if test="${msg.messageFolder.name != 'Spam box'}">
		<input type="submit" name="spam"
			value="<spring:message code="message.spam" />"
			onclick="return confirm('<spring:message code="message.spam" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" onclick="javascript:history.back();"
		value="<spring:message code='message.cancel' />" />

</form:form>