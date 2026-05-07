import { checkAuth } from "../../../utils/auth";
import { navigateTo } from "../../../utils/navigate";
import { PRODUCTS, getCategories } from "../../../data/data";
import type { Product } from "../../../types/product";
import type { CartItem } from "../../../types/product";

// Guard
checkAuth("client");

// Logout
document.getElementById("btn-logout")?.addEventListener("click", () => {
  localStorage.removeItem("userData");
  navigateTo("/src/pages/auth/login/index.html");
});

// ---- Carrito ----
const getCart = (): CartItem[] => {
  const raw = localStorage.getItem("cart");
  return raw ? JSON.parse(raw) : [];
};

const saveCart = (cart: CartItem[]): void => {
  localStorage.setItem("cart", JSON.stringify(cart));
};

const addToCart = (product: Product): void => {
  const cart = getCart();
  const existing = cart.find((item) => item.product.id === product.id);

  if (existing) {
    existing.cantidad += 1;
  } else {
    cart.push({ product, cantidad: 1 });
  }

  saveCart(cart);
  alert(`✅ "${product.nombre}" agregado al carrito`);
};

// ---- Renderizar Productos ----
const renderizarProductos = (lista: Product[]): void => {
  const contenedor = document.getElementById("contenedor-productos");
  if (!contenedor) return;

  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron productos.</p>";
    return;
  }

  lista.forEach((producto: Product) => {
    const article = document.createElement("article");
    article.innerHTML = `
      <img src="${producto.imagen}" alt="Imagen de ${producto.nombre}">
      <h3>${producto.nombre}</h3>
      <p>${producto.descripcion}</p>
      <p>Precio: <strong>$${producto.precio.toLocaleString("es-AR")}</strong></p>
      <button class="btn-agregar" data-id="${producto.id}">Agregar al Carrito</button>
    `;
    contenedor.appendChild(article);
  });

  // Eventos de los botones
  contenedor.addEventListener("click", (e) => {
    const target = e.target as HTMLElement;
    if (!target.classList.contains("btn-agregar")) return;

    const id = Number(target.dataset.id);
    const producto = PRODUCTS.find((p) => p.id === id);
    if (producto) addToCart(producto);
  });
};

// ---- Renderizar Categorías ----
const cargarCategorias = (): void => {
  const lista = document.getElementById("lista-categorias");
  if (!lista) return;

  getCategories().forEach((categoria: string) => {
    const li = document.createElement("li");
    li.innerHTML = `<a href="#" data-categoria="${categoria}">${categoria}</a>`;
    lista.appendChild(li);
  });

  lista.addEventListener("click", (e) => {
    const target = e.target as HTMLAnchorElement;
    if (target.tagName !== "A") return;
    e.preventDefault();

    const categoriaElegida = target.dataset.categoria ?? "Todas";
    const filtrados =
      categoriaElegida === "Todas"
        ? PRODUCTS
        : PRODUCTS.filter((p) => p.categoria === categoriaElegida);

    renderizarProductos(filtrados);
  });
};

// ---- Búsqueda ----
const form = document.getElementById("form-busqueda") as HTMLFormElement;
form.addEventListener("submit", (e) => {
  e.preventDefault();
  const input = document.getElementById("input-busqueda") as HTMLInputElement;
  const busqueda = input.value.toLowerCase().trim();

  const filtrados = PRODUCTS.filter((p) =>
    p.nombre.toLowerCase().includes(busqueda)
  );

  renderizarProductos(filtrados);
});

// Carga inicial
cargarCategorias();
renderizarProductos(PRODUCTS);