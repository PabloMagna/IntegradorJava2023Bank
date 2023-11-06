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
import negocio.LocalidadNegocio;
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
		// ALTA CLIENTE
		if (request.getParameter("alta") != null) {
			CargarDescolgables(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
			dispatcher.forward(request, response);
		}

		if (request.getParameter("lista") != null) {

			CargarListaClientes(request, response);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("ElimId") != null) {
			int clienteId = Integer.parseInt(request.getParameter("ElimId"));
			boolean filasEliminadas = clienteNegocio.EliminarCliente(clienteId);

			if (filasEliminadas) {
				request.setAttribute("exitoEliminacionCliente", true);

				CargarListaClientes(request, response);

				RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
				dispatcher.forward(request, response);
			} else {

				response.sendRedirect("Error.jsp");
			}
		}

		// BUSCAR
		if (request.getParameter("btnBusqueda") != null) {
			String busqueda = request.getParameter("busqueda");

			ArrayList<Cliente> lista = clienteNegocio.ListarClientesActivos(busqueda);

			request.setAttribute("lista", lista);
			ArrayList<Telefono> listaTelefonos = telefonoNegocio.ListarTelefonoPorIdCliente(0);// lista todos
			request.setAttribute("listaTelefonos", listaTelefonos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListadoClientes.jsp");
			dispatcher.forward(request, response);
		}

		// FILTRAR
		if (request.getParameter("btnFiltrar") != null) {
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

		if (request.getParameter("ModifId") != null) {
			int modifId = Integer.parseInt(request.getParameter("ModifId"));
			clienteNegocio = new ClienteNegocio();
			Cliente cliente = clienteNegocio.ObtenerPorIdCliente(modifId);
			ArrayList<Telefono> telefonos = telefonoNegocio.ListarTelefonoPorIdCliente(modifId);
			request.setAttribute("telefonos", telefonos);

			CargarDescolgables(request, response);

			Cliente clienteModificar = clienteNegocio.ObtenerPorIdCliente(modifId);

			if (clienteModificar != null) {
				request.setAttribute("clienteModificar", clienteModificar);

				LocalDate fechaNacimientoCliente = clienteModificar.getFechaNacimiento();
				String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				request.setAttribute("fechaNacimiento", fechaNacimientoString);

				RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteNegocio clienteNegocio = new ClienteNegocio();
		// LOGIN
		if (request.getParameter("btnLogin") != null) {
			String usuario = request.getParameter("username");
			String contrasenia = request.getParameter("password");
			Cliente cliente = clienteNegocio.Login(usuario, contrasenia);
			if (cliente != null) {
				sesion = request.getSession();
				sesion.setAttribute("cliente", cliente);
				System.out.println("exito");
				response.sendRedirect("Inicio.jsp");
			} else {
				System.out.println("mal");
				response.sendRedirect("Inicio.jsp");
			}
		}
		if (request.getParameter("btnCerrarSesion") != null) {
			HttpSession sesion = request.getSession(false);
			if (sesion != null) {
				sesion.invalidate();
			}

			response.sendRedirect("Inicio.jsp");
		}
		// ALTA CLIENTE
		if (request.getParameter("btnGuardar") != null) {
		
			Cliente cliente = obtenerClienteDesdeRequest(request);
			
			if (!clienteNegocio.DniUnico(cliente.getDni())) {
				cliente.setIdCliente(0);
				
				request.setAttribute("advertencia", "DNI existente en registros!");
				
				request.setAttribute("clienteModificar", cliente);
				
				CargarDescolgables(request,response);

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
				
				request.setAttribute("clienteModificar", cliente);
				
				CargarDescolgables(request,response);

				LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
				String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				request.setAttribute("fechaNacimiento", fechaNacimientoString);

				RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
				dispatcher.forward(request, response);
				return;
			}
			if (!clienteNegocio.CuilUnico(cliente.getCuil())) {
				cliente.setIdCliente(0);
				cliente.setCuil("");
				
				request.setAttribute("advertencia", "Cuil existente en registros!");
				
				request.setAttribute("clienteModificar", cliente);
				
				CargarDescolgables(request,response);

				LocalDate fechaNacimientoCliente = cliente.getFechaNacimiento();
				String fechaNacimientoString = fechaNacimientoCliente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				request.setAttribute("fechaNacimiento", fechaNacimientoString);

				RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp");
				dispatcher.forward(request, response);
				return;
			}
			if (!clienteNegocio.CorreoUnico(cliente.getCorreo())) {
				cliente.setIdCliente(0);
				cliente.setCorreo("");
				
				request.setAttribute("advertencia", "Correo existente, elegí otro!");
				
				request.setAttribute("clienteModificar", cliente);
				
				CargarDescolgables(request,response);

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
					telefonoNegocio.AgregarTelefonos(cliente.getIdCliente(), telefonos);
				else {
					telefonoNegocio.AgregarTelefonos(cliente.getIdCliente(), null); // solo los borra
				}
				CargarDescolgables(request, response);
				RequestDispatcher dispatcher = request.getRequestDispatcher("AltaCliente.jsp?exito=true");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("Error.jsp");
			}
		}

		// Modificacion de cliente
		if (request.getParameter("btnModificar") != null) {
			int clienteId = Integer.parseInt(request.getParameter("idCliente"));
			Cliente clienteModificado = obtenerClienteDesdeRequest(request);
			clienteModificado.setIdCliente(clienteId);

			boolean exitoCliente = clienteNegocio.ModificarCliente(clienteModificado);

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

}
