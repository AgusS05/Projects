package Service;
import java.sql.Connection;
import java.util.List;

import Dao.GenericDAO;
import Models.Legajo;

public class LegajoServiceImpl implements GenericService<Legajo> {

    private final GenericDAO<Legajo> legajoDAO;

    public LegajoServiceImpl(GenericDAO<Legajo> legajoDAO) {
        if (legajoDAO == null) {
            throw new IllegalArgumentException("LegajoDAO no puede ser null");
        }
        this.legajoDAO = legajoDAO;
    }

    @Override
    public void insertar(Legajo legajo) throws Exception {
        validateLegajo(legajo);
        legajoDAO.insertar(legajo);
    }

    @Override
    public void actualizar(Legajo legajo) throws Exception {
        validateLegajo(legajo);
        if (legajo.getId() == null || legajo.getId() <= 0) {
            throw new IllegalArgumentException("El ID del legajo debe ser mayor a 0 para actualizar");
        }
        legajoDAO.actualizar(legajo);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        legajoDAO.eliminar(id);
    }

    @Override
    public Legajo getById(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return legajoDAO.getById(id);
    }

    @Override
    public List<Legajo> getAll() throws Exception {
        return legajoDAO.getAll();
    }

    private void validateLegajo(Legajo legajo) {
        if (legajo == null) {
            throw new IllegalArgumentException("El legajo no puede ser null");
        }
        if (legajo.getNroLegajo() == null || legajo.getNroLegajo().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de legajo no puede estar vacío");
        }
        if (legajo.getEstado() == null) {
            throw new IllegalArgumentException("El estado del legajo es obligatorio");
        }
        if (legajo.getFechaAlta() == null) {
            throw new IllegalArgumentException("La fecha de alta es obligatoria");
        }
    }

    /**
     * Método utilizado por EmpleadoServiceImpl para participar en una misma transacción
     * (insertar empleado + legajo).
     */
    public void insertTx(Legajo legajo, Connection conn) throws Exception {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        validateLegajo(legajo);
        legajoDAO.insertTx(legajo, conn);
    }
}
