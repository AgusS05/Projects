import { checkAuth } from "../../utils/auth";
import { navigateTo } from "../../utils/navigate";
import { PRODUCTS } from "../../data/data";
import type { Product } from "../../types/product";

// Guard
checkAuth("admin");

// Logout
document.getElementById("btn-logout")?.addEventListener("click", () => {
  localStorage.removeItem("userData");
  navigateTo("/src/pages/auth/login/index.html");
});

// ---- Renderizar tabla ----
const renderizarTabla = (lista: Product[]): void => {
  const tbody = document.getElementById("tbody-productos");
  if (!tbody) return;

  tbody.innerHTML = "";

  lista.forEach((producto: Product) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${producto.id}</td>
      <td><img src="${producto.imagen}" alt="${producto.nombre}" width="50"></td>
      <td>${producto.nombre}</td>
      <td>${producto.categoria}</td>
      <td>$${producto.precio.toLocaleString("es-AR")}</td>
      <td>${producto.descripcion}</td>
    `;
    tbody.appendChild(tr);
  });
};

// ---- Agregar producto ----
const form = document.getElementById("form-admin") as HTMLFormElement;

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const nombre = (document.getElementById("nombre") as HTMLInputElement).value;
  const precio = Number((document.getElementById("precio") as HTMLInputElement).value);
  const categoria = (document.getElementById("categoria") as HTMLSelectElement).value;
  const descripcion = (document.getElementById("descripcion") as HTMLTextAreaElement).value;

  const nuevoProducto: Product = {
    id: Date.now(),
    nombre,
    descripcion,
    precio,
    imagen: "../../assets/default.png",
    categoria
  };

  PRODUCTS.push(nuevoProducto);
  renderizarTabla(PRODUCTS);
  form.reset();
  alert(`Producto "${nombre}" agregado correctamente.`);
});

// Carga inicial
renderizarTabla(PRODUCTS);