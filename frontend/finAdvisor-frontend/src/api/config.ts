// import { getDataCookies } from "@/lib/utils";
import axios from "axios";

import { getDataCookies } from "@/lib/utils";

const API_URL = import.meta.env.VITE_BASE_URL;

const instance = axios.create({
  baseURL: `${API_URL}`,
  withCredentials: true,
  headers: {
    Authorization: "Bearer " + getDataCookies("accessToken"),
  },
});

export default instance;
