/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpintegrador;

/**
 *
 * @author delar
 */
public class MenuDisplay {
    public static void mostrarMenuPrincipal() {
        System.out.println("\n========= SISTEMA EMPLEADOS-LEGAJOS =========");
        System.out.println("1. Crear empleado");
        System.out.println("2. Listar empleados");
        System.out.println("3. Actualizar empleado");
        System.out.println("4. Eliminar empleado");
        System.out.println("5. Crear legajo independiente");
        System.out.println("6. Listar legajos");
        System.out.println("7. Actualizar legajo por ID");
        System.out.println("8. Eliminar legajo por ID");
        System.out.println("9. Actualizar legajo por ID de empleado");
        System.out.println("10. Eliminar legajo por ID de empleado");
        System.out.println("0. Salir");
        System.out.print("Ingrese una opcion: ");
    }
}