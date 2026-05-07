import type { IUser } from "../types/IUser";

export const initAdmin = (): void => {
  const usersGuardados = localStorage.getItem("users");
  const users: IUser[] = usersGuardados ? JSON.parse(usersGuardados) : [];

  // Si ya existe un admin no hacemos nada
  const adminExiste = users.some((u) => u.rol === "admin");
  if (adminExiste) return;

  // Creamos el admin inicial
  const admin: IUser = {
    id: 1,
    email: "admin@foodstore.com",
    password: "admin1234",
    rol: "admin"
  };

  users.push(admin);
  localStorage.setItem("users", JSON.stringify(users));
};