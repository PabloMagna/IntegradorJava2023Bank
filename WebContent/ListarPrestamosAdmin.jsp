<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Prestamo"%>
<%@include file="Layout.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%
	Cliente clienteUsuario = (Cliente) session.getAttribute("cliente");

	if (clienteUsuario == null || clienteUsuario.getTipoCliente() != Cliente.TipoCliente.ADMIN) {
		response.sendRedirect("ErrorPermiso.jsp");
	}
%>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
	function limpiarCampos() {
		document.getElementById("busqueda").value = "";
		document.getElementById("saldoFiltro").value = "";
		document.querySelector('select[name="operadorSaldo"]').selectedIndex = 0;
		document.querySelector('[name="btnBusquedaAdmin"]').click();
	}
</script>
<title>Lista de Préstamos</title>
</head>
<body>
	<%
		if (request.getParameter("btnAprobar") != null) {
			// ... tu código existente ...

			// SweetAlert para la aprobación
	%>
	<script>
            Swal.fire({
                icon: 'success',
                title: 'Préstamo aprobado correctamente',
                showConfirmButton: false,
                timer: 2000
            }).then((result) => {
                // Redirige después de cerrar la alerta
                window.location.href = '<%=request.getContextPath()%>/ServletPrestamo?lista=1';
            });
        </script>
	<%
		}
		if (request.getParameter("btnRechazar") != null) {
			// ... tu código existente ...

			// SweetAlert para el rechazo
	%>
	<script>
            Swal.fire({
                icon: 'error',
                title: 'Préstamo rechazado correctamente',
                showConfirmButton: false,
                timer: 2000
            }).then((result) => {
                // Redirige después de cerrar la alerta
                window.location.href = '<%=request.getContextPath()%>/ServletPrestamo?lista=1';
            });
        </script>
	<%
		}
	%>
	<%
		String alerta = (String) request.getSession().getAttribute("alerta");
		if (alerta != null) {
	%>
	<script>
        Swal.fire({
            icon: "success",
            title: "<%=alerta%>",
            showConfirmButton: false,
            timer: 2500
        });
    </script>
	<%
		request.getSession().removeAttribute("alerta");
		}
	%>
	<h1 class="text-center mt-2">Préstamos Pendientes de Aprobación</h1>

	<div class="container mt-2" style="margin-bottom: 10px;">
		<form method="get" action="ServletPrestamo" class="row g-3 mt-2 mb-5">
			<div class="col-md-6">
				<label for="busqueda" class="form-label">Buscar:</label>
				<div class="input-group">
					<input type="text" id="busqueda" name="busqueda"
						class="form-control" placeholder="Ingrese búsqueda"
						value="<%=(request.getParameter("busqueda") != null) ? request.getParameter("busqueda") : ""%>">
					<button type="submit" name="btnBusquedaAdmin"
						class="btn btn-primary">Buscar</button>
				</div>
			</div>
			<div class="col-md-6">
				<label for="saldoFiltro" class="form-label">Importe Pedido:</label>
				<div class="input-group">
					<select name="operadorSaldo" class="form-select">
						<option value="mayor"
							<%=(request.getParameter("operadorSaldo") != null
					&& request.getParameter("operadorSaldo").equals("mayor")) ? "selected" : ""%>>Mayor
							que:</option>
						<option value="menor"
							<%=(request.getParameter("operadorSaldo") != null
					&& request.getParameter("operadorSaldo").equals("menor")) ? "selected" : ""%>>Menor
							que:</option>
						<option value="igual"
							<%=(request.getParameter("operadorSaldo") != null
					&& request.getParameter("operadorSaldo").equals("igual")) ? "selected" : ""%>>Igual
							a:</option>
					</select> <input type="text" id="saldoFiltro" name="saldoFiltro"
						class="form-control  me-2" placeholder="Importe"
						value="<%=(request.getParameter("saldoFiltro") != null) ? request.getParameter("saldoFiltro") : ""%>"
						oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
					<select name="estadoFiltro" class="form-select">
						<option value="3"
							<%=(request.getParameter("estadoFiltro") != null && request.getParameter("estadoFiltro").equals("3"))
					? "selected"
					: ""%>>TODOS</option>
						<option value="0"
							<%=(request.getParameter("estadoFiltro") != null && request.getParameter("estadoFiltro").equals("0"))
					? "selected"
					: ""%>>PENDIENTE</option>
						<option value="1"
							<%=(request.getParameter("estadoFiltro") != null && request.getParameter("estadoFiltro").equals("1"))
					? "selected"
					: ""%>>ACEPTADO</option>
						<option value="2"
							<%=(request.getParameter("estadoFiltro") != null && request.getParameter("estadoFiltro").equals("2"))
					? "selected"
					: ""%>>RECHAZADO</option>
					</select>
					<button type="submit" name="btnFiltrarAdmin"
						class="btn btn-success">Filtrar</button>
					<button type="button" onclick="limpiarCampos()"
						class="btn btn-secondary">Limpiar Campos</button>
				</div>
			</div>
		</form>
	</div>


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
					<%
						if (prestamo.getEstado().ordinal() == 0) {
					%>
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
					</form> <%
 	} else {
 %> <!-- Muestra un guión si el estado no es PENDIENTE (ordinal 0) -->
					- <%
 	}
 %>
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
		$(document)
				.ready(
						function() {
							$('#prestamosTable')
									.DataTable(
											{
												"paging" : true,
												"lengthMenu" : [ 5, 10, 25, 50,
														100 ],
												"pageLength" : 10,
												"searching" : false,
												"ordering" : true,
												"language" : {
													"emptyTable" : "No hay prestamos pendientes disponibles en la tabla"
												}
											});
						});
	</script>
	<div class="text-center">
		<a class="btn btn-primary mx-auto" href="Inicio.jsp">Volver al
			Inicio</a>
	</div>
</body>
</html>
