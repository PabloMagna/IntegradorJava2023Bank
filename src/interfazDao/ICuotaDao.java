package interfazDao;

import java.util.ArrayList;

import entidad.Cuota;
import entidad.Prestamo;

public interface ICuotaDao {
	public int GenerarCuotas(Prestamo prestamo);
	public int PagarCuota(int numeroCuota, int idPrestamo);
	public Cuota obtenerCuotaPorNumeroYID(int numeroCuota, int idPrestamo);
	public ArrayList<Cuota> ListarPorIdPrestamo(int idPrestamo);
	public ArrayList<Cuota> ListarPorClienteAprobadoEImpago(int idCliente);
}
