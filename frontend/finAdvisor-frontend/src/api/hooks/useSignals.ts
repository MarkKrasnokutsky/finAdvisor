import {
  QueryKey,
  useQuery,
  UseQueryOptions,
  UseQueryResult,
} from "@tanstack/react-query";
import { signalsService } from "../service/signalsService";
import { Signal } from "@/types/signals";
import { useState } from "react";

export const useSignals = (): UseQueryResult<Signal[], Error> => {
  const [reloadAttempts, setReloadAttempts] = useState(0);
  const maxReloadAttempts = 3;

  const signalsQuery = useQuery<Signal[], Error>({
    queryKey: ["signals"] as QueryKey,
    queryFn: async (): Promise<Signal[]> => {
      const res = await signalsService.getSignals();
      return res.data;
    },
    onError: (error: Error) => {
      console.error("Ошибка при получении сигналов:", error);
      if (reloadAttempts < maxReloadAttempts) {
        setReloadAttempts((prev) => prev + 1);
        window.location.reload();
      }
    },
  } as UseQueryOptions<Signal[], Error>);

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
    const dateA = new Date(a.date.split("-").reverse().join("-"));
    const dateB = new Date(b.date.split("-").reverse().join("-"));

    return dateB.getTime() - dateA.getTime();
  });

  const filteredByDate = date
    ? signals.filter((signal) => signal.date === date)
    : sortSignals;

  const filteredByTool = tool
    ? filteredByDate.filter((signal) => signal.shortname === tool)
    : filteredByDate;

  const total = filteredByTool.sort((a, b) => {
    const dateA = new Date(a.date.split("-").reverse().join("-"));
    const dateB = new Date(b.date.split("-").reverse().join("-"));

    return dateB.getTime() - dateA.getTime();
  });

  // signals?.sort((a, b) => {
  //   const dateA = new Date(a.date.split("-").reverse().join("-"));
  //   const dateB = new Date(b.date.split("-").reverse().join("-"));

  //   return dateB.getTime() - dateA.getTime();
  // }
  // const total = filteredByTool.sort(
  //   (a, b) => new Date(b.date).getTime() - new Date(a.date).getTime()
  // );

  return total;
};
