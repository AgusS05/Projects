import type { IUser } from "../../../types/IUser";

const form = document.getElementById("form-login") as HTMLFormElement;

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const email = (document.getElementById("email") as HTMLInputElement).value;
  const password = (document.getElementById("password") as HTMLInputElement).value;

  // Traemos los usuarios del localStorage
  const usersGuardados = localStorage.getItem("users");
  const users: IUser[] = usersGuardados ? JSON.parse(usersGuardados) : [];

  // Buscamos uno que coincida con email Y contraseña
  const usuarioEncontrado = users.find(
    (u) => u.email === email && u.password === password
  );

  if (!usuarioEncontrado) {
    alert("Email o contraseña incorrectos.");
    return;
  }

  // Guardamos el usuario en sesión y redirigimos según su rol
  localStorage.setItem("userData", JSON.stringify(usuarioEncontrado));

  if (usuarioEncontrado.rol === "admin") {
    window.location.href = "../../admin/index.html";
  } else {
    window.location.href = "../../client/index.html";
  }
});