package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_empleados?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "???";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
            validateConfiguration();
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Error: No se encontró el driver JDBC de MySQL: " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new ExceptionInInitializerError("Error en la configuración de la base de datos: " + e.getMessage());
        }
    }

    private DatabaseConnection() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private static void validateConfiguration() {
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("La URL de la base de datos no está configurada");
        }
        if (USER == null || USER.trim().isEmpty()) {
            throw new IllegalStateException("El usuario de la base de datos no está configurado");
        }
        // PASSWORD puede ser vacío (común en MySQL local con usuario root sin contraseña)
        // Solo validamos que no sea null
        if (PASSWORD == null) {
            throw new IllegalStateException("La contraseña de la base de datos no está configurada");
        }
    }
}