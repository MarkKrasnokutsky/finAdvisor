import { AxiosResponse } from "axios";
import instance from "../config";
import { Tool } from "@/types/tools";

export const toolsService = {
  getTools: async (): Promise<AxiosResponse<Tool[]>> => {
    const response = await instance.get<Tool[]>(`/instruments/getAll`);
    return response;
  },
};
