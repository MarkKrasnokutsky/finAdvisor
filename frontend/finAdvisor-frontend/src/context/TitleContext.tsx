import { PropsWithChildren, createContext, useState } from "react";

export interface TitleContextType {
  title: string;
  setTitle: (title: string) => void;
}

export const TitleContext = createContext<TitleContextType | undefined>(
  undefined
);

export const TitleProvider: React.FC<PropsWithChildren> = ({ children }) => {
  const [title, setTitle] = useState("");
  return (
    <TitleContext.Provider value={{ title, setTitle }}>
      {children}
    </TitleContext.Provider>
  );
};
