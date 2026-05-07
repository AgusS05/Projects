import type { IUser } from "../../../types/IUser";

const form = document.getElementById("form-registro") as HTMLFormElement;

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const email = (document.getElementById("email") as HTMLInputElement).value;
  const password = (document.getElementById("password") as HTMLInputElement).value;

  // Traemos los usuarios existentes del localStorage, o un array vacío si no hay ninguno
  const usersGuardados = localStorage.getItem("users");
  const users: IUser[] = usersGuardados ? JSON.parse(usersGuardados) : [];

  // Verificamos que el email no esté registrado ya
  const yaExiste = users.some((u) => u.email === email);
  if (yaExiste) {
    alert("Ese email ya está registrado.");
    return;
  }

  // Creamos el nuevo usuario — el rol siempre es "client"
  const nuevoUsuario: IUser = {
    id: Date.now(),
    email,
    password,
    rol: "client"
  };

  // Lo agregamos al array y lo guardamos
  users.push(nuevoUsuario);
  localStorage.setItem("users", JSON.stringify(users));

  alert("¡Registro exitoso! Ahora podés iniciar sesión.");
  window.location.href = "../login/index.html";
});