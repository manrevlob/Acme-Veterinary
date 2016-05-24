<%--
 * index.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">

	<spring:message code="comment.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />

	<spring:message code="comment.rating" var="ratingHeader" />
	<display:column title="${ratingHeader}" sortable="true">
		<jstl:forEach var="i" begin="1" end="${row.rating}">
			<img src="./images/star.png" style="width: 20px; height: 20px;">
		</jstl:forEach>
	</display:column>

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column title="${momentHeader}">
		<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${row.moment}" />
	</display:column>

	<security:authorize access="hasRole('ADMIN')">

		<display:column>

			<a
				onclick="return confirm('<spring:message code="comment.confirm.delete" />')"
				href="comment/administrator/delete.do?commentId=${row.id}&id=${id}&type=${type}">
				<spring:message code="comment.delete" />
			</a>
		</display:column>

	</security:authorize>

</display:table>
<br />
<jstl:if test="${requestURI == 'comment/listByItem.do'}">
	<security:authorize access="hasRole('CUSTOMER')">
		<a href="comment/customer/createToItem.do?itemId=${itemId}"> <spring:message
				code="comment.create" />
		</a>
	</security:authorize>
</jstl:if>

<jstl:if test="${requestURI == 'comment/listByVeterinary.do'}">
	<security:authorize access="hasRole('CUSTOMER')">
		<a
			href="comment/customer/createToVeterinary.do?veterinaryId=${veterinaryId}">
			<spring:message code="comment.create" />
		</a>
	</security:authorize>
</jstl:if>