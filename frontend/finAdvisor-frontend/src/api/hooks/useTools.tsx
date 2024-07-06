import { useQuery } from "@tanstack/react-query";
import { toolsService } from "../service/toolsService";

export const useTools = () => {
  const toolsQuery = useQuery({
    queryKey: ["tools"],
    queryFn: async () => {
      try {
        const res = await toolsService.getTools();
        return res.data;
      } catch (error) {
        console.log(error);
      }
    },
  });

  return toolsQuery;
};
