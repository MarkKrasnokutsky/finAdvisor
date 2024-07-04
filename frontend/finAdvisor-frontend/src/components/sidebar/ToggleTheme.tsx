import { useToggleTheme } from "@/api/hooks/useTheme";
import { Sun, Moon } from "@/assets";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

export const ToggleTheme: React.FC = () => {
  const { theme, toggleTheme } = useToggleTheme();
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx("flex items-center gap-x-4 font-medium text-xs ", {
        "px-14": onlyWidth > 1720,
      })}
    >
      <p className="text-primary dark:text-secondary">Светлая</p>
      <div
        className="flex items-center gap-x-3 border border-secondary rounded-full p-1 dark:border-secondary-dark"
        onClick={toggleTheme}
      >
        <Sun />
        <div
          className={clsx(
            "absolute size-8 rounded-full transition-all duration-700",
            {
              "bg-white translate-x-9": theme === "dark",
              "bg-black translate-x-0": theme === "light",
            }
          )}
        ></div>
        <Moon />
      </div>
      <p className="text-secondary dark:text-nav-focusDark">Темная</p>
    </div>
  );
};
