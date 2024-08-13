import { useAuth } from "@/api/hooks/useAuth";
// import { usePayment } from "@/api/hooks/usePayment";

export const PrivateRoute: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  // usePayment();
  useAuth(true);

  return <>{children}</>;
};
