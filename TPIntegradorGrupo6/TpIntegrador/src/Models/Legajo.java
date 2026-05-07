package Models;

import java.time.LocalDate;
import java.util.Objects;

public class Legajo extends Base {
    private String nroLegajo;    // UNIQUE
    private String categoria;
    private EstadoLegajo estado;
    private LocalDate fechaAlta;
    private String observaciones;

    public enum EstadoLegajo {
        ACTIVO, INACTIVO
    }

    // Constructor completo
    public Legajo(Long id, String nroLegajo, String categoria, EstadoLegajo estado, 
                 LocalDate fechaAlta, String observaciones) {
        super(id, false);
        this.nroLegajo = nroLegajo;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.observaciones = observaciones;
    }

    // Constructor por defecto
    public Legajo() {
        super();
    }

    // Getters y Setters
    public String getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(String nroLegajo) { this.nroLegajo = nroLegajo; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public EstadoLegajo getEstado() { return estado; }
    public void setEstado(EstadoLegajo estado) { this.estado = estado; }
    
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Legajo legajo = (Legajo) o;
        return Objects.equals(nroLegajo, legajo.nroLegajo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroLegajo);
    }
}