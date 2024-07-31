import { Exit, Help, Home, Signal, Tariff, Tool } from "@/assets";
import { Nav, ToggleTheme } from "@/components";

const links = [
  {
    title: "Главная",
    path: "",
    icon: <Home />,
    default: true,
  },
  {
    title: "Сигналы",
    path: "/signals",
    icon: <Signal />,
  },
  {
    title: "Инструменты",
    path: "/tools",
    icon: <Tool />,
  },
  {
    title: "Тарифы",
    path: "/tariffs",
    icon: <Tariff />,
  },
  {
    title: "Помощь",
    path: "/help",
    icon: <Help />,
  },
  {
    title: "Выход",
    path: "/login",
    icon: <Exit />,
  },
];
type ActiveMenuProps = {
  onClick: () => void;
};

export const ActiveMenu: React.FC<ActiveMenuProps> = ({ onClick }) => {
  return (
    <div className="absolute top-0 left-0 h-screen w-full z-50 bg-background pt-12 pl-6 dark:bg-background-dark">
      <h4 className="text-primary font-semibold text-2xl mb-8 dark:text-primary-dark">
        Меню
      </h4>
      <Nav links={links} closeMenuHandler={onClick} />
      <ToggleTheme />
    </div>
  );
};
