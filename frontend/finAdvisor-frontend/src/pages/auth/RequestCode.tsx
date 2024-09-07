import { RequestCodeForm } from "@/components";

const RequestCode: React.FC = () => {
  return (
    <>
      <h2 className="text-4xl font-semibold mb-9 text-white text-center">
        Восстановление пароля
      </h2>
      <RequestCodeForm />
    </>
  );
};

export default RequestCode;
