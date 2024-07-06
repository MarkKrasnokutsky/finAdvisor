import { ResponseData } from "@/types/auth";
import { createContext, FC, ReactNode, useState } from "react";

export interface AuthContextType {
  authData: ResponseData | null;
  setAuthData: (data: ResponseData) => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export const AuthProvider: FC<{ children: ReactNode }> = ({ children }) => {
  const [authData, setAuthData] = useState<ResponseData | null>(null);

  return (
    <AuthContext.Provider value={{ authData, setAuthData }}>
      {children}
    </AuthContext.Provider>
  );
};
