<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList"%>
<%@include file="Layout.jsp"%>

<%
	Cliente clienteUsuario = (Cliente) session.getAttribute("cliente");

	if (clienteUsuario == null || clienteUsuario.getTipoCliente() != Cliente.TipoCliente.ADMIN) {
		response.sendRedirect("Inicio.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
	<title>Reporte estadistico 2</title>
</head>
<body>
	<h1 id="tituloReporte">Reporte Estadístico 2: Cantidad de cuotas elegida por los clientes</h1>
	</br>
	<h5 id="descripcionReporte"><i>Distribución porcentual de elección para cada opción de cantidad de cuotas.</i></h5>
	</br>
	
	<% float cantidad3cuotas = (float)request.getAttribute("cantidad3cuotas"); %>
	<% float cantidad6cuotas = (float)request.getAttribute("cantidad6cuotas"); %>
	<% float cantidad12cuotas = (float)request.getAttribute("cantidad12cuotas"); %>
	<% float cantidad24cuotas = (float)request.getAttribute("cantidad24cuotas"); %>

	
	<div id="cuadroReporte" class="grid text-center" style="--bs-columns: 3;">
	  <div>
	    <h2>Filtrar por estado de préstamo</h2>  	
	    <div id="grillaReporte" class="grid text-center">
		    <div>
			    <form method="get" action="ServletPrestamo" class="text-center">			
					<div class="text-center">					
						<div class="input-group">
							<select name="estado" class="form-select">
								<option value="0"
									<%=(request.getParameter("estado") != null 
									&& request.getParameter("estado").equals("0")) ? "selected" : "" %>>
									TODOS LOS PRÉSTAMOS
								</option>	
								<option value="1"
									<%=(request.getParameter("estado") != null 
									&& request.getParameter("estado").equals("1")) ? "selected" : "" %>>
									PENDIENTE
								</option>
								<option value="2"
									<%=(request.getParameter("estado") != null 
									&& request.getParameter("estado").equals("2")) ? "selected" : "" %>>
									APROBADO
								</option>
								<option value="3"
									<%=(request.getParameter("estado") != null 
									&& request.getParameter("estado").equals("3")) ? "selected" : "" %>>
									RECHAZADO
								</option>											
							</select> 
							<button type="submit" name="btnFiltrarReporte2" class="btn btn-primary">Filtrar</button>							
						</div>
					</div>
				</form>
		    </div>
		    <div id="cuadroReporte2" class="row">		    
		    	<div id="div3cuotas" class="col">
		      	<h2>3 Cuotas</h2>
			      	<h3 id="porcent3cuotas"><%= cantidad3cuotas%> %</h3>
				</div>				
		      	<div id="div6cuotas" class="col">
		      		<h2>6 Cuotas</h2>
			      	<h3 id="porcent6cuotas"><%= cantidad6cuotas%> %</h3>
				</div>
				<div class="w-100"></div>
				<div id="div12cuotas" class="col">
			      	<h2>12 Cuotas</h2>
			      	<h3 id="porcent12cuotas"><%= cantidad12cuotas%> %</h3>
				</div>				
		      	<div id="div24cuotas" class="col">
		      		<h2>24 Cuotas</h2>
			      	<h3 id="porcent24cuotas"><%= cantidad24cuotas%> %</h3>
				</div>
			</div>		    
	      	
	    </div>
	  </div>
	 </div>	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
</body>
</html>
