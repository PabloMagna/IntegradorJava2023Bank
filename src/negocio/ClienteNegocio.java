package negocio;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import dao.ClienteDao;
import entidad.Cliente;
import interfazNegocio.IClienteNegocio;

public class ClienteNegocio implements IClienteNegocio {
	private ClienteDao dao = new ClienteDao();

	@Override
	public Cliente Login(String usuario, String contrasenia) {    
        Cliente cliente = dao.Login(usuario, contrasenia);
        return cliente; // Devuelve el objeto Cliente si es diferente de null
    }

	@Override
	public int Agregar(Cliente cliente) {
		return dao.Agregar(cliente);
	}

	@Override
	public ArrayList<Cliente> ListarClientesActivos(String busqueda) {
		return dao.ListarClientesActivos(busqueda);
	}
	public Cliente ObtenerPorIdCliente(int idCliente) {
		return dao.ObtenerPorIdCliente(idCliente);
	}

	@Override
	public boolean ModificarCliente(Cliente cliente) {
		return dao.ModificarCliente(cliente) == 0 ? false : true;
	}

	@Override
	public boolean EliminarCliente(int idCliente) {
		return dao.EliminarCliente(idCliente) == 0 ? false : true;
	}
	public ArrayList<Cliente> filtrarLista(ArrayList<Cliente> listaOriginal, String tipoFiltro, int numeroFiltro) {
	    ArrayList<Cliente> listaFiltrada = new ArrayList<>();

	    if (numeroFiltro == 0) {
	        return listaOriginal; // No aplicar ningún filtro y devolver la lista completa
	    }

	    for (Cliente cliente : listaOriginal) {
	        int edadCliente = calcularEdad(cliente.getFechaNacimiento()); // Asume que tienes un método para calcular la edad
	        boolean cumpleCondicion = false;

	        if (tipoFiltro.equals("mayor")) {
	            cumpleCondicion = (edadCliente > numeroFiltro);
	        } else if (tipoFiltro.equals("menor")) {
	            cumpleCondicion = (edadCliente < numeroFiltro);
	        } else if (tipoFiltro.equals("igual")) {
	            cumpleCondicion = (edadCliente == numeroFiltro);
	        }

	        if (cumpleCondicion) {
	            listaFiltrada.add(cliente);
	        }
	    }

	    return listaFiltrada;
	}

	public int calcularEdad(LocalDate fechaNacimiento) {
	    LocalDate fechaActual = LocalDate.now();
	    Period edad = Period.between(fechaNacimiento, fechaActual);
	    return edad.getYears();
	}
	
	//devuelven true si es unico elq ue se le pasa y puede ser agregado
	@Override
    public boolean CuilUnico(String cuil) {
        return dao.CuilUnico(cuil) == 0 ? false : true;
    }

    @Override
    public boolean UsuarioUnico(String usuario) {
        return dao.UsuarioUnico(usuario) == 0 ? false : true;
    }

    @Override
    public boolean CorreoUnico(String correo) {
        return dao.CorreoUnico(correo) == 0 ? false : true;
    }

	@Override
	public boolean DniUnico(int dni) {
		return dao.DniUnico(dni) == 0? false:true;
	}
	
}