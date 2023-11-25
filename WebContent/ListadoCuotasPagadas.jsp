<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuota"%>
<%@ page import="java.util.ArrayList"%>
<%@include file="Layout.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%
	Cliente clienteUsuario = (Cliente) session.getAttribute("cliente");

	if (clienteUsuario == null || clienteUsuario.getTipoCliente() == Cliente.TipoCliente.ADMIN) {
		response.sendRedirect("ErrorPermiso.jsp");
	}
%>
<style>
.bold {
	font-weight: bold;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listado de Cuotas del Cliente</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
<script>
	$(document).ready(function() {
		$('#cuotasTable').DataTable({
			"paging" : true,
			"lengthMenu" : [ 5, 10, 25, 50, 100 ],
			"pageLength" : 10,
			"searching" : false,
			"ordering" : true,
			"language" : {
				"emptyTable" : "El cliente no posee cuotas activas"
			}
		});
	});
</script>
</head>
<body>

	<h1 class="text-center mt-2">Listado de Cuotas</h1>

	<table border="1" id="cuotasTable" class="display">
		<thead>
			<tr>
				<th>ID Cuota</th>
				<th>Número de Cuota</th>
				<th>ID Prestamo</th>
				<th>Importe</th>
				<th>Fecha de Pago</th>
				<th>Estado</th>
			</tr>
		</thead>

		<%
			ArrayList<Cuota> listaCuotas = (ArrayList<Cuota>) request.getAttribute("listaCuotas");

			if (listaCuotas != null && !listaCuotas.isEmpty()) {
				for (Cuota cuota : listaCuotas) {
		%>
		<tr>
			<td><%=cuota.getIdCuota()%></td>
			<td><%=cuota.getNumeroCuota()%></td>
			<td><%=cuota.getPrestamo().getIdPrestamo()%></td>
			<td><%=cuota.getImporte()%></td>
			<td>
				<%
					if (cuota.getEstado() == Cuota.Estado.IMPAGO) {
				%> - <%
					} else {
								out.print(cuota.getFechaPago());
							}
				%>
			</td>
			<td>
				<%
					if (cuota.getEstado() == Cuota.Estado.IMPAGO) {
				%> <span class="text-danger fw-bold">IMPAGO</span> <%
 	} else {
 %> <span class="text-success fw-bold">PAGADO</span> <%
 	}
 %>
			</td>

		</tr>
		<%
			}
			}
		%>
	</table>
	<div class="d-flex justify-content-center">
		<a class="btn btn-primary mt-3" href="Inicio.jsp">Volver al Inicio</a>
	</div>
</body>
</html>
