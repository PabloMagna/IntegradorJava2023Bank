<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList"%>
<%@include file="Layout.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
	<title>Reporte estadistico 1</title>
</head>
<body>
	<h1 id="tituloReporte">Reporte Estadístico 1: Altas de Cuentas</h1>
	</br>
	<h5 id="descripcionReporte"><i>Comparación de registros entre períodos del año actual y año anterior.</i></h5>
	</br>
	
	<% Integer AltasCuentasAnioActual = (Integer)request.getAttribute("AltasCuentasAnioActual"); %>
	<% Integer AltasCuentasAnioPasado = (Integer)request.getAttribute("AltasCuentasAnioPasado"); %>

	
	<div id="cuadroReporte" class="grid text-center" style="--bs-columns: 3;">
	  <div>
	    <h2>Seleccione el período</h2>  	
	    <div id="grillaReporte" class="grid text-center">
		    <div>
			    <form method="get" action="ServletCuenta" class="text-center">			
					<div class="text-center">					
						<div class="input-group">
							<select name="mes" class="form-select">
							<option value="0"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("1")) ? "selected" : "" %>>
									Todo el año
								</option>
								<option value="1"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("1")) ? "selected" : "" %>>
									Enero
								</option>
								<option value="2"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("2")) ? "selected" : "" %>>
									Febrero
								</option>
								<option value="3"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("3")) ? "selected" : "" %>>
									Marzo
								</option>
								<option value="4"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("4")) ? "selected" : "" %>>
									Abril
								</option>
								<option value="5"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("5")) ? "selected" : "" %>>
									Mayo
								</option>
								<option value="6"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("6")) ? "selected" : "" %>>
									Junio
								</option>
								<option value="7"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("7")) ? "selected" : "" %>>
									Julio
								</option>
								<option value="8"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("8")) ? "selected" : "" %>>
									Agosto
								</option>
								<option value="9"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("9")) ? "selected" : "" %>>
									Septiembre
								</option>
								<option value="10"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("10")) ? "selected" : "" %>>
									Octubre
								</option>
								<option value="11"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("11")) ? "selected" : "" %>>
									Noviembre
								</option>
								<option value="12"
									<%=(request.getParameter("mes") != null 
									&& request.getParameter("mes").equals("12")) ? "selected" : "" %>>
									Diciembre
								</option>
								
							</select> 
							<button type="submit" name="btnFiltrarReporte1" class="btn btn-success">Filtrar</button>							
						</div>
					</div>
				</form>
		    </div>
		    <% int anioActual = LocalDate.now().getYear(); %>
		    <% int anioPasado = LocalDate.now().getYear() - 1; %>
	      	<div id="divAnioActual">
		      	<h2><%=anioActual %></h2>
		      	<h3 id="cantidadActual"><%=AltasCuentasAnioActual%> Altas de Cuentas</h3>
			</div>			
	      	<div id="divAnioAnterior">
	      		<h2><%=anioPasado %></h2>
		      	<h3 id="cantidadAnterior"><%=AltasCuentasAnioPasado%> Altas de Cuentas</h3>
			</div>
	    </div>
	  </div>
	 </div>	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.js"></script>
	<div class="d-flex justify-content-center">
		<a class="btn btn-primary mt-3" href="Inicio.jsp">Volver al Inicio</a>
	</div>
</body>
</html>