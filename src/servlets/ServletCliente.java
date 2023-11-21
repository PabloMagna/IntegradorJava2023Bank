package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.Telefono;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.LocalidadNegocio;
import negocio.PrestamoNegocio;
import negocio.ProvinciaNegocio;
import negocio.TelefonoNegocio;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession sesion;
	private TelefonoNegocio telefonoNegocio = new TelefonoNegocio();

	public ServletCliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteNegocio clienteNegocio = new ClienteNegocio();

		if (request.getParameter("login") != null) 
			CheckLogin(request, response);
		
		if (request.getParameter("logout") != null) 
			Logout(request, response);

		// ALTA (AltaCliente.jsp)
		if (request.getParameter("alta") != null)
			Alta(request, response);

		// LISTA DE CLIENTES (ListadoClientes.jsp)
		if (request.getParameter("lista") != null) 
			ListaClientes(request, response);
		
		// ELIMINAR CLIENTE (ListadoClientes.jsp)
		if (request.getParameter("ElimId") != null) 
			EliminarCliente(request, response, clienteNegocio);

		// BUSCAR CLIENTE (ListadoClientes.jsp)
		if (request.getParameter("btnBusqueda") != null) 
			BuscarCliente(request, response, clienteNegocio);

		// FILTRAR LISTA DE CLIENTES (ListadoClientes.jsp)
		if (request.getParameter("btnFiltrar") != null) 
			FiltrarListado(request, response, clienteNegocio);

		// MODIF ID (ListadoClientes.jsp)
		if (request.getParameter("ModifId") != null) {
			ModifId(request, response, clienteNegocio);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteNegocio clienteNegocio = new ClienteNegocio();
		
		// LOGIN (Inicio.jsp)
		if (request.getParameter("btnLogin") != null) 
			Login(request, response, clienteNegocio);
		
		// CERRAR SESION (Layout.jsp)
		if (request.getParameter("btnCerrarSesion") != null) 
			CerrarSesion(request, response, clienteNegocio);

		// GUARDAR CLIENTE (AltaCliente.jsp)
		if (request.getParameter("btnGuardar") != null)
			GuardarCliente(request, response, clienteNegocio);

		// MODIFICAR CLIENTE (AltaCliente.jsp)
		if (request.getParameter("btnModificar") != null) 
			ModificarCliente(request, response, clienteNegocio);

	}
	
	
	// ************** METODOS DO GET *********************************************************************************************
	// Check Datos Login
	private void CheckLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession sesion = request.getSession(false);
		if (sesion != null) {
			sesion.setAttribute("checkLogin", true);
			response.sendRedirect("Inicio.jsp");
			return;
		}
	}
	
	// LOGOUT 
	private void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 sesion.setAttribute("checkCerrar", true);
		 response.sendRedirect("Inicio.jsp"); 
	}
	
	// ALTA CLIENTE (AltaCliente.jsp)
	private void Alta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("clienteDatos");
		CargarDescolgables(request, response);
		RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
		dispatcher.forward(request, response);
	}
	
	// LISTA CLIENTES (ListadoClientes.jsp)
	private void ListaClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CargarListaClientes(request, response);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
		dispatcher.forward(request, response);
	}
	
	// BUSCAR CLIENTE (ListadoClientes.jsp)
	private void BuscarCliente(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws ServletException, IOException {
		String busqueda = request.getParameter("busqueda");
		ArrayList<Cliente> lista = clienteNegocio.ListarClientesActivos(busqueda);
		
		request.setAttribute("lista", lista);
		ArrayList<Telefono> listaTelefonos = telefonoNegocio.ListarTelefonoPorIdCliente(0);// lista todos
		request.setAttribute("listaTelefonos", listaTelefonos);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
		dispatcher.forward(request, response);
	}
	
	// FILTRAR LISTADO (ListadoClientes.jsp)
	private void FiltrarListado(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws ServletException, IOException {
		String tipoFiltro = request.getParameter("operadorEdad");
		String numeroFiltroStr = request.getParameter("edad");
		int numeroFiltro = (numeroFiltroStr != null && !numeroFiltroStr.isEmpty())
				? Integer.parseInt(numeroFiltroStr)
				: 0;
		String busqueda = request.getParameter("busqueda");

		ArrayList<Cliente> lista = clienteNegocio.ListarClientesActivos(busqueda);
		lista = clienteNegocio.filtrarLista(lista, tipoFiltro, numeroFiltro);

		ArrayList<Telefono> listaTelefonos = telefonoNegocio.ListarTelefonoPorIdCliente(0);// lista todos
		request.setAttribute("listaTelefonos", listaTelefonos);

		request.setAttribute("lista", lista);

		request.setAttribute("operadorEdad", tipoFiltro);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("edad", numeroFiltro);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
		dispatcher.forward(request, response);
	}
	
	// ELIMINAR CLIENTE (ListadoClientes.jsp)
	private void EliminarCliente(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws ServletException, IOException {
		int clienteId = Integer.parseInt(request.getParameter("ElimId"));
		boolean filasEliminadas = clienteNegocio.EliminarCliente(clienteId);

		if (filasEliminadas) {
			request.setAttribute("exitoEliminacionCliente", true);
			CargarListaClientes(request, response);
			
			//Eliminar sus cuentas y rechaza préstamos.
			CuentaNegocio cuentaNegocio = new CuentaNegocio();
			cuentaNegocio.EliminarCuentasPorIdCliente(clienteId);
			PrestamoNegocio prestamoNegocio = new PrestamoNegocio();
			prestamoNegocio.RechazarPrestamosPorIdClienteEliminado(clienteId);
			

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("ErrorPermiso.jsp");
		}
	}
	
	// MODIF ID (ListadoClientes.jsp)
	private void ModifId(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws ServletException, IOException {
		int modifId = Integer.parseInt(request.getParameter("ModifId"));
		ArrayList<Telefono> telefonos = telefonoNegocio.ListarTelefonoPorIdCliente(modifId);
		request.setAttribute("telefonos", telefonos);

		CargarDescolgables(request, response);

		Cliente cliente = clienteNegocio.ObtenerPorIdCliente(modifId);

		if (cliente != null) {
			HttpSession session = request.getSession();
			session.setAttribute("clienteDatos", cliente);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
		}
	}
	

	// ************** METODOS DO POST *********************************************************************************************
	
	// LOGIN (Inicio.jsp)
	private void Login(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws IOException {
		String usuario = request.getParameter("username");
		String contrasenia = request.getParameter("password");
		Cliente cliente = clienteNegocio.Login(usuario, contrasenia);
		if (cliente != null) {
			sesion = request.getSession();
			sesion.setAttribute("cliente", cliente);
			response.sendRedirect("Inicio.jsp");
		} else {
			response.sendRedirect("ServletCliente?login=1");
		}
		
	}
	
	// CERRAR SESION (Layout.jsp)
	private void CerrarSesion(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws IOException {
	    HttpSession session = request.getSession(false); 
	    if (session != null) {
	        session.invalidate(); 
	        session = request.getSession(); 
	        session.setAttribute("checkCerrar", true); 
	    }
	    response.sendRedirect("Inicio.jsp?logout=1");  
	}
	
	// MODIFICAR CLIENTE (AltaCliente.jsp)
	private void ModificarCliente(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws IOException, ServletException {
		int clienteId = Integer.parseInt(request.getParameter("idCliente"));
		Cliente cliente = obtenerClienteDesdeRequest(request);
		HttpSession session = request.getSession();
		session.setAttribute("clienteDatos", cliente);
		cliente.setIdCliente(clienteId);

		if (!clienteNegocio.DniUnico(cliente.getDni(), cliente.getIdCliente())) {
			request.setAttribute("advertencia", "DNI existente en registros!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			ArrayList<Telefono> telefonos = telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
			request.setAttribute("telefonos", telefonos);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("AltaCliente.jsp?ModifId=" + cliente.getIdCliente());
			dispatcher.forward(request, response);
			return;
		}
		if (!clienteNegocio.CuilUnico(cliente.getCuil(), cliente.getIdCliente())) {
			cliente.setCuil("");

			request.setAttribute("advertencia", "Cuil existente en registros!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			ArrayList<Telefono> telefonos = telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
			request.setAttribute("telefonos", telefonos);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("AltaCliente.jsp?ModifId=" + cliente.getIdCliente());
			dispatcher.forward(request, response);
			return;
		}
		if (!clienteNegocio.CorreoUnico(cliente.getCorreo(), cliente.getIdCliente())) {
			cliente.setCorreo("");

			request.setAttribute("advertencia", "Correo existente, elegí otro!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			ArrayList<Telefono> telefonos = telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
			request.setAttribute("telefonos", telefonos);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("AltaCliente.jsp?ModifId=" + cliente.getIdCliente());
			dispatcher.forward(request, response);
			return;
		}

		boolean exitoCliente = clienteNegocio.ModificarCliente(cliente);

		if (exitoCliente) {
			TelefonoNegocio telefonoNegocio = new TelefonoNegocio();
			String[] telefonos = request.getParameterValues("telefonos");
			if (telefonos != null)
				telefonoNegocio.AgregarTelefonos(clienteId, telefonos);
			else {
				telefonoNegocio.AgregarTelefonos(clienteId, null); // solo los borra
			}
			request.setAttribute("exitoModificacion", true);

			CargarListaClientes(request, response);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
			dispatcher.forward(request, response);

		} else {
			response.sendRedirect("Error.jsp");
		}
	}
	
	// GUARDAR CLIENTE (AltaCliente.jsp)
	private void GuardarCliente(HttpServletRequest request, HttpServletResponse response, ClienteNegocio clienteNegocio) throws ServletException, IOException {

		Cliente cliente = obtenerClienteDesdeRequest(request);
		HttpSession session = request.getSession();
		session.setAttribute("clienteDatos", cliente);

		if (!clienteNegocio.DniUnico(cliente.getDni(), 0)) {
			cliente.setIdCliente(0);
			request.setAttribute("advertencia", "DNI existente en registros!");
			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (!clienteNegocio.UsuarioUnico(cliente.getUsuario())) {
			cliente.setIdCliente(0);
			cliente.setUsuario("");

			request.setAttribute("advertencia", "Nombre de usuario en uso, elegí otro!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (!clienteNegocio.CuilUnico(cliente.getCuil(), 0)) {
			cliente.setIdCliente(0);
			cliente.setCuil("");

			request.setAttribute("advertencia", "Cuil existente en registros!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (!clienteNegocio.CorreoUnico(cliente.getCorreo(), 0)) {
			cliente.setIdCliente(0);
			cliente.setCorreo("");

			request.setAttribute("advertencia", "Correo existente, elegí otro!");

			session.setAttribute("clienteDatos", cliente);

			CargarDescolgables(request, response);

			LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
			String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			request.setAttribute("fechaNacimiento", fechaNacimientoString);

			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
			return;
		}

		int idCliente = clienteNegocio.Agregar(cliente);

		if (idCliente > 0) {
			TelefonoNegocio telefonoNegocio = new TelefonoNegocio();
			String[] telefonos = request.getParameterValues("telefonos");
			if (telefonos != null)
				telefonoNegocio.AgregarTelefonos(idCliente, telefonos);
			else {
				telefonoNegocio.AgregarTelefonos(idCliente, null); // solo los borra
			}
			CargarDescolgables(request, response);
			// Redirige al ServletCliente con parámetros "Alta=1" y "exito=true"
			response.sendRedirect(request.getContextPath() + "/ServletCliente?alta=1&exito=true");
		}
	}
	
	
	
	
	
	// METODOS

	private void CargarListaClientes(HttpServletRequest request, HttpServletResponse response) {
		String buscar = null;
		ClienteNegocio clienteNegocio = new ClienteNegocio();
		ArrayList<Cliente> lista = clienteNegocio.ListarClientesActivos(buscar);
		request.setAttribute("lista", lista);

		ArrayList<Telefono> listaTelefonos = telefonoNegocio.ListarTelefonoPorIdCliente(0);// lista todos
		request.setAttribute("listaTelefonos", listaTelefonos);

		// valor filtros
		request.setAttribute("operadorEdad", "mayor");
		request.setAttribute("busqueda", "");
		request.setAttribute("edad", 0);
	}

	private void CargarDescolgables(HttpServletRequest request, HttpServletResponse response) {
		ProvinciaNegocio provinciaNegocio = new ProvinciaNegocio();
		ArrayList<Provincia> provincias = provinciaNegocio.Listar();

		request.setAttribute("provincias", provincias);

		LocalidadNegocio localidadNegocio = new LocalidadNegocio();
		ArrayList<Localidad> localidades = localidadNegocio.Listar();

		request.setAttribute("localidades", localidades);
	}

	private Cliente obtenerClienteDesdeRequest(HttpServletRequest request) {
		Cliente cliente = new Cliente();

		String idClienteStr = request.getParameter("idCliente");
		String usuario = request.getParameter("usuario");
		String contrasena = request.getParameter("contrasena");
		String dniStr = request.getParameter("dni");
		String cuil = request.getParameter("cuil");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String sexoStr = request.getParameter("sexo");
		String nacionalidad = request.getParameter("nacionalidad");
		String fechaNacimientoStr = request.getParameter("fechaNacimiento");
		String direccion = request.getParameter("direccion");
		String localidadIdStr = request.getParameter("localidad");
		String provinciaIdStr = request.getParameter("provincia");
		String correo = request.getParameter("correo");

		try {
			int idCliente = Integer.parseInt(idClienteStr);
			int dni = Integer.parseInt(dniStr);
			int sexo = Integer.parseInt(sexoStr);
			int localidadId = Integer.parseInt(localidadIdStr);
			int provinciaId = Integer.parseInt(provinciaIdStr);

			LocalDate fechaCreacion = LocalDate.now();
			LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);

			Localidad localidad = new Localidad();
			localidad.setId(localidadId);
			localidad.setIdProvincia(provinciaId);

			Provincia provincia = new Provincia();
			provincia.setId(provinciaId);

			cliente.setIdCliente(idCliente);
			cliente.setUsuario(usuario);
			cliente.setContrasena(contrasena);
			cliente.setDni(dni);
			cliente.setCuil(cuil);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setSexo(sexo);
			cliente.setNacionalidad(nacionalidad);
			cliente.setFechaNacimiento(fechaNacimiento);
			cliente.setFechaCreacion(fechaCreacion);
			cliente.setDireccion(direccion);
			cliente.setLocalidad(localidad);
			cliente.setProvincia(provincia);
			cliente.setCorreo(correo);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			// Manejar errores de conversión de números si es necesario
			// Aquí puedes agregar el manejo de errores o lanzar una excepción si algo sale
			// mal
			// Puede haber se la exepcion que tenemos que hacer.
		}

		return cliente;
	}

	/*
	 * private Cliente ValidarDniCuilUsuarioCorreoUnico(Cliente cliente,
	 * HttpServletRequest request, HttpServletResponse response) { ClienteNegocio
	 * clienteNegocio = new ClienteNegocio();
	 * 
	 * if (!clienteNegocio.DniUnico(cliente.getDni(),cliente.getIdCliente())) {
	 * request.setAttribute("advertencia", "DNI existente en registros!");
	 * 
	 * HttpSession session2 = request.getSession();
	 * session2.setAttribute("clienteDatos", cliente);
	 * 
	 * CargarDescolgables(request,response);
	 * 
	 * LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento(); String
	 * fechaNacimientoString =
	 * fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	 * request.setAttribute("fechaNacimiento", fechaNacimientoString);
	 * 
	 * ArrayList<Telefono> telefonos =
	 * telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
	 * request.setAttribute("telefonos", telefonos);
	 * 
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("AltaCliente.jsp?ModifId="+cliente.getIdCliente(
	 * )); dispatcher.forward(request, response); return cliente; } if
	 * (!clienteNegocio.CuilUnico(cliente.getCuil(),cliente.getIdCliente())) {
	 * cliente.setCuil("");
	 * 
	 * request.setAttribute("advertencia", "Cuil existente en registros!");
	 * 
	 * session.setAttribute("clienteDatos", cliente);
	 * 
	 * CargarDescolgables(request,response);
	 * 
	 * LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento(); String
	 * fechaNacimientoString =
	 * fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	 * request.setAttribute("fechaNacimiento", fechaNacimientoString);
	 * 
	 * ArrayList<Telefono> telefonos =
	 * telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
	 * request.setAttribute("telefonos", telefonos);
	 * 
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("AltaCliente.jsp?ModifId="+cliente.getIdCliente(
	 * )); dispatcher.forward(request, response); return; } if
	 * (!clienteNegocio.CorreoUnico(cliente.getCorreo(),cliente.getIdCliente())) {
	 * cliente.setCorreo("");
	 * 
	 * request.setAttribute("advertencia", "Correo existente, elegí otro!");
	 * 
	 * session.setAttribute("clienteDatos", cliente);
	 * 
	 * CargarDescolgables(request,response);
	 * 
	 * LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento(); String
	 * fechaNacimientoString =
	 * fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	 * request.setAttribute("fechaNacimiento", fechaNacimientoString);
	 * 
	 * ArrayList<Telefono> telefonos =
	 * telefonoNegocio.ListarTelefonoPorIdCliente(cliente.getIdCliente());
	 * request.setAttribute("telefonos", telefonos);
	 * 
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("AltaCliente.jsp?ModifId="+cliente.getIdCliente(
	 * )); dispatcher.forward(request, response); return; } }
	 */

}
