import { checkAuth } from "../../../utils/auth";
import { navigateTo } from "../../../utils/navigate";
import type { CartItem } from "../../../types/product";

checkAuth("client");

document.getElementById("btn-logout")?.addEventListener("click", () => {
  localStorage.removeItem("userData");
  navigateTo("/src/pages/auth/login/index.html");
});

// ---- Helpers localStorage ----
const getCart = (): CartItem[] => {
  const raw = localStorage.getItem("cart");
  return raw ? JSON.parse(raw) : [];
};

const saveCart = (cart: CartItem[]): void => {
  localStorage.setItem("cart", JSON.stringify(cart));
};

// ---- Calcular total ----
const calcularTotal = (cart: CartItem[]): number =>
  cart.reduce((acc, item) => acc + item.product.precio * item.cantidad, 0);

// ---- Renderizar ----
const renderizarCarrito = (): void => {
  const contenedor = document.getElementById("contenedor-carrito");
  const listaSubtotales = document.getElementById("lista-subtotales");
  const totalDiv = document.getElementById("total-carrito");
  const msgFinalizar = document.getElementById("msg-finalizar");
  if (!contenedor || !listaSubtotales || !totalDiv || !msgFinalizar) return;

  const cart = getCart();
  contenedor.innerHTML = "";
  listaSubtotales.innerHTML = "";
  msgFinalizar.textContent = "";

  if (cart.length === 0) {
    contenedor.innerHTML = "<p class='carrito-vacio'>Tu carrito está vacío.</p>";
    totalDiv.innerHTML = "";
    return;
  }

  // ── Productos ──
 cart.forEach((item: CartItem) => {
    const article = document.createElement("article");
    article.classList.add("carrito-item");
    article.innerHTML = `
      <img src="${item.product.imagen}" alt="${item.product.nombre}">
      <div class="carrito-item-info">
        <h3>${item.product.nombre}</h3>
        <p>$${item.product.precio.toLocaleString("es-AR")} por unidad</p>
        <div class="cantidad-controles">
          <button class="btn-restar">−</button>
          <span class="cantidad-numero">${item.cantidad}</span>
          <button class="btn-sumar">+</button>
        </div>
      </div>
      <div class="carrito-item-subtotal">
        <p>$${(item.product.precio * item.cantidad).toLocaleString("es-AR")}</p>
        <button class="btn-eliminar">✕</button>
      </div>
    `;

    // Eventos en cada botón
    article.querySelector(".btn-sumar")?.addEventListener("click", () => {
      const cart = getCart();
      const found = cart.find((i) => i.product.id === item.product.id);
      if (found) found.cantidad += 1;
      saveCart(cart);
      renderizarCarrito();
    });

    article.querySelector(".btn-restar")?.addEventListener("click", () => {
      let cart = getCart();
      const found = cart.find((i) => i.product.id === item.product.id);
      if (found) {
        found.cantidad -= 1;
        if (found.cantidad <= 0) cart = cart.filter((i) => i.product.id !== item.product.id);
      }
      saveCart(cart);
      renderizarCarrito();
    });

    article.querySelector(".btn-eliminar")?.addEventListener("click", () => {
      const cart = getCart().filter((i) => i.product.id !== item.product.id);
      saveCart(cart);
      renderizarCarrito();
    });

    contenedor.appendChild(article);
  });

  // Subtotales en el resumen 
  cart.forEach((item: CartItem) => {
    const div = document.createElement("div");
    div.classList.add("resumen-linea");
    div.innerHTML = `
      <span>${item.product.nombre} x${item.cantidad}</span>
      <span>$${(item.product.precio * item.cantidad).toLocaleString("es-AR")}</span>
    `;
    listaSubtotales.appendChild(div);
  });

  // Total 
  totalDiv.innerHTML = `
    <div class="resumen-total">
      <span>Total</span>
      <span>$${calcularTotal(cart).toLocaleString("es-AR")}</span>
    </div>
  `;

  // Eventos de cantidad y eliminar 
  contenedor.addEventListener("click", (e) => {
    const target = e.target as HTMLElement;
    const id = Number(target.dataset.id);
    if (!id) return;

    let cart = getCart();

    if (target.classList.contains("btn-sumar")) {
      const item = cart.find((i) => i.product.id === id);
      if (item) item.cantidad += 1;

    } else if (target.classList.contains("btn-restar")) {
      const item = cart.find((i) => i.product.id === id);
      if (item) {
        item.cantidad -= 1;
        if (item.cantidad <= 0) cart = cart.filter((i) => i.product.id !== id);
      }

    } else if (target.classList.contains("btn-eliminar")) {
      cart = cart.filter((i) => i.product.id !== id);
    }

    saveCart(cart);
    renderizarCarrito();
  });
};

// ---- Finalizar compra ----
document.getElementById("btn-finalizar")?.addEventListener("click", () => {
  const msg = document.getElementById("msg-finalizar");
  if (msg) msg.textContent = "⚠️ El checkout no está disponible en esta versión.";
});

// ---- Vaciar carrito ----
document.getElementById("btn-vaciar")?.addEventListener("click", () => {
  localStorage.removeItem("cart");
  renderizarCarrito();
});

// Carga inicial
renderizarCarrito();