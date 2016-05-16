
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


			<div class="row contact-wrap">
				<form:form action="appointment/listDate.do"
					modelAttribute="appointmentForm">
					<form:hidden path="veterinary" />
					<div class=" col-sm-offset-1">
						<div class="form-group">
						<form:label path="startMoment">
							<spring:message code="appointment.startMoment" />:
						</form:label>
						
						<form:select path="startMoment">
							<form:option value="0" label="----" />
							<form:options items="${days}" />
						</form:select>
						<button type="submit" name="datedate" class="btn">
							<spring:message code="appointment.changeDay" />
						</button>
						</div>
					</div>
					
				</form:form>

			</div>
			<div class="table-responsive">
			<table class="bookHours table">
				<tr>
					<th colspan="5" style="text-align: center;"><spring:message code="appointment.schedule" /></th>
				</tr>
				<tr class="bold">

					<jstl:forEach var="i" begin="8" end="12">
						<td style="background-color: white;">${i}:00 - ${i+1}:00</td>
					</jstl:forEach>
				</tr>
				<tr>
					<jstl:forEach var="i" begin="8" end="12">
						<jstl:choose>
							<jstl:when
								test="${appointmentService.getVeterinaryisBooked(daySelected,i,i+1,veterinary)}">
								<td style="background-color: lightPink;"><jstl:out
										value="Reservado"></jstl:out></td>
							</jstl:when>
							<jstl:otherwise>

								<td style="background-color: #d3e8a3;"><form:form
										action="appointment/customer/create.do" modelAttribute="appointmentForm">
										<form:hidden path="veterinary" />
										<form:hidden path="startTime" value="${i}" />
										<form:hidden path="endTime" value="${i+1}" />
										<form:hidden path="startMoment" value="${daySelected }" />

										<acme:submit name="book" code="appointment.book" />
									</form:form></td>
							</jstl:otherwise>
						</jstl:choose>
					</jstl:forEach>
				<tr class="bold">

					<jstl:forEach var="i" begin="13" end="17">
						<td style="background-color: white;">${i}:00 - ${i+1}:00</td>
					</jstl:forEach>
				</tr>
				<tr>
					<jstl:forEach var="i" begin="13" end="17">
						<jstl:choose>
							<jstl:when
								test="${appointmentService.getVeterinaryisBooked(daySelected,i,i+1,veterinary)}">
								<td style="background-color: lightPink;"><jstl:out
										value="Reservado"></jstl:out></td>
							</jstl:when>
							<jstl:otherwise>
								<td style="background-color: #d3e8a3;"><form:form
										action="appointment/customer/create.do" modelAttribute="appointmentForm">
										<form:hidden path="veterinary" />
										<form:hidden path="startTime" value="${i}" />
										<form:hidden path="endTime" value="${i+1}" />
										<form:hidden path="startMoment" value="${daySelected }" />

										<acme:submit name="book" code="appointment.book" />
									</form:form></td>
							</jstl:otherwise>
						</jstl:choose>
					</jstl:forEach>
				</tr>
				<tr class="bold">

					<jstl:forEach var="i" begin="18" end="22">
						<td style="background-color: white;">${i}:00 - ${i+1}:00</td>
					</jstl:forEach>
				</tr>
				<tr>
					<jstl:forEach var="i" begin="18" end="22">
						<jstl:choose>
							<jstl:when
								test="${appointmentService.getVeterinaryisBooked(daySelected,i,i+1,veterinary)}">
								<td style="background-color: lightPink;"><jstl:out
										value="Reservado"></jstl:out></td>
							</jstl:when>
							<jstl:otherwise>
								<td style="background-color: #d3e8a3;"><form:form
										action="appointment/customer/create.do" modelAttribute="appointmentForm">
										<form:hidden path="veterinary" />
										<form:hidden path="startTime" value="${i}" />
										<form:hidden path="endTime" value="${i+1}" />
										<form:hidden path="startMoment" value="${daySelected }" />

										<acme:submit name="book" code="appointment.book" />
									</form:form></td>
							</jstl:otherwise>
						</jstl:choose>
					</jstl:forEach>
				</tr>
			</table>
			</div>

