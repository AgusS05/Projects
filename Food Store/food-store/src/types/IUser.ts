// src/types/IUser.ts
import type { Rol } from "./Rol";

export interface IUser {
  id: number;
  email: string;
  password: string;
  rol: Rol;
}