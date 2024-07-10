import { createContext, FC, ReactNode, useState } from "react";

export interface FilterToolContextType {
  FilterToolData: string;
  setFilterToolData: (data: string) => void;
}

export const FilterToolContext = createContext<
  FilterToolContextType | undefined
>(undefined);

export const FilterToolProvider: FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [FilterToolData, setFilterToolData] = useState<string>("");
  return (
    <FilterToolContext.Provider value={{ FilterToolData, setFilterToolData }}>
      {children}
    </FilterToolContext.Provider>
  );
};
