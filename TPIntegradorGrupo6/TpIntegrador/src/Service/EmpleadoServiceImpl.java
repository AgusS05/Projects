package Service;

import Config.DatabaseConnection;
import Dao.EmpleadoDAO;
import Models.Empleado;


import java.sql.Connection;
import java.util.List;

public class EmpleadoServiceImpl implements GenericService<Empleado> {

    private final EmpleadoDAO empleadoDAO;
    private final LegajoServiceImpl legajoServiceImpl;

    public EmpleadoServiceImpl(EmpleadoDAO empleadoDAO, LegajoServiceImpl legajoServiceImpl) {
        if (empleadoDAO == null) {
            throw new IllegalArgumentException("EmpleadoDAO no puede ser null");
        }
        if (legajoServiceImpl == null) {
            throw new IllegalArgumentException("LegajoServiceImpl no puede ser null");
        }
        this.empleadoDAO = empleadoDAO;
        this.legajoServiceImpl = legajoServiceImpl;
    }

    /**
     * INSERCIÓN CON TRANSACCIÓN - Empleado + Legajo
     */
    @Override
    public void insertar(Empleado empleado) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Validaciones
                validateEmpleado(empleado);
                validateDniUnique(empleado.getDni(), null);

                // Insertar empleado con connection compartida
                empleadoDAO.insertTx(empleado, conn);

                // Insertar legajo si existe
                if (empleado.getLegajo() != null) {
                    empleado.getLegajo().setId(empleado.getId()); // ajustar FK según tu modelo
                    legajoServiceImpl.insertTx(empleado.getLegajo(), conn);
                }

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public void actualizar(Empleado empleado) throws Exception {
        validateEmpleado(empleado);
        if (empleado.getId() == null || empleado.getId() <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor a 0 para actualizar");
        }
        validateDniUnique(empleado.getDni(), empleado.getId());
        empleadoDAO.actualizar(empleado);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        empleadoDAO.eliminar(id);
    }

    @Override
    public Empleado getById(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return empleadoDAO.getById(id);
    }

    @Override
    public List<Empleado> getAll() throws Exception {
        return empleadoDAO.getAll();
    }

    public List<Empleado> buscarPorNombreApellido(String filtro) throws Exception {
        if (filtro == null || filtro.trim().isEmpty()) {
            throw new IllegalArgumentException("El filtro de búsqueda no puede estar vacío");
        }
        return empleadoDAO.buscarPorNombreApellido(filtro);
    }

    public Empleado buscarPorDni(String dni) throws Exception {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }
        return empleadoDAO.buscarPorDni(dni);
    }

    public void eliminarLegajoDeEmpleado(Long empleadoId, Long legajoId) throws Exception {
        if (empleadoId == null || empleadoId <= 0 || legajoId == null || legajoId <= 0) {
            throw new IllegalArgumentException("Los IDs deben ser mayores a 0");
        }

        Empleado empleado = empleadoDAO.getById(empleadoId);
        if (empleado == null) {
            throw new IllegalArgumentException("Empleado no encontrado con ID: " + empleadoId);
        }

        if (empleado.getLegajo() == null || !empleado.getLegajo().getId().equals(legajoId)) {
            throw new IllegalArgumentException("El legajo no pertenece a este empleado");
        }

        empleado.setLegajo(null);
        empleadoDAO.actualizar(empleado);
        legajoServiceImpl.eliminar(legajoId);
    }

    public LegajoServiceImpl getLegajoService() {
        return this.legajoServiceImpl;
    }

    // --------- VALIDACIONES PRIVADAS ---------

    private void validateEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (empleado.getApellido() == null || empleado.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (empleado.getDni() == null || empleado.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }
        if (empleado.getEmail() != null && !empleado.getEmail().isEmpty()) {
            if (!isValidEmail(empleado.getEmail())) {
                throw new IllegalArgumentException("Formato de email inválido");
            }
        }
    }

    private void validateDniUnique(String dni, Long excludeId) throws Exception {
        Empleado existente = empleadoDAO.buscarPorDni(dni);
        if (existente != null) {
            if (excludeId == null || !existente.getId().equals(excludeId)) {
                throw new IllegalArgumentException("Ya existe un empleado con el DNI: " + dni);
            }
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}