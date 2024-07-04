import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";
import Cookies from "js-cookie";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getDataCookies(key: string) {
  return Cookies.get(key);
}
// export function getDataLocalStorage(key: string) {
//   return localStorage.getItem(key);
// }
