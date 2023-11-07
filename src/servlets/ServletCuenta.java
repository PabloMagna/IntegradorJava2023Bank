package servlets;

import java.io.IOException;
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
import entidad.Movimiento;
import entidad.Telefono;
import entidad.TipoCuenta;
import entidad.TipoMovimiento;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.MovimientoNegocio;

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

// CLIENTE ------------------------------------------------------

		if (request.getParameter("listaPorId") != null) {
			// Obtener el cliente de la sesión
			HttpSession session = request.getSession();
			Cliente cliente = (Cliente) session.getAttribute("cliente");

			CuentaNegocio cuentaNegocio = new CuentaNegocio();

			if (cliente != null) {
				int idCliente = cliente.getIdCliente();

				// Usar el CuentaNegocio para listar las cuentas del cliente por su ID
				ArrayList<Cuenta> cuentas = cuentaNegocio.ListarPorIdCliente(idCliente);
				request.setAttribute("listaCuentas", cuentas);


				RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoCuentasDeCliente.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("error.jsp");
			}
		}
		
		if (request.getParameter("transferencia") != null) {
			int numeroCuenta = Integer.parseInt(request.getParameter("transferencia"));
			Cuenta cuentaOrigen = cuentaNegocio.ObtenerPorNumeroCuenta(numeroCuenta);
			
			System.out.print(cuentaOrigen.toString());

			HttpSession session = request.getSession();

			session.removeAttribute("cuentaOrigen");

			session.setAttribute("cuentaOrigen", cuentaOrigen);

			// Luego, puedes redirigir a la página de transferencia
			response.sendRedirect("Transferencia.jsp");
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
		
		//CLIENTE -----------------
		
		if (request.getParameter("btnTransferir") != null) {
			// Obtener el importe y la cuenta de origen de la solicitud
			double importe = Double.parseDouble(request.getParameter("importe"));
			Cuenta cuentaOrigen = (Cuenta) request.getSession().getAttribute("cuentaOrigen");

			// Obtener el CBU de la cuenta destino desde la solicitud
			String cbuDestino = request.getParameter("cbu");

			// Llamar al método para obtener la cuenta de destino por CBU
			Cuenta cuentaDestino = cuentaNegocio.ObtenerPorCbu(cbuDestino);

			// Verificar si se encontró la cuenta destino
			if (cuentaDestino == null) {
				// Mostrar un mensaje de error
				request.setAttribute("errorMensaje", "El CBU de destino no se encontró.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Transferencia.jsp");
				dispatcher.forward(request, response);
				return; // Detener la ejecución
			}

			if (cuentaOrigen.getNumero() == cuentaDestino.getNumero()) {
				// Mostrar un mensaje de error
				request.setAttribute("errorMensaje", "No se puede transferir a la misma cuenta.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Transferencia.jsp");
				dispatcher.forward(request, response);
				return; // Detener la ejecución
			}

			// Verificar si hay fondos suficientes para la transferencia
			if (cuentaOrigen.getSaldo() < importe) {
				// Mostrar un mensaje de error
				request.setAttribute("errorMensaje", "Saldo insuficiente para realizar la transferencia.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Transferencia.jsp");
				dispatcher.forward(request, response);
				return; // Detener la ejecución
			}

			// Deduct the transfer amount from the account of origin
			cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - importe);

			// Add the transfer amount to the destination account
			cuentaDestino.setSaldo(cuentaDestino.getSaldo() + importe);

			cuentaNegocio.ModificarCuenta(cuentaOrigen);
			cuentaNegocio.ModificarCuenta(cuentaDestino);
			/*
			// Create a movement for the origin account (Resta por transferencia)
			Movimiento movimientoOrigen = new Movimiento();
			movimientoOrigen.setNumeroCuenta(cuentaOrigen.getNumero());
			movimientoOrigen.setImporte(-importe); // Importe negativo
			movimientoOrigen.setIdTipoMovimiento(new TipoMovimiento(4)); // Establece el tipo
			movimientoOrigen.setDetalle("Resta por transferencia");

			// Create a movement for the destination account (Suma por transferencia)
			Movimiento movimientoDestino = new Movimiento();
			movimientoDestino.setNumeroCuenta(cuentaDestino.getNumero());
			movimientoDestino.setImporte(importe);
			movimientoDestino.setIdTipoMovimiento(new TipoMovimiento(5));
			movimientoDestino.setDetalle("Suma por transferencia");

			// Llama a los métodos para agregar estos movimientos en la base de datos
			MovimientoNegocio movimientoNegocio = new MovimientoNegocio();
			movimientoNegocio.Agregar(movimientoOrigen);
			movimientoNegocio.Agregar(movimientoDestino);*/

			// Después de la transferencia exitosa
			request.setAttribute("errorMensaje", "Transferencia exitosa.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Transferencia.jsp");
			dispatcher.forward(request, response);
		}

	}
}
