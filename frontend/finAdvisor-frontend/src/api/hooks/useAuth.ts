import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { authService } from "../service/authService";
import { useContext, useEffect } from "react";
import { AuthContext, AuthContextType } from "@/context/AuthContext";
import { useCheckPayment, useTariffChange } from "./usePayment";
import { getDataCookies, setDataCookies } from "@/lib/utils";

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
    onSuccess: () => {
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
    onSuccess: () => {
      console.log("Данные получены");
    },
  });

  return meMutation;
};
export const useRefreshToken = () => {
  const refreshTokenMutation = useMutation({
    mutationFn: authService.refreshToken,
    onSuccess: () => {
      console.log("Данные получены");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return refreshTokenMutation;
};

export const useAuth = (isProtectedRoute: boolean) => {
  const { data: payment } = useCheckPayment();

  const tariffChangeData = getDataCookies("tariffChangeData");
  const parseTariffChangeData = tariffChangeData
    ? JSON.parse(tariffChangeData)
    : "";

  const tariffChangeMutation = useTariffChange();

  const navigate = useNavigate();
  const meMutation = useMe();
  const refreshTokenMutation = useRefreshToken();
  const { setAuthData } = useAuthContext();
  useEffect(() => {
    const isAuth = async () => {
      try {
        await tariffChangeMutation.mutateAsync(
          payment?.status === "succeeded" ? parseTariffChangeData : {}
        );
        payment?.status === "succeeded" &&
          (await setDataCookies("tariffChangeData", ""),
          await setDataCookies("paymentId", "")),
          await setDataCookies("updateData", "");
        const result = await meMutation.mutateAsync();
        !isProtectedRoute && navigate("/dashboard");
        await setAuthData(result.data);
      } catch (error) {
        try {
          await tariffChangeMutation.mutateAsync(
            payment?.status === "succeeded" ? parseTariffChangeData : {}
          );

          const result = await refreshTokenMutation.mutateAsync();
          await setAuthData(result.data);
          !isProtectedRoute && navigate("/dashboard");
        } catch (error) {
          navigate("/dashboard/login");
        }
      }
    };
    // };

    isAuth();
  }, [payment]);
  // useEffect(() => {
  //   const isAuth = async () => {
  //     // try {
  //     //   await tariffChangeMutation.mutateAsync(parseTariffChangeData);
  //     //   const result = await meMutation.mutateAsync();
  //     //   // console.log("result: ", result);
  //     //   !isProtectedRoute && navigate("/dashboard");
  //     //   await setAuthData(result.data);
  //     // } catch (error) {
  //     try {
  //       // мб провкерку на isProtectedRoute
  //       // if ((await payment?.status) === "succeeded") {
  //       // }
  //       // (await payment?.status) === "succeeded" &&
  //       //   (await tariffChangeMutation.mutateAsync(parseTariffChangeData));
  //       await tariffChangeMutation.mutateAsync(parseTariffChangeData);
  //       const result = await meMutation.mutateAsync();
  //       // console.log("result: ", result);
  //       !isProtectedRoute && navigate("/dashboard");
  //       await setAuthData(result.data);
  //     } catch (error) {
  //       try {
  //         (await payment?.status) === "succeeded" &&
  //           (await tariffChangeMutation.mutateAsync(parseTariffChangeData));

  //         const result = await refreshTokenMutation.mutateAsync();
  //         await setAuthData(result.data);
  //         !isProtectedRoute && navigate("/dashboard");
  //       } catch (error) {
  //         navigate("/dashboard/login");
  //       }
  //     }
  //   };
  //   // };

  //   isAuth();
  // }, [payment]);
};

export const useAuthContext = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuthContext must be used within an AuthProvider");
  }
  return context;
};

export const useSendCode = () => {
  const navigate = useNavigate();

  const sendCodeMutation = useMutation({
    mutationFn: authService.sendCode,
    onSuccess: () => {
      navigate("/dashboard/forgot-password");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return sendCodeMutation;
};
export const useResetPassword = () => {
  const navigate = useNavigate();

  const resetPasswordMutation = useMutation({
    mutationFn: authService.resetPassword,
    onSuccess: () => {
      navigate("/dashboard/login");
    },
    onError: (error: Error) => {
      console.log(error);
    },
  });

  return resetPasswordMutation;
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
