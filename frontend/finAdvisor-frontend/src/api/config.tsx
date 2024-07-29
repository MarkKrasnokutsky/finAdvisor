// import { getDataCookies } from "@/lib/utils";
import axios from "axios";

import { getDataCookies } from "@/lib/utils";

const API_URL = import.meta.env.VITE_BASE_URL;

export const instance = axios.create({
  baseURL: `${API_URL}`,
  withCredentials: true,
});

instance.interceptors.request.use(
  (config) => {
    const accessToken = getDataCookies("accessToken");
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);

export default instance;
