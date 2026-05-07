import type { Product } from "../types/product";

export const PRODUCTS: Product[] = [
  // ── Hamburguesas ──
  {
    id: 1,
    nombre: "Hamburguesa Doble Clásica",
    descripcion: "Dos medallones de carne con cheddar, lechuga, tomate y cebolla morada.",
    precio: 18000,
    imagen: "/assets/Hamburguesa.png",
    categoria: "Hamburguesas"
  },
  {
    id: 2,
    nombre: "Hamburguesa Bacon",
    descripcion: "Medallón de carne con bacon crocante, cheddar fundido y salsa BBQ.",
    precio: 17000,
    imagen: "/assets/HamburguesaBacon.png",
    categoria: "Hamburguesas"
  },
  {
    id: 3,
    nombre: "Hamburguesa Veggie",
    descripcion: "Medallón de garbanzo y vegetales con palta, rúcula y tomate.",
    precio: 15000,
    imagen: "/assets/HamburguesaVeggie.png",
    categoria: "Hamburguesas"
  },
  {
    id: 4,
    nombre: "Hamburguesa Doble Cheddar",
    descripcion: "Dos medallones con doble cheddar, pepinillos y mostaza.",
    precio: 16500,
    imagen: "/assets/HamburguesaDoble.png",
    categoria: "Hamburguesas"
  },
  // ── Pizzas ──
  {
    id: 5,
    nombre: "Pizza Muzzarella",
    descripcion: "Salsa de tomate casera, abundante muzzarella y aceitunas.",
    precio: 12000,
    imagen: "/assets/Pizza.png",
    categoria: "Pizzas"
  },
  {
    id: 6,
    nombre: "Pizza Napolitana",
    descripcion: "Muzzarella, rodajas de tomate fresco, ajo y albahaca.",
    precio: 13000,
    imagen: "/assets/PizzaNapolitana.png",
    categoria: "Pizzas"
  },
  {
    id: 7,
    nombre: "Pizza Fugazzeta",
    descripcion: "Masa gruesa rellena de muzzarella con cebolla caramelizada.",
    precio: 14000,
    imagen: "/assets/PizzaFugazzeta.png",
    categoria: "Pizzas"
  },
  {
    id: 8,
    nombre: "Pizza Cuatro Quesos",
    descripcion: "Muzzarella, provolone, roquefort y parmesano.",
    precio: 15000,
    imagen: "/assets/PizzaCuatroQuesos.png",
    categoria: "Pizzas"
  },
  // ── Papas Fritas ──
  {
    id: 9,
    nombre: "Papas Fritas",
    descripcion: "Papas fritas clásicas acompañadas con ketchup.",
    precio: 6000,
    imagen: "/assets/PapasFritas.png",
    categoria: "Papas Fritas"
  },
  {
    id: 10,
    nombre: "Papas con Bacon y Cheddar",
    descripcion: "Papas fritas con bacon crocante y salsa cheddar caliente.",
    precio: 9000,
    imagen: "/assets/PapasBaconCheddar.png",
    categoria: "Papas Fritas"
  },
  {
    id: 11,
    nombre: "Papas Rústicas",
    descripcion: "Papas con piel, condimentadas con romero y ajo, con dip de queso.",
    precio: 8000,
    imagen: "/assets/PapasRusticas.png",
    categoria: "Papas Fritas"
  },
  {
    id: 12,
    nombre: "Papas Picantes",
    descripcion: "Papas fritas con mezcla de especias picantes y salsa criolla.",
    precio: 7500,
    imagen: "/assets/PapasPicantes.png",
    categoria: "Papas Fritas"
  },
  // ── Bebidas ──
  {
    id: 13,
    nombre: "Gaseosa",
    descripcion: "Bebida fría a elección: Cola, Naranja o Lima Limón.",
    precio: 4000,
    imagen: "/assets/Gaseosa.png",
    categoria: "Bebidas"
  },
  {
    id: 14,
    nombre: "Agua Mineral",
    descripcion: "Agua mineral con o sin gas, bien fría.",
    precio: 2500,
    imagen: "/assets/Agua.png",
    categoria: "Bebidas"
  },
  {
    id: 15,
    nombre: "Limonada",
    descripcion: "Limonada fresca exprimida al momento, con o sin menta.",
    precio: 5000,
    imagen: "/assets/Limonada.png",
    categoria: "Bebidas"
  }
];

export const getCategories = (): string[] => [
  "Todas",
  "Hamburguesas",
  "Pizzas",
  "Papas Fritas",
  "Bebidas"
];