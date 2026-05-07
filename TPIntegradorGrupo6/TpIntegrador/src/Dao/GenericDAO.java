package Dao;

import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T> {

    void insertar(T entidad) throws Exception;

    /**
     * Versión que utiliza una Connection externa para participar en una transacción.
     */
    void insertTx(T entidad, Connection conn) throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(Long id) throws Exception;

    T getById(Long id) throws Exception;

    List<T> getAll() throws Exception;
}