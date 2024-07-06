import { Link } from "react-router-dom";
import simple from "@/assets/tariffs/simple.png";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

type TariffItemProps = {
  name: string;
  cost: number;
  tariffExpiration: string;
  allTools: number;
};

export const TariffItem: React.FC<TariffItemProps> = ({
  name,
  cost,
  tariffExpiration,
  allTools,
}) => {
  const date = tariffExpiration.split("T")[0];

  const onlyWidth = useWindowWidth();
  return (
    <div className="flex flex-col justify-between h-full relative">
      <div className="">
        <span className="font-medium text-secondary">Мой тариф</span>
        <h3 className="font-semibold text-[28px]">{name}</h3>
      </div>
      <div
        className={clsx("flex flex-col  font-medium ", {
          "text-lg gap-y-6": onlyWidth > 1930,
          "gap-y-2 text-sm": onlyWidth < 1650 && onlyWidth > 1450,
          "text-sm": onlyWidth < 1450,
        })}
      >
        <p>{allTools} доступных инструментов</p>
        <p>
          Действует до <span className="font-semibold">{date}</span>
        </p>
      </div>
      <div className="flex flex-col gap-y-4 font-medium">
        <p>
          <span
            className={clsx(" font-semibold", {
              "text-3xl": onlyWidth > 1930,
              "text-2xl": onlyWidth < 1930 && onlyWidth > 1450,
              "text-xl": onlyWidth < 1450,
            })}
          >
            2400{cost}₽
          </span>{" "}
          в месяц
        </p>
        <Link
          to="tariffs"
          className={clsx(
            " text-center border border-primary rounded-[20px] transition-all hover:bg-primary hover:text-secondaryBg dark:border-primary-dark dark:hover:bg-primary-dark dark:hover:text-secondaryBg-dark",
            {
              "py-5": onlyWidth > 860,
              "py-3 max-w-52": onlyWidth < 860,
            }
          )}
        >
          Перейти к тарифам
        </Link>
      </div>
      <div className="absolute -right-5 -top-7">
        <img
          src={simple}
          alt=""
          className={clsx({
            "w-72": onlyWidth > 1650,
            "w-44": onlyWidth < 1650 && onlyWidth > 1300,
            "w-36": onlyWidth < 1300,
          })}
        />
      </div>
    </div>
  );
};
