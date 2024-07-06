import { useQuery } from "@tanstack/react-query";
import { signalsService } from "../service/signalsService";

export const useSignals = () => {
  const signalsQuery = useQuery({
    queryKey: ["signals"],
    queryFn: async () => {
      try {
        const res = await signalsService.getSignals();
        return res.data;
      } catch (error) {
        console.log(error);
      }
    },
  });

  return signalsQuery;
};
