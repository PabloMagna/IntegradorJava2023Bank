<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="javax.servlet.http.HttpSession" %>

<%@include file="Layout.jsp" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Transferencia</title>
</head>
<body>
    <h1>Realizar Transferencia</h1>

  	 <%-- Mostrar mensaje de error si existe --%>
	<div class="error-message">
   
	</div>

    <form action="ServletCuenta" method="post">
        <label for="importe">Importe:</label>
        <input type="text" name="importe" id="importe" required>
        <br>

        <label for="cbu">CBU (22 números):</label>
        <input type="text" name="cbu" id="cbu" pattern="[0-9]{22}" required>
        <br>

        <label for="origen">Cuenta de Origen:</label>
        21
        <input type="hidden" name="cuentaOrigen" value="21">
        <br>

        <input type="submit" name="btnTransferir" value="Realizar Transferencia">
    </form>
</body>
</html>