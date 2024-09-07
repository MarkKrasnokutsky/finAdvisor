import { ForgotPasswordForm } from "@/components";

const ForgotPassword: React.FC = () => {
  return (
    <>
      <h2 className="text-4xl font-semibold mb-9 text-white text-center">
        Восстановление пароля
      </h2>
      <ForgotPasswordForm />
    </>
  );
};

export default ForgotPassword;
