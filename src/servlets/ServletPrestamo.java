
package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Movimiento;
import entidad.Prestamo;
import entidad.Prestamo.Estado;
import entidad.TipoMovimiento;
import negocio.CuentaNegocio;
import negocio.CuotaNegocio;
import negocio.MovimientoNegocio;
import negocio.PrestamoNegocio;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CuentaNegocio cuentaNegocio = new CuentaNegocio();

	public ServletPrestamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/////////////
		// CLIENTE

		// PEDIR PRESTAMO (ListadoCuentasCliente.jsp)
		if (request.getParameter("pedirPrestamo") != null)
			IrAPedirPrestamo(request, response);

		// LISTA PAGAR PRESTAMO (Inicio.jsp)
		if (request.getParameter("listaPagar") != null)
			ListaPagarPrestamo(request, response);

		/////////////////////////////////////
		// ADMIN ----------------------------------------
		
		// LISTAR PRESTAMOS ADMIN (ListarPrestamosAdmin.jsp)
		if (request.getParameter("lista") != null) 
			ListaPrestamosAdmin(request, response);
		
		// BUSCAR PRESTAMOS ADMIN (ListarPrestamosAdmin.jsp)
		if (request.getParameter("btnBusquedaAdmin") != null) 
			BuscarPrestamosAdmin(request, response);
		
		// FILTRAR PRESTAMOS ADMIN (ListarPrestamosAdmin.jsp)
		if (request.getParameter("btnFiltrarAdmin") != null)
			FiltrarPrestamosAdmin(request, response);
		
		// INFORME 2 (Layout.jsp)
		if (request.getParameter("informe2") != null) 
			Informe2(request, response);
		
		// FILTRAR REPORTE 2 (ReporteEstadisticoCantCuotas.jsp)
		if (request.getParameter("btnFiltrarReporte2") != null) 
			FiltrarReporte2(request, response);
		
		if(request.getParameter("cuotasPagas")!= null)
			ListarCuotasPagasPorPrestamo(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// PEDIR PRESTAMO (PedirPrestamo.jsp)
		if (request.getParameter("btnPedirPrestamo") != null) 
			PedirPrestamo(request, response);
		
		// APROBAR PRESTAMO -ADMIN- (ListarPrestamosAdmin.jsp)
		if (request.getParameter("btnAprobar") != null) 
			AprobarPrestamo(request, response);
			
		// RECHAZAR PRESTAMO -ADMIN- (ListarPrestamosAdmin.jsp)
		if (request.getParameter("btnRechazar") != null)
			RechazarPrestamo(request, response);
		
		// PAGAR PRESTAMO (ListarPrestamosCliente.jsp)
		if (request.getParameter("btnPagar") != null) 
			PagarPrestamo(request, response);
	}
	
	
	// ************** METODOS DO GET *********************************************************************************************
	// PEDIR PRESTAMO (PedirPrestamo.jsp)
	private void IrAPedirPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		int numeroCuenta = Integer.parseInt(request.getParameter("pedirPrestamo"));
		Cuenta cuenta = cuentaNegocio.ObtenerPorNumeroCuenta(numeroCuenta);

		session.setAttribute("cuentaPrestamo", cuenta);

		response.sendRedirect("PedirPrestamo.jsp");
	}
	
	// LISTA PAGAR PRESTAMO (ListarPrestamosCliente.JSP)
	private void ListaPagarPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CargarCuotasYCuentasCliente(request, response);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosCliente.jsp");
		dispatcher.forward(request, response);
	}

	private void ListaPrestamosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		ArrayList<Prestamo> listaPrestamos = prestamoNegocio.ListarPendientes(null);

		request.setAttribute("listaPrestamo", listaPrestamos);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosAdmin.jsp");
		dispatcher.forward(request, response);
	}
	
	// BUSCAR PRESTAMOS ADMIN (ListarPrestamosAdmin.jsp)
	private void BuscarPrestamosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String busqueda = request.getParameter("busqueda");

		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		ArrayList<Prestamo> listaPrestamos = prestamoNegocio.ListarPendientes(busqueda);

		request.setAttribute("listaPrestamo", listaPrestamos);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosAdmin.jsp");
		dispatcher.forward(request, response);
	}
	
	// FILTRAR PRESTAMOS ADMIN (ListarPrestamosAdmin.jsp)
	private void FiltrarPrestamosAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipoFiltro = request.getParameter("operadorSaldo");
		String saldoFiltroStr = request.getParameter("saldoFiltro");
		String estadoFiltroStr = request.getParameter("estadoFiltro");
		double saldoFiltro = (saldoFiltroStr != null && !saldoFiltroStr.isEmpty())
				? Double.parseDouble(saldoFiltroStr)
				: 0.0;
		int estadoFiltro = Integer.parseInt(estadoFiltroStr);
		String busqueda = request.getParameter("busqueda");

		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		ArrayList<Prestamo> listaPrestamos = prestamoNegocio.ListarPendientes(busqueda);
		listaPrestamos = prestamoNegocio.filtrarLista(listaPrestamos, tipoFiltro, saldoFiltro, estadoFiltro);

		request.setAttribute("listaPrestamo", listaPrestamos);

		request.setAttribute("operadorSaldo", tipoFiltro);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("saldoFiltro", saldoFiltro);
		request.setAttribute("estadoFiltro", estadoFiltro);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosAdmin.jsp");
		dispatcher.forward(request, response);
	}
	
	// INFORME 2 (ReporteEstadisticoCantCuotas.jsp)
	private void Informe2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		
		float porcentaje3 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(3, 0);
		float porcentaje6 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(6, 0);
		float porcentaje12 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(12, 0);
		float porcentaje24 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(24, 0);			
		
		request.setAttribute("cantidad3cuotas", porcentaje3);
		request.setAttribute("cantidad6cuotas", porcentaje6);
		request.setAttribute("cantidad12cuotas", porcentaje12);
		request.setAttribute("cantidad24cuotas", porcentaje24);

		request.setAttribute("estado", 0);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ReporteEstadisticoCantCuotas.jsp");
		dispatcher.forward(request, response);
	}
	
	// FILTRAR REPORTE 2 (ReporteEstadisticoCantCuotas.jsp)
	private void FiltrarReporte2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int estado = Integer.parseInt(request.getParameter("estado"));			
		
		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		
		float porcentaje3 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(3, estado);
		float porcentaje6 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(6, estado);
		float porcentaje12 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(12, estado);
		float porcentaje24 = prestamoNegocio.PorcentajePrestamosPorCantDeCuotas(24, estado);			
		
		request.setAttribute("cantidad3cuotas", porcentaje3);
		request.setAttribute("cantidad6cuotas", porcentaje6);
		request.setAttribute("cantidad12cuotas", porcentaje12);
		request.setAttribute("cantidad24cuotas", porcentaje24);

		request.setAttribute("estado", estado);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ReporteEstadisticoCantCuotas.jsp");
		dispatcher.forward(request, response);
	}
	
	// ************** METODOS DO POST *********************************************************************************************
	
	// PEDIR PRESTAMO -CLIENTE- (PedirPrestamo.jsp)
	private void PedirPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaPrestamo");

		Cliente cliente = (Cliente) session.getAttribute("cliente");

		Prestamo prestamo = new Prestamo();

		cuenta.setCliente(cliente);
		prestamo.setCuenta(cuenta);

		int cuotas = Integer.parseInt(request.getParameter("cuotas"));
		prestamo.setCuotas(cuotas);

		double importe = Double.parseDouble(request.getParameter("importe"));
		prestamo.setImportePedido(importe);

		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();

		boolean exito = prestamoNegocio.PedirPrestamo(prestamo);

		if (exito) {
			request.getSession().setAttribute("pedidoExitoso", true);

			session.setAttribute("cuentaPrestamo", cuenta);

			response.sendRedirect("PedirPrestamo.jsp");
		}
	}
	
	// APROBAR PRESTAMO -ADMIN- (ListarPrestamosAdmin.jsp)
	private void AprobarPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));

		prestamoNegocio.CambiarEstadoPrestamo(idPrestamo, Estado.APROBADO);

		Prestamo prestamo = prestamoNegocio.ObtenerPrestamoPorId(idPrestamo);
		CuentaNegocio cuentaNegocio = new CuentaNegocio();
		Cuenta cuenta = cuentaNegocio.ObtenerPorNumeroCuenta(prestamo.getCuenta().getNumero());

		// Generar movimiento
		Movimiento movimiento = new Movimiento();
		movimiento.setDetalle("Suma por préstamo aprobado");
		movimiento.setIdTipoMovimiento(new TipoMovimiento(2));
		movimiento.setImporte(prestamo.getImportePedido());
		movimiento.setCuenta(cuenta);

		cuentaNegocio.SumarSaldo(cuenta.getNumero(), prestamo.getImportePedido());

		MovimientoNegocio movimientoNegocio = new MovimientoNegocio();
		movimientoNegocio.Agregar(movimiento);

		// Generar Cuotas
		CuotaNegocio cuotaNegocio = new CuotaNegocio();
		if (cuotaNegocio.GenerarCuotas(prestamo)) {

			ArrayList<Prestamo> listaPrestamos = prestamoNegocio.ListarPendientes(null);
			request.setAttribute("listaPrestamo", listaPrestamos);

			request.getSession().setAttribute("alerta", "Préstamo aceptado correctamente.");

			response.sendRedirect(request.getContextPath() + "/ServletPrestamo?lista=1");
		}
	}
	
	// RECHAZAR PRESTAMO -ADMIN- (ListarPrestamosAdmin.jsp)
	private void RechazarPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
		int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));

		prestamoNegocio.CambiarEstadoPrestamo(idPrestamo, Estado.RECHAZADO);

		request.getSession().setAttribute("alerta", "Préstamo rechazado correctamente.");

		response.sendRedirect(request.getContextPath() + "/ServletPrestamo?lista=1");
	}
	
	// PAGAR PRESTAMO -CLIENTE- (ListarPrestamosCliente.jsp)
	private void PagarPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idCuota = Integer.parseInt(request.getParameter("cuota"));
		double importe = Double.parseDouble(request.getParameter("importe"));
		int numeroCuenta = Integer.parseInt(request.getParameter("cuenta"));

		Cuenta cuenta = cuentaNegocio.ObtenerPorNumeroCuenta(numeroCuenta);

		CuentaNegocio cuentaNegocio = new CuentaNegocio();
		boolean exitoCuenta = cuentaNegocio.SumarSaldo(numeroCuenta, importe * (-1));

		if (exitoCuenta) {

			CuotaNegocio cuotaNegocio = new CuotaNegocio();
			boolean exitoCuota = cuotaNegocio.PagarCuota(idCuota);
			if (exitoCuota) {
				Movimiento movimiento = new Movimiento();
				movimiento.setDetalle("Pago de cuota de Préstamo");
				movimiento.setIdTipoMovimiento(new TipoMovimiento(3));
				movimiento.setImporte(importe * (-1));
				movimiento.setCuenta(cuenta);

				MovimientoNegocio movimientoNegocio = new MovimientoNegocio();
				movimientoNegocio.Agregar(movimiento);

				request.getSession().setAttribute("alertaExito", true);

				CargarCuotasYCuentasCliente(request, response);

				RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosCliente.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			System.out.println("no hay fondos");

			request.getSession().setAttribute("alertaError", true);

			CargarCuotasYCuentasCliente(request, response);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListarPrestamosCliente.jsp");
			dispatcher.forward(request, response);
		}
	}

	
	// METODOS
	private void CargarCuotasYCuentasCliente(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cliente cliente = (Cliente) session.getAttribute("cliente");
		int idCliente = cliente.getIdCliente();

		CuotaNegocio cuotaNegocio = new CuotaNegocio();
		ArrayList<Cuota> listaCuotas = cuotaNegocio.ListarPorClienteAprobadoEImpago(idCliente);

		request.setAttribute("listaCuotas", listaCuotas);

		CuentaNegocio cuentaNegocio = new CuentaNegocio();
		ArrayList<Cuenta> listaCuentas = cuentaNegocio.ListarPorIdCliente(idCliente, null);

		request.setAttribute("listaCuentas", listaCuentas);
	}
	
	private void ListarCuotasPagasPorPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int idPrestamo = Integer.parseInt(request.getParameter("cuotasPagas"));
		
		CuotaNegocio cuotaNegocio = new CuotaNegocio();
			
		ArrayList<Cuota> listaCuotas = cuotaNegocio.ListarCuotasPagadasPorPrestamo(idPrestamo);
		
		request.setAttribute("listaCuotas", listaCuotas);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuotasPagadas.jsp");
		dispatcher.forward(request, response);
	}

}
