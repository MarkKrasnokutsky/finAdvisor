import { TitleContext, TitleContextType } from "@/context/TitleContext";
import { useContext, useEffect } from "react";

export const useTitle = (title?: string): TitleContextType => {
  const context = useContext(TitleContext);
  if (!context) {
    throw new Error("useTitle должен использоваться внутри TitleProvider.");
  }
  useEffect(() => {
    if (title) {
      context.setTitle(title);
    }
  }, []);

  return context;
};
