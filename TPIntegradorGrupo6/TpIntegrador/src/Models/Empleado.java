package Models;

import java.time.LocalDate;
import java.util.Objects;

public class Empleado extends Base {
    private String nombre;
    private String apellido;
    private String dni;          
    private String email;
    private LocalDate fechaIngreso;
    private String area;
    private Legajo legajo;       // Relación 1→1 unidireccional

     public Empleado(Long id, String nombre, String apellido, String dni, String email, 
                   LocalDate fechaIngreso, String area) {
        super(id, false);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.area = area;
    }

    // Constructor por defecto
    public Empleado() {
        super();
    }

   // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    
    public Legajo getLegajo() { return legajo; }
    public void setLegajo(Legajo legajo) { this.legajo = legajo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(dni, empleado.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", area='" + area + '\'' +
                ", legajo=" + legajo +
                '}';
    }
}