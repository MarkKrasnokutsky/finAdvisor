import { useAuth } from "@/api/hooks/useAuth";

export const PublicRoute: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  useAuth(false);
  return <>{children}</>;
};
