import { getDataLocalStorage } from "@/lib/utils";
import axios from "axios";

const API_URL = import.meta.env.VITE_BASE_URL;

console.log(import.meta.env.VITE_BASE_URL);

const instance = axios.create({
  baseURL: `${API_URL}`,
  headers: {
    Authorization: "Bearer " + getDataLocalStorage("token"),
  },
});

export default instance;
