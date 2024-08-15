import { useAuth } from "@/api/hooks/useAuth";

export const PrivateRoute: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  useAuth(true);

  return <>{children}</>;
};
