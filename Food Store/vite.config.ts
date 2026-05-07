import { defineConfig } from "vite";
import { resolve } from "path";

export default defineConfig({
  root: ".",
  build: {
    rollupOptions: {
      input: {
        // ── entradas existentes ──
        main:     resolve(__dirname, "index.html"),
        login:    resolve(__dirname, "src/pages/auth/login/index.html"),
        registro: resolve(__dirname, "src/pages/auth/registro/index.html"),
        admin:    resolve(__dirname, "src/pages/admin/index.html"),
        client:   resolve(__dirname, "src/pages/client/index.html"),
        // ── nuevas entradas del parcial ──
        storeHome: resolve(__dirname, "src/pages/store/home/home.html"),
        storeCart: resolve(__dirname, "src/pages/store/cart/cart.html"),
      },
    },
  },
});