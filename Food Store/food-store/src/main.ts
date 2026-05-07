import { initAdmin } from "./utils/initAdmin";
import type { IUser } from "./types/IUser";

// Inicializar admin
initAdmin();

// Redirigir según sesión
const userData = localStorage.getItem("userData");

if (!userData) {
  window.location.href = "/src/pages/auth/login/index.html";
} else {
  const user: IUser = JSON.parse(userData);
  if (user.rol === "admin") {
    window.location.href = "/src/pages/admin/index.html";
  } else {
    window.location.href = "/src/pages/client/index.html";
  }
}