import { useQuery } from "@tanstack/react-query";
import { signalsService } from "../service/signalsService";
import { Signal } from "@/types/signals";

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

export const useFilterSignals = (
  signals: Signal[],
  date: string,
  tool: string
) => {
  if (!signals) {
    return undefined;
  }
  const sortSignals = signals?.sort((a, b) => {
    return new Date(b.date).getTime() - new Date(a.date).getTime();
  });

  const filteredByDate = date
    ? signals.filter((signal) => signal.date === date)
    : sortSignals;

  const filteredByTool = tool
    ? filteredByDate.filter((signal) => signal.shortname === tool)
    : filteredByDate;

  const total = filteredByTool.sort(
    (a, b) => new Date(b.date).getTime() - new Date(a.date).getTime()
  );

  return total;
};
