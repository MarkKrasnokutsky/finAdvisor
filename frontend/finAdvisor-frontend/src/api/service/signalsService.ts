import { AxiosResponse } from "axios";
import instance from "../config";
import { Signal } from "@/types/signals";

export const signalsService = {
  getSignals: async (): Promise<AxiosResponse<Signal[]>> => {
    const response = await instance.get<Signal[]>(`/signals/getByUserTariff`);
    return response;
  },
};
