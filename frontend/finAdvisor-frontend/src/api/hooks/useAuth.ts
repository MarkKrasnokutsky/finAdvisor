import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { authService } from "../service/authService";
import { AxiosResponse } from "axios";
import { ResponseData } from "@/types/auth";
import { useEffect } from "react";

export const useLogin = () => {
  const navigate = useNavigate();

  const loginMutation = useMutation({
    mutationFn: authService.login,
    onSuccess: () => {
      navigate("/dashboard");
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
      navigate("/dashboard/login");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return registerMutation;
};

export const useMe = () => {
  const meMutation = useMutation({
    mutationFn: authService.me,
    onSuccess: (res: AxiosResponse<ResponseData>) => {
      console.log("useMe", res.data);
    },
    onError: (error: Error) => {
      console.log(error);
      console.log("useMe Error", error);
    },
  });

  return meMutation;
};
export const useRefreshToken = () => {
  const refreshTokenMutation = useMutation({
    mutationFn: authService.refreshToken,
    onSuccess: (res: AxiosResponse<ResponseData>) => {
      console.log("RefreshToken", res.data);
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return refreshTokenMutation;
};

export const useAuth = (isProtectedRoute: boolean) => {
  const navigate = useNavigate();
  const meMutation = useMe();
  const refreshTokenMutation = useRefreshToken();

  useEffect(() => {
    const isAuth = async () => {
      try {
        await meMutation.mutateAsync();
        !isProtectedRoute && navigate("/dashboard");
      } catch (error) {
        try {
          await refreshTokenMutation.mutateAsync();
          !isProtectedRoute && navigate("/dashboard");
        } catch (error) {
          navigate("/dashboard/login");
        }
      }
    };

    isAuth();
  }, []);
};
// export const useMe = () => {
//   const meQuery = useQuery({
//     queryKey: ["me"],
//     queryFn: async () => {
//       try {
//         const res = await authService.me();
//         return res.data;
//       } catch (error) {
//         console.log(error);
//       }
//     },
//   });

//   return meQuery;
// };
