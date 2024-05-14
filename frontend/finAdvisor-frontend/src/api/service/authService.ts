import { AxiosResponse } from "axios";

import { InputData, ResponseData, Token } from "@/types/auth";
import instance from "../config";

export const authService = {
  login: async (loginData: InputData): Promise<AxiosResponse<Token>> => {
    console.log(instance);
    const response = await instance.post<Token>(`/auth/signin`, {
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
    return await instance.get<ResponseData>(`/secured/user`);
  },

  // logout: () => {
  //   const response = instance.post<string>(`/auth/logout`);
  //   return response;
  // },
};
