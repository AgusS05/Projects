package Models;

/**
 * Clase base abstracta para todas las entidades del sistema.
 * Implementa el patrón de soft delete mediante el campo 'eliminado'.
 */
public abstract class Base {
    private Long id;
    private boolean eliminado;

    protected Base(Long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    protected Base() {
        this.eliminado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}