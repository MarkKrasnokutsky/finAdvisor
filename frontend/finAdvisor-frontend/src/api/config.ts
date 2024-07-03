// import { getDataLocalStorage } from "@/lib/utils";
import axios from "axios";

import { getDataLocalStorage } from "@/lib/utils";

const API_URL = import.meta.env.VITE_BASE_URL;

const instance = axios.create({
  baseURL: `${API_URL}`,
  withCredentials: true,
  headers: {
    Authorization: "Bearer " + getDataLocalStorage("accessToken"),
  },
});

export default instance;
