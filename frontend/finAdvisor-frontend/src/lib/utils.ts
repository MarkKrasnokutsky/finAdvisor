import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";
import Cookies from "js-cookie";
import { ResponseData } from "@/types/auth";

type tariffChangeData = {
  name: string;
  duration: string;
};
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getDataCookies(key: string) {
  return Cookies.get(key);
}
export function setDataCookies(
  key: string,
  value: string | ResponseData | tariffChangeData
) {
  return Cookies.set(key, JSON.stringify(value));
}

export function removeToken() {
  Cookies.remove("accessToken");
  Cookies.remove("refreshToken");
}
// export function getDataLocalStorage(key: string) {
//   return localStorage.getItem(key);
// }

export const todayMoscowDate = () => {
  const options = {
    timeZone: "Europe/Moscow",
  };
  const now = new Date().toLocaleDateString("ru-RU", options);
  return now.split(".").reverse().join("-");
};

export const getYesterdayDate = () => {
  const currentDate = new Date();
  const moscowTimezoneOffset = currentDate.getTimezoneOffset() + 3 * 60; // UTC+3 for Moscow time
  const moscowCurrentTime = new Date(
    currentDate.getTime() + moscowTimezoneOffset * 60 * 1000
  );

  const yesterdayTime = moscowCurrentTime.getTime() - 24 * 60 * 60 * 1000;
  const yesterday = new Date(yesterdayTime);

  const formattedYesterday = yesterday.toISOString().split("T")[0];
  return formattedYesterday;
};

export const updateMoscowTime = () => {
  const options = { timeZone: "Europe/Moscow", hour12: false };
  const now = new Date();
  return now.toLocaleTimeString("ru-RU", options);
};

export const handleToggleFocus = (
  el: React.RefObject<HTMLDivElement>,
  booleanVar: boolean,
  func: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (el.current) {
    if (booleanVar) {
      el.current.blur();
    } else {
      el.current.focus();
    }
    func(!booleanVar);
  }
};
