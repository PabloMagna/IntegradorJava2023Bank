<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Cuota"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.util.*"%>
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
<meta charset="UTF-8">
<title>Lista de Préstamos</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
	function limpiarCampos() {
		document.getElementById("busqueda").value = "";
		document.getElementById("saldoFiltro").value = "";
		document.querySelector('select[name="operadorSaldo"]').selectedIndex = 0;
		document.querySelector('[name="btnBusquedaCliente"]').click();
	}
	$(document).ready(function() {
		$('#prestamosTable').DataTable({
			"paging" : true,
			"lengthMenu" : [ 5, 10, 25, 50, 100 ],
			"pageLength" : 10,
			"searching" : true,
			"ordering" : true,
			"language" : {
				"emptyTable" : "No hay préstamos disponibles para pagar"
			}
		});
	});
</script>
</head>
<body>

	<%
		// Verificar si hay una alerta de éxito y mostrarla
		Boolean alertaExito = (Boolean) session.getAttribute("alertaExito");
		if (alertaExito != null && alertaExito) {
	%>
	<script>
		Swal.fire({
			icon : "success",
			title : "Éxito",
			text : "Cuota pagada correctamente."
		});
	</script>
	<%
		// Una vez mostrada la alerta, establecerla como false
			session.setAttribute("alertaExito", false);
		}

		// Verificar si hay una alerta de error y mostrarla
		Boolean alertaError = (Boolean) session.getAttribute("alertaError");
		if (alertaError != null && alertaError) {
	%>
	<script>
		Swal.fire({
			icon : "error",
			title : "Error",
			text : "No hay fondos suficientes en la cuenta."
		});
	</script>
	<%
		// Una vez mostrada la alerta, establecerla como false
			session.setAttribute("alertaError", false);
		}
	%>


	<h1 class="text-center mt-2">Pagar Cuotas de Préstamos</h1>

	<table border="1" id="prestamosTable" class="display">
		<thead>
			<tr>
				<th>ID Préstamo</th>
				<th>Seleccionar Cuota</th>
				<th>Importe</th>
				<th>Seleccionar Cuenta</th>
				<th>Pagar</th>
				<th>Historial</th>
			</tr>
		</thead>
		<tbody>
			<%
				ArrayList<Cuota> listaCuotas = (ArrayList<Cuota>) request.getAttribute("listaCuotas");
				ArrayList<Cuenta> listaCuentas = (ArrayList<Cuenta>) request.getAttribute("listaCuentas");

				if (listaCuotas != null) {
					Set<Integer> idPrestamosUnicos = new HashSet<>();
					for (Cuota cuota : listaCuotas) {
						idPrestamosUnicos.add(cuota.getPrestamo().getIdPrestamo());
					}

					for (Integer idPrestamo : idPrestamosUnicos) {
						ArrayList<Cuota> cuotasDelPrestamo = new ArrayList<Cuota>();
						for (Cuota cuota : listaCuotas) {
							if (cuota.getPrestamo().getIdPrestamo() == idPrestamo) {
								cuotasDelPrestamo.add(cuota);
							}
						}
			%>
			<tr>
				<form action="ServletPrestamo" method="post"
					id="form<%=idPrestamo%>">
					<td><%=idPrestamo%></td>
					<td><select name="cuota" class="form-select">
							<%
								for (Cuota cuota : cuotasDelPrestamo) {
							%>
							<option value="<%=cuota.getIdCuota()%>"
								<%if (cuotasDelPrestamo.indexOf(cuota) == 0)
							out.print("selected");%>><%=cuota.getNumeroCuota()%></option>
							<%
								}
							%>
					</select></td>

					<td><input id="importe" name="importe"
						value=<%=cuotasDelPrestamo.get(0).getImporte()%> class="border-0"
						readonly></td>
					<td><select name="cuenta" class="form-select">
							<%
								for (Cuenta cuenta : listaCuentas) {
							%>
							<option value="<%=cuenta.getNumero()%>"><%=cuenta.getNumero()%></option>
							<%
								}
							%>
					</select></td>
					<td>
						<button type="submit" class="btn btn-success" name="btnPagar"
							value="<%=idPrestamo%>">
							<i class="bi bi-cash"></i> Pagar
						</button>
					</td>
					<td>
						<a href="ServletPrestamo?cuotasPagas=<%=idPrestamo%>" class="btn btn-secondary"> 
						<i class="bi bi-exclamation-triangle"></i> Historial</a>
					</td>
				</form>
			</tr>
			<%
				}
				} else {
			%>
			<tr>
				<td colspan="5">No hay préstamos para mostrar.</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<div class="d-flex justify-content-center">
		<a class="btn btn-primary mt-3" href="Inicio.jsp">Volver al Inicio</a>
	</div>
</body>
</html>
