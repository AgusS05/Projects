import type { IUser } from "../types/IUser";
import { navigateTo } from "./navigate";

export const checkAuth = (rolRequerido: "admin" | "client"): void => {

  // 1. ¿Hay alguien logueado?
  const userData = localStorage.getItem("userData");

  if (!userData) {
    // No hay sesión — mandamos al login
    navigateTo("/src/pages/auth/login/index.html");
    return;
  }

  // 2. ¿El rol del usuario coincide con el requerido?
  const user: IUser = JSON.parse(userData);

  if (user.rol !== rolRequerido) {
    // Tiene sesión pero no tiene permiso
    if (user.rol === "admin") {
      navigateTo("/src/pages/admin/index.html");
    } else {
      navigateTo("/src/pages/client/index.html");
    }
  }
};