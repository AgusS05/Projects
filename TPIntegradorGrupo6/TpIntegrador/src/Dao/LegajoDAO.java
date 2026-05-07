package Dao;
import Config.DatabaseConnection;
import Models.Legajo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class LegajoDAO implements GenericDAO<Legajo> {

    private static final String INSERT_SQL =
            "INSERT INTO legajos (nro_legajo, categoria, estado, fecha_alta, observaciones, empleado_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE legajos SET nro_legajo = ?, categoria = ?, estado = ?, fecha_alta = ?, observaciones = ?, empleado_id = ? " +
            "WHERE id = ?";

    private static final String DELETE_SQL =
            "UPDATE legajos SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM legajos WHERE id = ? AND eliminado = FALSE";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM legajos WHERE eliminado = FALSE";

    @Override
    public void insertar(Legajo legajo) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            setLegajoParameters(stmt, legajo);
            stmt.executeUpdate();
            setGeneratedId(stmt, legajo);
        }
    }

    @Override
    public void insertTx(Legajo legajo, Connection conn) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setLegajoParameters(stmt, legajo);
            stmt.executeUpdate();
            setGeneratedId(stmt, legajo);
        }
    }

    @Override
    public void actualizar(Legajo legajo) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            setLegajoParameters(stmt, legajo);
            stmt.setLong(7, legajo.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el legajo con ID: " + legajo.getId());
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se encontró legajo con ID: " + id);
            }
        }
    }

    @Override
    public Legajo getById(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLegajo(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Legajo> getAll() throws SQLException {
        List<Legajo> legajos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                legajos.add(mapResultSetToLegajo(rs));
            }
        }
        return legajos;
    }

    private void setLegajoParameters(PreparedStatement stmt, Legajo legajo) throws SQLException {
        stmt.setString(1, legajo.getNroLegajo());
        stmt.setString(2, legajo.getCategoria());
        stmt.setString(3, legajo.getEstado().name());
        stmt.setDate(4, Date.valueOf(legajo.getFechaAlta()));
        stmt.setString(5, legajo.getObservaciones());

        // Manejar FK empleado_id (por ahora se utiliza el id del legajo, según tu diseño actual)
        if (legajo.getId() != null && legajo.getId() > 0) {
            stmt.setLong(6, legajo.getId());
        } else {
            stmt.setNull(6, Types.BIGINT);
        }
    }

    private void setGeneratedId(PreparedStatement stmt, Legajo legajo) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                legajo.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("La inserción del legajo falló, no se obtuvo ID generado");
            }
        }
    }

    private Legajo mapResultSetToLegajo(ResultSet rs) throws SQLException {
        return new Legajo(
                rs.getLong("id"),
                rs.getString("nro_legajo"),
                rs.getString("categoria"),
                Legajo.EstadoLegajo.valueOf(rs.getString("estado")),
                rs.getDate("fecha_alta").toLocalDate(),
                rs.getString("observaciones")
        );
    }
}