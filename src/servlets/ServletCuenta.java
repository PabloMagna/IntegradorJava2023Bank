package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Telefono;
import entidad.TipoCuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentaNegocio cuentaNegocio = new CuentaNegocio();

	public ServletCuenta() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("AgregarId") != null) {

			int idCliente = Integer.parseInt(request.getParameter("AgregarId"));

			request.setAttribute("idCliente", idCliente);

			ArrayList<TipoCuenta> tiposCuenta = cuentaNegocio.ListarTipoCuenta();

			request.setAttribute("tiposCuenta", tiposCuenta);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCuenta.jsp");
			dispatcher.forward(request, response);
		}

		if (request.getParameter("lista") != null) {

			request.setAttribute("advertencia", null);

			ArrayList<Cuenta> Cuentas = cuentaNegocio.ListarCuentasActivas(null);
			request.setAttribute("listaCuentas", Cuentas);

			request.setAttribute("operadorSaldo", "mayor");
			request.setAttribute("busqueda", "");
			request.setAttribute("saldoFiltro", "");

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentas.jsp");
			dispatcher.forward(request, response);
		}

		if (request.getParameter("EliminarNumeroCuenta") != null) {
			int numeroCuentaAEliminar = Integer.parseInt(request.getParameter("EliminarNumeroCuenta"));
			boolean exitoEliminacion = cuentaNegocio.EliminarCuenta(numeroCuentaAEliminar);

			if (exitoEliminacion) {
				request.setAttribute("exitoEliminacion", true);
			} else {
				request.setAttribute("exitoEliminacion", false);
			}

			ArrayList<Cuenta> Cuentas = cuentaNegocio.ListarCuentasActivas(null);
			request.setAttribute("listaCuentas", Cuentas);

			request.setAttribute("mostrarError", !exitoEliminacion);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentas.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ModificarNumero") != null) {

			int numeroCuenta = Integer.parseInt(request.getParameter("ModificarNumero"));

			Cuenta cuenta = cuentaNegocio.ObtenerPorNumeroCuenta(numeroCuenta);

			request.setAttribute("cuenta", cuenta);

			ArrayList<TipoCuenta> tiposCuenta = cuentaNegocio.ListarTipoCuenta();

			request.setAttribute("tiposCuenta", tiposCuenta);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ModificarCuenta.jsp");
			dispatcher.forward(request, response);
		}

		// BUSCAR
		if (request.getParameter("btnBusqueda") != null) {
			String busqueda = request.getParameter("busqueda");

			ArrayList<Cuenta> listaCuentas = cuentaNegocio.ListarCuentasActivas(busqueda);

			request.setAttribute("listaCuentas", listaCuentas);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentas.jsp");
			dispatcher.forward(request, response);
		}
		// FILTRAR
		if (request.getParameter("btnFiltrar") != null) {
			String tipoFiltro = request.getParameter("operadorSaldo");
			String saldoFiltroStr = request.getParameter("saldoFiltro");
			double saldoFiltro = (saldoFiltroStr != null && !saldoFiltroStr.isEmpty())
					? Double.parseDouble(saldoFiltroStr)
					: 0.0;
			String busqueda = request.getParameter("busqueda");

			ArrayList<Cuenta> listaCuentas = cuentaNegocio.ListarCuentasActivas(busqueda);
			listaCuentas = cuentaNegocio.filtrarLista(listaCuentas, tipoFiltro, saldoFiltro);

			request.setAttribute("listaCuentas", listaCuentas);

			request.setAttribute("operadorSaldo", tipoFiltro);
			request.setAttribute("busqueda", busqueda);
			request.setAttribute("saldoFiltro", saldoFiltro);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentas.jsp");
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("btnAgregar") != null) {

			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			int idTipoCuenta = Integer.parseInt(request.getParameter("idTipoCuenta"));

			Cuenta cuenta = new Cuenta();
			ClienteNegocio clienteNegocio = new ClienteNegocio();
			Cliente cliente = clienteNegocio.ObtenerPorIdCliente(idCliente);
			cuenta.setCliente(cliente);
			
			TipoCuenta tipoCuenta = new TipoCuenta();
			tipoCuenta.setIdTipoCuenta(idTipoCuenta);
			cuenta.setTipoCuenta(tipoCuenta);

			CuentaNegocio cuentaNegocio = new CuentaNegocio();
			int numeroCuenta = cuentaNegocio.Agregar(cuenta);
			
			request.setAttribute("idCliente", idCliente);

			if (numeroCuenta == -1) {
				request.setAttribute("advertencia", "error");

				ArrayList<TipoCuenta> tiposCuenta = cuentaNegocio.ListarTipoCuenta();
				request.setAttribute("tiposCuenta", tiposCuenta);

				RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCuenta.jsp");
				dispatcher.forward(request, response);
				return;
			}
			request.setAttribute("advertencia", "exito");
			ArrayList<TipoCuenta> tiposCuenta = cuentaNegocio.ListarTipoCuenta();
			request.setAttribute("tiposCuenta", tiposCuenta);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCuenta.jsp");
			dispatcher.forward(request, response);
		}

		if (request.getParameter("btnModificar") != null) {
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			int numero = Integer.parseInt(request.getParameter("numero"));
			String CBU = request.getParameter("CBU");
			int idTipoCuenta = Integer.parseInt(request.getParameter("idTipoCuenta"));
			double saldo = Double.parseDouble(request.getParameter("saldo"));

			// Crear un objeto Cuenta con los datos
			Cuenta cuenta = new Cuenta();

			ClienteNegocio clienteNegocio = new ClienteNegocio();
			Cliente cliente = clienteNegocio.ObtenerPorIdCliente(idCliente);
			cuenta.setCliente(cliente);
			
			cuenta.setNumero(numero);
			cuenta.setCBU(CBU);
			cuenta.setSaldo(saldo);
			cuenta.setActivo(1);

			// Crear un objeto TipoCuenta con el id seleccionado
			TipoCuenta tipoCuenta = new TipoCuenta();
			tipoCuenta.setIdTipoCuenta(idTipoCuenta);
			cuenta.setTipoCuenta(tipoCuenta);

			// Llamar al método Agregar del negocio y verificar si fue exitoso
			cuentaNegocio = new CuentaNegocio();
			boolean exito = cuentaNegocio.ModificarCuenta(cuenta);

			if (exito) {
				request.setAttribute("exitoModificacion", true); 
			    
				request.setAttribute("advertencia", null);

				ArrayList<Cuenta> Cuentas = cuentaNegocio.ListarCuentasActivas(null);
				request.setAttribute("listaCuentas", Cuentas);

				request.setAttribute("operadorSaldo", "mayor");
				request.setAttribute("busqueda", "");
				request.setAttribute("saldoFiltro", "");

				RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentas.jsp");
				dispatcher.forward(request, response);
				
			} else {
			    response.sendRedirect("error.jsp");
			}
		}

	}
}
