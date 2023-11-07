<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Prestamo"%>
<%@include file="Layout.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>

<link rel="stylesheet" 	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<title>Lista de Préstamos</title>
</head>
<body>
	<h1>Lista de Préstamos</h1>
	<form method="get" action="ServletCliente">
		<%--NADA TIENE FUNCIONALIDAD EN ESTE FORM, solo para presentar tp1 --%>
		<label for="busqueda">Buscar:</label> <input type="text" id="busqueda"
			name="busqueda"> <input type="submit" name="btnBusqueda"
			value="Buscar"> <label for="edad">Importe:</label>
		<%--NADA TIENE FUNCIONALIDAD EN ESTE FORM, solo para presentar tp1 --%>
		<select name="operadorEdad">
			<option value="mayor">Mayor que:</option>
			<option value="menor">Menor que:</option>
			<option value="igual">Igual a:</option>
		</select> <input type="number" id="edad" name="edad">
		<%--NADA TIENE FUNCIONALIDAD EN ESTE FORM, solo para presentar tp1 --%>
	</form>
	<%
		ArrayList<Prestamo> listaPrestamos = (ArrayList<Prestamo>) request.getAttribute("listaPrestamo");
		if (listaPrestamos != null) {
	%>
	<table id="prestamosTable" class="display" border="1">
		<thead>
			<tr>
				<th>ID Prestamo</th>
				<th>Número de Cuenta</th>
				<th>ID Cliente</th>
				<th>Importe Pedido</th>
				<th>Importe por Mes</th>
				<th>Cuotas</th>
				<th>Fecha Pedido</th>
				<th>Estado</th>
				<th>Aprobar</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Prestamo prestamo : listaPrestamos) {
			%>
			<tr>
				<td><%=prestamo.getIdPrestamo()%></td>
				<td><%=prestamo.getCuenta().getNumero()%></td>
				<td><%=prestamo.getCuenta().getCliente().getIdCliente()%></td>
				<td><%=prestamo.getImportePedido()%></td>
				<td><%=prestamo.getImportePorMes()%></td>
				<td><%=prestamo.getCuotas()%></td>
				<td><%=prestamo.getFechaPedido()%></td>
				<td><%=prestamo.getEstado()%></td>
				<td>
					<form action="ServletPrestamo" method="post">
						<input type="hidden" name="idPrestamo"
							value="<%=prestamo.getIdPrestamo()%>">
						<button type="submit" name="btnAprobar"
							value="<%=prestamo.getIdPrestamo()%>" class="btn btn-success"
							data-toggle="tooltip" data-placement="top" title="Aprobar">
							<i class="bi bi-check-circle"></i>
						</button>
						<button type="submit" name="btnRechazar"
							value="<%=prestamo.getIdPrestamo()%>" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Rechazar">
							<i class="bi bi-x-circle"></i>
						</button>
					</form>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<br>
	<%
		} else {
	%>
	<p>No hay préstamos para mostrar.</p>
	<%
		}
	%>
	<script>
		$(document).ready(function() {
			$('#prestamosTable').DataTable({
				"paging" : true,
				"lengthMenu" : [ 5, 10, 25, 50, 100 ],
				"pageLength" : 10,
				"searching" : true,
				"ordering" : true
			});
		});
	</script>
</body>
</html>
