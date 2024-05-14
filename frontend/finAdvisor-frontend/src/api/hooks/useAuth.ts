import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { authService } from "../service/authService";
import { AxiosResponse } from "axios";
import { Token } from "@/types/auth";
import { setDataLocalStorage } from "@/lib/utils";

export const useLogin = () => {
  const navigate = useNavigate();

  const loginMutation = useMutation({
    mutationFn: authService.login,
    onSuccess: (res: AxiosResponse<Token>) => {
      setDataLocalStorage(res.data), navigate("/");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return loginMutation;
};
export const useRegistration = () => {
  const navigate = useNavigate();

  const registerMutation = useMutation({
    mutationFn: authService.register,
    onSuccess: (res: AxiosResponse<string>) => {
      console.log(res.data);
      navigate("/login");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return registerMutation;
};

export const useMe = () => {
  const meQuery = useQuery({
    queryKey: ["me"],
    queryFn: async () => {
      try {
        const res = await authService.me();
        return res.data;
      } catch (error) {
        console.log(error);
      }
    },
  });

  return meQuery;
};
