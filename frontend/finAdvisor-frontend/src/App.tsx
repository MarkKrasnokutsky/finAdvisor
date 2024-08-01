import { Outlet } from "react-router-dom";
import { useToggleTheme } from "@/api/hooks/useTheme";
const App = () => {
  // если дефолт тема белая то удалить useToggleTheme(); и в хуке изменить дефолт тему dark на light
  useToggleTheme();
  // usePayment();
  return (
    <>
      <Outlet />
    </>
  );
};

export default App;
