import LoginForm from "@/components/form/LoginForm";

const Login: React.FC = () => {
  return (
    <>
      <h2 className="text-4xl font-semibold mb-9 text-white text-center">
        Авторизация
      </h2>
      <LoginForm />
    </>
  );
};

export default Login;
