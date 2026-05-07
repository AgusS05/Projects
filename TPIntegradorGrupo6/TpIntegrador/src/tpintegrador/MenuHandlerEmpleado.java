package tpintegrador;


import Models.Empleado;
import Models.Legajo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import Service.EmpleadoServiceImpl;

public class MenuHandlerEmpleado {
    private final Scanner scanner;
    private final EmpleadoServiceImpl empleadoService;

    public MenuHandlerEmpleado(Scanner scanner, EmpleadoServiceImpl empleadoService) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner no puede ser null");
        }
        if (empleadoService == null) {
            throw new IllegalArgumentException("EmpleadoService no puede ser null");
        }
        this.scanner = scanner;
        this.empleadoService = empleadoService;
    }

    public void crearEmpleado() {
        try {
            System.out.println("\n--- CREAR NUEVO EMPLEADO ---");
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();
            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Fecha ingreso (YYYY-MM-DD): ");
            String fechaIngresoStr = scanner.nextLine().trim();
            System.out.print("Área: ");
            String area = scanner.nextLine().trim();

            LocalDate fechaIngreso = null;
            if (!fechaIngresoStr.isEmpty()) {
                try {
                    fechaIngreso = LocalDate.parse(fechaIngresoStr);
                } catch (DateTimeParseException e) {
                    System.err.println("Formato de fecha inválido. Use YYYY-MM-DD");
                    return;
                }
            }

            Legajo legajo = null;
            System.out.print("¿Desea agregar un legajo? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                legajo = crearLegajo();
            }

            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setDni(dni);
            empleado.setEmail(email);
            empleado.setFechaIngreso(fechaIngreso);
            empleado.setArea(area);
            empleado.setLegajo(legajo);

            empleadoService.insertar(empleado);
            System.out.println("✅ Empleado creado exitosamente con ID: " + empleado.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al crear empleado: " + e.getMessage());
        }
    }

    public void listarEmpleados() {
        try {
            System.out.println("\n--- LISTAR EMPLEADOS ---");
            System.out.print("¿Desea (1) listar todos o (2) buscar por nombre/apellido? Ingrese opción: ");
            int subopcion = Integer.parseInt(scanner.nextLine());

            List<Empleado> empleados;
            if (subopcion == 1) {
                empleados = empleadoService.getAll();
            } else if (subopcion == 2) {
                System.out.print("Ingrese texto a buscar: ");
                String filtro = scanner.nextLine().trim();
                empleados = empleadoService.buscarPorNombreApellido(filtro);
            } else {
                System.out.println("❌ Opción inválida.");
                return;
            }

            if (empleados.isEmpty()) {
                System.out.println("ℹ️ No se encontraron empleados.");
                return;
            }

            System.out.println("\n--- RESULTADOS ---");
            for (Empleado e : empleados) {
                System.out.println("ID: " + e.getId() + 
                                 ", Nombre: " + e.getNombre() + " " + e.getApellido() +
                                 ", DNI: " + e.getDni() +
                                 ", Email: " + e.getEmail() + 
                                 ", Área: " + e.getArea() +
                                 ", Fecha Ingreso: " + e.getFechaIngreso());
                if (e.getLegajo() != null) {
                    System.out.println("   📋 Legajo: " + e.getLegajo().getNroLegajo() +
                                     " - " + e.getLegajo().getCategoria() + 
                                     " (" + e.getLegajo().getEstado() + ")");
                }
                System.out.println("   ---");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al listar empleados: " + e.getMessage());
        }
    }

    public void actualizarEmpleado() {
        try {
            System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
            System.out.print("ID del empleado a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());
            Empleado e = empleadoService.getById(id);

            if (e == null) {
                System.out.println("❌ Empleado no encontrado.");
                return;
            }

            System.out.println("Empleado actual: " + e.getNombre() + " " + e.getApellido() + " (DNI: " + e.getDni() + ")");
            
            System.out.print("Nuevo nombre (Enter para mantener '" + e.getNombre() + "'): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                e.setNombre(nombre);
            }

            System.out.print("Nuevo apellido (Enter para mantener '" + e.getApellido() + "'): ");
            String apellido = scanner.nextLine().trim();
            if (!apellido.isEmpty()) {
                e.setApellido(apellido);
            }

            System.out.print("Nuevo DNI (Enter para mantener '" + e.getDni() + "'): ");
            String dni = scanner.nextLine().trim();
            if (!dni.isEmpty()) {
                e.setDni(dni);
            }

            System.out.print("Nuevo email (Enter para mantener '" + e.getEmail() + "'): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) {
                e.setEmail(email);
            }

            System.out.print("Nueva área (Enter para mantener '" + e.getArea() + "'): ");
            String area = scanner.nextLine().trim();
            if (!area.isEmpty()) {
                e.setArea(area);
            }

            actualizarLegajoDeEmpleado(e);
            empleadoService.actualizar(e);
            System.out.println("✅ Empleado actualizado exitosamente.");
        } catch (Exception ex) {
            System.err.println("❌ Error al actualizar empleado: " + ex.getMessage());
        }
    }

    public void eliminarEmpleado() {
        try {
            System.out.println("\n--- ELIMINAR EMPLEADO ---");
            System.out.print("ID del empleado a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            System.out.print("¿Está seguro de eliminar este empleado? (s/n): ");
            String confirmacion = scanner.nextLine().trim();
            if (!confirmacion.equalsIgnoreCase("s")) {
                System.out.println("❌ Eliminación cancelada.");
                return;
            }
            
            empleadoService.eliminar(id);
            System.out.println("✅ Empleado eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar empleado: " + e.getMessage());
        }
    }

    public void crearLegajoIndependiente() {
        try {
            System.out.println("\n--- CREAR LEGAJO INDEPENDIENTE ---");
            Legajo legajo = crearLegajo();
            empleadoService.getLegajoService().insertar(legajo);
            System.out.println("✅ Legajo creado exitosamente con ID: " + legajo.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al crear legajo: " + e.getMessage());
        }
    }

    public void listarLegajos() {
        try {
            System.out.println("\n--- LISTAR LEGAJOS ---");
            List<Legajo> legajos = empleadoService.getLegajoService().getAll();
            if (legajos.isEmpty()) {
                System.out.println("ℹ️ No se encontraron legajos.");
                return;
            }
            
            System.out.println("\n--- LEGAJOS ACTIVOS ---");
            for (Legajo l : legajos) {
                System.out.println("ID: " + l.getId() + 
                                 ", Número: " + l.getNroLegajo() + 
                                 ", Categoría: " + l.getCategoria() + 
                                 ", Estado: " + l.getEstado() +
                                 ", Fecha Alta: " + l.getFechaAlta());
                if (l.getObservaciones() != null && !l.getObservaciones().isEmpty()) {
                    System.out.println("   Observaciones: " + l.getObservaciones());
                }
                System.out.println("   ---");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al listar legajos: " + e.getMessage());
        }
    }

    public void actualizarLegajoPorId() {
        try {
            System.out.println("\n--- ACTUALIZAR LEGAJO POR ID ---");
            System.out.print("ID del legajo a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());
            Legajo legajo = empleadoService.getLegajoService().getById(id);

            if (legajo == null) {
                System.out.println("❌ Legajo no encontrado.");
                return;
            }

            System.out.println("Legajo actual: " + legajo.getNroLegajo() + " - " + legajo.getCategoria());
            
            System.out.print("Nuevo número (Enter para mantener '" + legajo.getNroLegajo() + "'): ");
            String nroLegajo = scanner.nextLine().trim();
            if (!nroLegajo.isEmpty()) {
                legajo.setNroLegajo(nroLegajo);
            }

            System.out.print("Nueva categoría (Enter para mantener '" + legajo.getCategoria() + "'): ");
            String categoria = scanner.nextLine().trim();
            if (!categoria.isEmpty()) {
                legajo.setCategoria(categoria);
            }

            System.out.print("Nuevo estado (ACTIVO/INACTIVO) (Enter para mantener '" + legajo.getEstado() + "'): ");
            String estadoStr = scanner.nextLine().trim();
            if (!estadoStr.isEmpty()) {
                legajo.setEstado(Legajo.EstadoLegajo.valueOf(estadoStr.toUpperCase()));
            }

            System.out.print("Nueva fecha alta (YYYY-MM-DD) (Enter para mantener '" + legajo.getFechaAlta() + "'): ");
            String fechaAltaStr = scanner.nextLine().trim();
            if (!fechaAltaStr.isEmpty()) {
                legajo.setFechaAlta(LocalDate.parse(fechaAltaStr));
            }

            System.out.print("Nuevas observaciones (Enter para mantener actual): ");
            String observaciones = scanner.nextLine().trim();
            if (!observaciones.isEmpty()) {
                legajo.setObservaciones(observaciones);
            }

            empleadoService.getLegajoService().actualizar(legajo);
            System.out.println("✅ Legajo actualizado exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar legajo: " + e.getMessage());
        }
    }

    public void eliminarLegajoPorId() {
        try {
            System.out.println("\n--- ELIMINAR LEGAJO POR ID ---");
            System.out.print("ID del legajo a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            System.out.print("¿Está seguro de eliminar este legajo? (s/n): ");
            String confirmacion = scanner.nextLine().trim();
            if (!confirmacion.equalsIgnoreCase("s")) {
                System.out.println("❌ Eliminación cancelada.");
                return;
            }
            
            empleadoService.getLegajoService().eliminar(id);
            System.out.println("✅ Legajo eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar legajo: " + e.getMessage());
        }
    }

    public void actualizarLegajoPorEmpleado() {
        try {
            System.out.println("\n--- ACTUALIZAR LEGAJO POR EMPLEADO ---");
            System.out.print("ID del empleado: ");
            Long empleadoId = Long.parseLong(scanner.nextLine());
            Empleado empleado = empleadoService.getById(empleadoId);

            if (empleado == null) {
                System.out.println("❌ Empleado no encontrado.");
                return;
            }

            if (empleado.getLegajo() == null) {
                System.out.println("❌ Este empleado no tiene legajo asociado.");
                return;
            }

            Legajo legajo = empleado.getLegajo();
            System.out.println("Legajo actual: " + legajo.getNroLegajo() + " - " + legajo.getCategoria());
            
            actualizarLegajoDeEmpleado(empleado);
            System.out.println("✅ Legajo actualizado exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar legajo: " + e.getMessage());
        }
    }

    public void eliminarLegajoPorEmpleado() {
        try {
            System.out.println("\n--- ELIMINAR LEGAJO POR EMPLEADO ---");
            System.out.print("ID del empleado: ");
            Long empleadoId = Long.parseLong(scanner.nextLine());
            Empleado empleado = empleadoService.getById(empleadoId);

            if (empleado == null) {
                System.out.println("❌ Empleado no encontrado.");
                return;
            }

            if (empleado.getLegajo() == null) {
                System.out.println("❌ Este empleado no tiene legajo asociado.");
                return;
            }

            Long legajoId = empleado.getLegajo().getId();
            
            System.out.print("¿Está seguro de eliminar el legajo " + empleado.getLegajo().getNroLegajo() + "? (s/n): ");
            String confirmacion = scanner.nextLine().trim();
            if (!confirmacion.equalsIgnoreCase("s")) {
                System.out.println("❌ Eliminación cancelada.");
                return;
            }
            
            empleadoService.eliminarLegajoDeEmpleado(empleadoId, legajoId);
            System.out.println("✅ Legajo eliminado exitosamente y desasociado del empleado.");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar legajo: " + e.getMessage());
        }
    }

    private Legajo crearLegajo() {
        System.out.println("\n--- DATOS DEL LEGAJO ---");
        System.out.print("Número de legajo: ");
        String nroLegajo = scanner.nextLine().trim();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine().trim();
        
        String estadoStr;
        do {
            System.out.print("Estado (ACTIVO/INACTIVO): ");
            estadoStr = scanner.nextLine().trim().toUpperCase();
            if (!estadoStr.equals("ACTIVO") && !estadoStr.equals("INACTIVO")) {
                System.out.println("❌ Estado inválido. Use ACTIVO o INACTIVO.");
            }
        } while (!estadoStr.equals("ACTIVO") && !estadoStr.equals("INACTIVO"));
        
        System.out.print("Fecha alta (YYYY-MM-DD): ");
        String fechaAltaStr = scanner.nextLine().trim();
        System.out.print("Observaciones: ");
        String observaciones = scanner.nextLine().trim();

        Legajo.EstadoLegajo estado = Legajo.EstadoLegajo.valueOf(estadoStr);
        LocalDate fechaAlta = LocalDate.parse(fechaAltaStr);

        return new Legajo(0L, nroLegajo, categoria, estado, fechaAlta, observaciones);
    }

    private void actualizarLegajoDeEmpleado(Empleado e) throws Exception {
        if (e.getLegajo() != null) {
            System.out.print("¿Desea actualizar el legajo? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                Legajo legajo = e.getLegajo();
                
                System.out.print("Nueva categoría (" + legajo.getCategoria() + "): ");
                String categoria = scanner.nextLine().trim();
                if (!categoria.isEmpty()) {
                    legajo.setCategoria(categoria);
                }

                System.out.print("Nuevo estado (" + legajo.getEstado() + "): ");
                String estadoStr = scanner.nextLine().trim();
                if (!estadoStr.isEmpty()) {
                    legajo.setEstado(Legajo.EstadoLegajo.valueOf(estadoStr.toUpperCase()));
                }

                System.out.print("Nuevas observaciones (" + legajo.getObservaciones() + "): ");
                String observaciones = scanner.nextLine().trim();
                if (!observaciones.isEmpty()) {
                    legajo.setObservaciones(observaciones);
                }

                empleadoService.getLegajoService().actualizar(legajo);
            }
        } else {
            System.out.print("El empleado no tiene legajo. ¿Desea agregar uno? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                Legajo nuevoLegajo = crearLegajo();
                empleadoService.getLegajoService().insertar(nuevoLegajo);
                e.setLegajo(nuevoLegajo);
            }
        }
    }
}