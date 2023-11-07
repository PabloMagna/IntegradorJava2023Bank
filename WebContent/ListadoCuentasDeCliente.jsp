<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.util.ArrayList"%>
<%@include file="Layout.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listado de Cuentas del Cliente</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Incluye jQuery 3.6.0 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Incluye DataTables 1.13.6 -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
<script>
	$(document).ready(function() {
		$('#cuentasTable').DataTable({
			"paging" : true, // Habilita la paginación
			"lengthMenu" : [ 5, 10, 25, 50, 100 ], // Define las opciones de cantidad de filas por página
			"pageLength" : 10, // Establece la cantidad de filas por página predeterminada
			"searching" : true, // Habilita la búsqueda
			"ordering" : true, // Habilita el ordenamiento
		// Otras opciones de configuración según tus necesidades
		});
	});
</script>
</head>
<body>
	<h1>Listado de Cuentas</h1>
	<form method="get" action="ServletCliente">
		<label for="busqueda">Buscar:</label> <input type="text" id="busqueda"
			name="busqueda"> <input type="submit" name="btnBusqueda"
			value="Buscar"> <label for="edad">Saldo:</label> <select
			name="operadorEdad">
			<option value="mayor">Mayor que:</option>
			<option value="menor">Menor que:</option>
			<option value="igual">Igual a:</option>
		</select> <input type="number" id="edad" name="edad">
	</form>
	<table border="1" id="cuentasTable" class="display">
		<thead>
			<tr>
				<th>ID Cliente</th>
				<th>Número de Cuenta</th>
				<th>CBU</th>
				<th>Saldo</th>
				<th>Fecha</th>
				<th>Activo</th>
				<th>Tipo de Cuenta</th>
				<th>Acciones</th>
			</tr>
		</thead>

		<%
			ArrayList<Cuenta> listaCuentas = (ArrayList<Cuenta>) request.getAttribute("listaCuentas");

			if (listaCuentas != null && !listaCuentas.isEmpty()) {
				for (Cuenta cuenta : listaCuentas) {
		%>
		<tr>
			<td><%=cuenta.getCliente().getIdCliente() %></td>
			<td><%=cuenta.getNumero()%></td>
			<td><%=cuenta.getCBU()%></td>
			<td><%=cuenta.getSaldo()%></td>
			<td><%=cuenta.getFecha()%></td>
			<td><%=cuenta.getActivo()%></td>
			<td><%=cuenta.getTipoCuenta().getDescripcion()%></td>
			<td><a href="ServletCuenta?historial=<%=cuenta.getNumero()%>"
				class="btn btn-primary" title="Ver historial"> <i
					class="bi bi-clock"></i> Historial
			</a> <a href="ServletCuenta?transferencia=<%=cuenta.getNumero()%>"
				class="btn btn-warning" title="Transferir fondos"> <i
					class="bi bi-arrow-repeat"></i> Transferir
			</a> <a href="ServletPrestamo?pedirPrestamo=<%=cuenta.getNumero()%>"
				class="btn btn-success" title="Pedir préstamo"> <i
					class="bi bi-cash"></i> Pedir Préstamo
			</a></td>


		</tr>
		<%
			}
			}
		%>
	</table>
</body>
</html>
