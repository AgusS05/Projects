package Dao;

import Models.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Config.DatabaseConnection;
import Models.Legajo;

public class EmpleadoDAO implements GenericDAO<Empleado> {
    private static final String INSERT_SQL = 
        "INSERT INTO empleados (nombre, apellido, dni, email, fecha_ingreso, area) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_SQL = 
        "UPDATE empleados SET nombre = ?, apellido = ?, dni = ?, email = ?, fecha_ingreso = ?, area = ? WHERE id = ?";
    
    private static final String DELETE_SQL = 
        "UPDATE empleados SET eliminado = TRUE WHERE id = ?";
    
    private static final String SELECT_BY_ID_SQL = 
        "SELECT e.*, l.id as leg_id, l.nro_legajo, l.categoria, l.estado, l.fecha_alta, l.observaciones " +
        "FROM empleados e LEFT JOIN legajos l ON e.id = l.empleado_id " +
        "WHERE e.id = ? AND e.eliminado = FALSE";
    
    private static final String SELECT_ALL_SQL = 
        "SELECT e.*, l.id as leg_id, l.nro_legajo, l.categoria, l.estado, l.fecha_alta, l.observaciones " +
        "FROM empleados e LEFT JOIN legajos l ON e.id = l.empleado_id " +
        "WHERE e.eliminado = FALSE";
    
    private static final String SEARCH_BY_NAME_SQL = 
        "SELECT e.*, l.id as leg_id, l.nro_legajo, l.categoria, l.estado, l.fecha_alta, l.observaciones " +
        "FROM empleados e LEFT JOIN legajos l ON e.id = l.empleado_id " +
        "WHERE e.eliminado = FALSE AND (e.nombre LIKE ? OR e.apellido LIKE ?)";
    
    private static final String SEARCH_BY_DNI_SQL = 
        "SELECT e.*, l.id as leg_id, l.nro_legajo, l.categoria, l.estado, l.fecha_alta, l.observaciones " +
        "FROM empleados e LEFT JOIN legajos l ON e.id = l.empleado_id " +
        "WHERE e.eliminado = FALSE AND e.dni = ?";

    private final LegajoDAO legajoDAO;

    public EmpleadoDAO(LegajoDAO legajoDAO) {
        if (legajoDAO == null) {
            throw new IllegalArgumentException("LegajoDAO no puede ser null");
        }
        this.legajoDAO = legajoDAO;
    }

    @Override
    public void insertar(Empleado empleado) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            setEmpleadoParameters(stmt, empleado);
            stmt.executeUpdate();
            setGeneratedId(stmt, empleado);
        }
    }

    @Override
    public void insertTx(Empleado empleado, Connection conn) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setEmpleadoParameters(stmt, empleado);
            stmt.executeUpdate();
            setGeneratedId(stmt, empleado);
        }
    }

    @Override
    public void actualizar(Empleado empleado) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            setEmpleadoParameters(stmt, empleado);
            stmt.setLong(7, empleado.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el empleado con ID: " + empleado.getId());
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se encontró empleado con ID: " + id);
            }
        }
    }

    @Override
    public Empleado getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmpleado(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Empleado> getAll() throws Exception {
        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                empleados.add(mapResultSetToEmpleado(rs));
            }
        }
        return empleados;
    }

    public List<Empleado> buscarPorNombreApellido(String filtro) throws SQLException {
        if (filtro == null || filtro.trim().isEmpty()) {
            throw new IllegalArgumentException("El filtro de búsqueda no puede estar vacío");
        }

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_NAME_SQL)) {

            String searchPattern = "%" + filtro + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(mapResultSetToEmpleado(rs));
                }
            }
        }
        return empleados;
    }

    public Empleado buscarPorDni(String dni) throws SQLException {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_DNI_SQL)) {

            stmt.setString(1, dni.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmpleado(rs);
                }
            }
        }
        return null;
    }

    private void setEmpleadoParameters(PreparedStatement stmt, Empleado empleado) throws SQLException {
        stmt.setString(1, empleado.getNombre());
        stmt.setString(2, empleado.getApellido());
        stmt.setString(3, empleado.getDni());
        stmt.setString(4, empleado.getEmail());
        stmt.setDate(5, empleado.getFechaIngreso() != null ? Date.valueOf(empleado.getFechaIngreso()) : null);
        stmt.setString(6, empleado.getArea());
    }

    private void setGeneratedId(PreparedStatement stmt, Empleado empleado) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                empleado.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("La inserción del empleado falló, no se obtuvo ID generado");
            }
        }
    }

    private Empleado mapResultSetToEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getLong("id"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellido(rs.getString("apellido"));
        empleado.setDni(rs.getString("dni"));
        empleado.setEmail(rs.getString("email"));
        
        Date fechaIngreso = rs.getDate("fecha_ingreso");
        if (fechaIngreso != null) {
            empleado.setFechaIngreso(fechaIngreso.toLocalDate());
        }
        
        empleado.setArea(rs.getString("area"));

        // Mapear legajo si existe
        try {
            Long legajoId = rs.getLong("leg_id");
            if (legajoId > 0 && !rs.wasNull()) {
                Legajo legajo = new Legajo();
                legajo.setId(rs.getLong("leg_id"));
                legajo.setNroLegajo(rs.getString("nro_legajo"));
                legajo.setCategoria(rs.getString("categoria"));
                legajo.setEstado(Legajo.EstadoLegajo.valueOf(rs.getString("estado")));
                legajo.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());
                legajo.setObservaciones(rs.getString("observaciones"));
                empleado.setLegajo(legajo);
            }
        } catch (SQLException e) {
            // Si no hay legajo, continuar sin error
        }

        return empleado;
    }

    public void insertTx(Empleado entidad, com.sun.jdi.connect.spi.Connection conn) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}