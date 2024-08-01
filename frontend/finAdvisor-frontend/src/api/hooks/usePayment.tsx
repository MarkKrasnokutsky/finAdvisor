import { useMutation, useQuery } from "@tanstack/react-query";
import { paymentService } from "../service/paymentService";
import { AxiosResponse } from "axios";
import { getDataCookies, setDataCookies } from "@/lib/utils";
import { useEffect } from "react";
import { useAuthContext } from "./useAuth";
import { CreatePaymentResponse } from "@/types/payment";

export const useTariffChange = () => {
  const tariffChangeMutation = useMutation({
    mutationFn: paymentService.tariffChange,
    onSuccess: (res: AxiosResponse<string>) => {
      console.log("res: ", res);
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return tariffChangeMutation;
};
export const useCreatePayment = () => {
  const createPaymentMutation = useMutation({
    mutationFn: paymentService.createPayment,
    onSuccess: (res: AxiosResponse<CreatePaymentResponse>) => {
      setDataCookies("paymentId", res.data.id);
      window.location.href = res.data.confirmation.confirmation_url;
      console.log("res: ", res);
    },
    onError: (error: Error) => {
      console.log("ddd", error);
    },
  });

  return createPaymentMutation;
};

export const useCheckPayment = () => {
  const paymentId = getDataCookies("paymentId");
  const finPayId = paymentId ? JSON.parse(paymentId) : "";
  console.log("finPayId: ", finPayId);
  const checkPaymentMutation = useQuery({
    queryKey: ["payment"],
    queryFn: async () => {
      console.log("paymentId: ", paymentId);
      try {
        const { data } = await paymentService.checkPayment(finPayId);

        console.log("res: ", data);
        return data;
      } catch (error) {
        console.log(error);
      }
    },
  });

  return checkPaymentMutation;
};

export const usePayment = () => {
  const { data: payment } = useCheckPayment();

  const updateData = getDataCookies("updateData");
  const parseUpdateData = updateData ? JSON.parse(updateData) : "";

  const tariffChangeData = getDataCookies("tariffChangeData");
  const parseTariffChangeData = tariffChangeData
    ? JSON.parse(tariffChangeData)
    : "";

  const tariffChangeMutation = useTariffChange();
  const { setAuthData } = useAuthContext();
  useEffect(() => {
    if (payment?.status === "succeeded") {
      const handleTariffChange = async () => {
        try {
          await tariffChangeMutation.mutateAsync(parseTariffChangeData);
          await setAuthData(parseUpdateData);
        } catch (error) {
          console.log("error: ", error);
        }
      };

      handleTariffChange();
    }
  }, [payment]);

  return payment;
};
