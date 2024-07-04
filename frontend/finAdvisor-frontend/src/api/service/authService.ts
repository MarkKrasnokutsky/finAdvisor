import { AxiosResponse } from "axios";

import { InputData, ResponseData } from "@/types/auth";
import instance from "../config";

export const authService = {
  login: async (loginData: InputData): Promise<AxiosResponse<string>> => {
    const response = await instance.post<string>(`/auth/signin`, {
      username: loginData.username,
      password: loginData.password,
    });
    return response;
  },

  register: async (loginData: InputData): Promise<AxiosResponse<string>> => {
    const response = await instance.post<string>(`/auth/signup`, {
      username: loginData.username,
      email: loginData.email,
      password: loginData.password,
    });
    return response;
  },
  me: async (): Promise<AxiosResponse<ResponseData>> => {
    const response = await instance.post<ResponseData>(`/secured/user`);
    return response;
  },
  refreshToken: async (): Promise<AxiosResponse<ResponseData>> => {
    return await instance.post<ResponseData>(`/auth/refresh_token`);
  },

  // logout: () => {
  //   const response = instance.post<string>(`/auth/logout`);
  //   return response;
  // },
};
