package interfazNegocio;

import java.util.ArrayList;

import entidad.Cliente;

public interface IClienteNegocio {
	public Cliente Login(String usuario, String constrasenia);
	public int Agregar(Cliente cliente);
	public ArrayList<Cliente> ListarClientesActivos(String busqueda);
	public Cliente ObtenerPorIdCliente(int idCliente);
	public boolean ModificarCliente(Cliente cliente);
	public boolean EliminarCliente(int idCliente);
	public boolean DniUnico(int dni);
	public boolean CuilUnico(String cuil);
	public boolean UsuarioUnico(String usuario);
	public boolean CorreoUnico(String correo);
}