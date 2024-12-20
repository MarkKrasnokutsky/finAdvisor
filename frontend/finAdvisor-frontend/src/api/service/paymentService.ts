import { AxiosResponse } from "axios";
import instance from "../config";
import { differenceDaysResponce, TariffChangeInput } from "@/types/tariff";
import {
  CreatePaymentRequest,
  CheckPaymentResponse,
  CreatePaymentResponse,
} from "@/types/payment";

export const paymentService = {
  tariffChange: async (
    input: TariffChangeInput
  ): Promise<AxiosResponse<string>> => {
    const response = await instance.put<string>(`/tariff/updateByUser`, {
      name: input.name,
      duration: input.duration,
    });
    return response;
  },
  createPayment: async (
    tariff: CreatePaymentRequest
  ): Promise<AxiosResponse<CreatePaymentResponse>> => {
    const response = await instance.post<CreatePaymentResponse>(
      `/payment/create`,
      {
        value: tariff.value,
        currency: "RUB",
        description: tariff.description,
        fullName: tariff.username,
        email: tariff.email,
        // description: `Покупка тарифа: ${tariff.description}`,
      }
    );
    return response;
  },
  differenceDays: async (
    input: TariffChangeInput
  ): Promise<AxiosResponse<differenceDaysResponce>> => {
    const response = await instance.post<differenceDaysResponce>(
      `/tariff/getDifferenceDays`,
      {
        name: input.name,
        duration: input.duration,
      }
    );
    return response;
  },
  checkPayment: async (
    id: string
  ): Promise<AxiosResponse<CheckPaymentResponse>> => {
    const response = await instance.get<CheckPaymentResponse>(
      `payment/check/${id}`
    );
    return response;
  },
};
