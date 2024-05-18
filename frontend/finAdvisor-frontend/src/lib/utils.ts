import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function setDataLocalStorage(data: string) {
  localStorage.setItem("token", data);
}

export function getDataLocalStorage(key: string) {
  return localStorage.getItem(key);
}
