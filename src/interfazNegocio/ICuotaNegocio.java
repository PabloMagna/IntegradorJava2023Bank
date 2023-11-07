package interfazNegocio;

import java.util.ArrayList;

import entidad.Cuota;
import entidad.Prestamo;

public interface ICuotaNegocio {
	public boolean GenerarCuotas(Prestamo prestamo);
	public boolean PagarCuota(int numeroCuota, int idPrestamo);
	public Cuota obtenerCuotaPorNumeroYID(int numeroCuota, int idPrestamo);
	public ArrayList<Cuota> ListarPorIdPrestamo(int idPrestamo);
	public ArrayList<Cuota> ListarPorClienteAprobadoEImpago(int idCliente);
}
