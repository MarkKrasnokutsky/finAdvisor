import { Nav, ToggleTheme } from "@/components";
import { Logo, Help, Home, Signal, Tariff, Tool, Exit } from "@/assets";
import clsx from "clsx";
import { useWindowWidth } from "@react-hook/window-size";

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

export const Sidebar: React.FC = () => {
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx(
        "flex flex-col justify-between bg-secondaryBg pt-16 pb-10  dark:bg-secondaryBg-dark",
        {
          "px-10": onlyWidth > 1380,
          "px-4": onlyWidth < 1380,
        }
      )}
    >
      <div>
        <div className="ml-0 mb-14 flex items-center gap-4">
          <Logo
            className={clsx("", {
              "w-[74px] h-[64px]": onlyWidth > 1380,
              "w-[64px] h-[57px]": onlyWidth < 1380,
            })}
          />
          <h1 className="font-medium text-primary text-xl dark:text-logo">
            Profit Picks
          </h1>
        </div>
        <Nav links={links} />
      </div>
      <ToggleTheme />
      {/* <Nav links={links} /> */}
    </div>
  );
};
