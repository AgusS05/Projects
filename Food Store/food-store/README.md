# 🍔 Food Store

Aplicación frontend de una tienda de comidas desarrollada con HTML5, CSS3, JavaScript y TypeScript, usando Vite como bundler.

## 📋 Descripción

Food Store permite a los usuarios registrarse, iniciar sesión y explorar un catálogo de productos con búsqueda y filtrado por categoría. Los productos pueden agregarse a un carrito de compras con persistencia en `localStorage`.

### Funcionalidades principales

- Registro e inicio de sesión con validación de credenciales
- Guard de rutas según rol (`admin` / `client`)
- Catálogo de productos con búsqueda por nombre y filtrado por categoría
- Carrito de compras persistente con `localStorage`
- Vista del carrito con nombre, precio, cantidad, subtotal y total general
- Panel de administración para gestionar productos

## 🚀 Instrucciones para ejecutarlo

### Requisitos previos

- Node.js instalado
- pnpm instalado (`npm install -g pnpm`)

### Pasos

```bash
# 1. Clonar el repositorio
git clone <url-del-repositorio>
cd food-store

# 2. Instalar dependencias
pnpm install

# 3. Levantar el servidor de desarrollo
pnpm dev
```

El servidor estará disponible en `http://localhost:5173`.

### Usuarios de prueba

| Rol | Email | Contraseña |
|-----|-------|------------|
| Admin | admin@foodstore.com | admin1234 |
| Cliente | (registrarse en /src/pages/auth/registro/) | - |

### Páginas del parcial

- Catálogo: `http://localhost:5173/src/pages/store/home/home.html`
- Carrito: `http://localhost:5173/src/pages/store/cart/cart.html`

## 🛠️ Tecnologías utilizadas

- HTML5
- CSS3
- TypeScript
- Vite