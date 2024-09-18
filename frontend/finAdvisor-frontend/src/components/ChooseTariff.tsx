import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { Link } from "react-router-dom";

export const ChooseTariff: React.FC = () => {
  const onlyWidth = useWindowWidth();
  return (
    <div className="flex flex-col items-center justify-center h-full gap-3">
      <h3
        className={clsx("", {
          "text-3xl": onlyWidth > 860,
          "text-2xl": onlyWidth < 860,
        })}
      >
        Выберите тариф
      </h3>
      <Link
        to={"/dashboard/tariffs"}
        className={clsx(
          " text-center border border-primary rounded-[20px] transition-all hover:bg-primary hover:text-secondaryBg dark:border-primary-dark dark:hover:bg-primary-dark dark:hover:text-secondaryBg-dark",
          {
            "p-5": onlyWidth > 860,
            "p-3 max-w-52": onlyWidth < 860,
          }
        )}
      >
        Перейти к тарифам
      </Link>
    </div>
  );
};
