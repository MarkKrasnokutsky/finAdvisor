import { Link } from "react-router-dom";
import simple from "@/assets/tariffs/simple.png";
import plus from "@/assets/tariffs/plus.png";
import pro from "@/assets/tariffs/pro.png";
import vip from "@/assets/tariffs/vip.png";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { useDifferenceDays } from "@/api/hooks/usePayment";

const tariffs = [
  { name: "Simple", img: simple, cost: 2490 },
  { name: "Plus", img: plus, cost: 3490 },
  { name: "Pro", img: pro, cost: 4990 },
  { name: "VIP", img: vip, cost: 7490 },
];

type TariffItemProps = {
  name?: string;
  cost?: number;
  tariffExpiration?: string;
  instrumentCount?: number;
  isPage?: boolean;
  isTariff?: boolean;
};

export const TariffItem: React.FC<TariffItemProps> = ({
  name,
  tariffExpiration,
  instrumentCount,
  isPage,
  isTariff,
}) => {
  const date = tariffExpiration?.split("T")[0];

  const selectTariff = tariffs.find(
    (a) => a.name.toLocaleLowerCase() === name?.toLocaleLowerCase()
  );

  const differenceDaysMutatiton = useDifferenceDays();

  const differenceDaysData = {
    name: name || "",
    duration: "30",
  };

  // RENAME!!!
  const onSubmit = async () => {
    try {
      await differenceDaysMutatiton.mutateAsync(differenceDaysData);
    } catch (error) {
      console.log("error: ", error);
    }
  };

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
        {isTariff ? (
          <>
            <p>{instrumentCount} доступных инструментов</p>
            <p>
              Действует до <span className="font-semibold">{date}</span>
            </p>
          </>
        ) : (
          <p>
            Подберите подходящий тарифный план и начните пользоваться всеми
            возможностями Profit Picks
          </p>
        )}
      </div>
      <div className="flex flex-col gap-y-4 font-medium">
        {isTariff && (
          <p>
            <span
              className={clsx(" font-semibold", {
                "text-3xl": onlyWidth > 1930,
                "text-2xl": onlyWidth < 1930 && onlyWidth > 1450,
                "text-xl": onlyWidth < 1450,
              })}
            >
              {selectTariff?.cost}₽
            </span>{" "}
            в месяц
          </p>
        )}
        <Link
          to={isPage ? "" : "tariffs"}
          className={clsx(
            " text-center border border-primary rounded-[20px] transition-all hover:bg-primary hover:text-secondaryBg dark:border-primary-dark dark:hover:bg-primary-dark dark:hover:text-secondaryBg-dark",
            {
              "py-5": onlyWidth > 860,
              "py-3 max-w-52": onlyWidth < 860,
            }
          )}
          onClick={onSubmit}
        >
          {isPage ? "Продлить" : "Перейти к тарифам"}
        </Link>
      </div>
      <div className="absolute -right-5 -top-7">
        <img
          src={`https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/tariffs/${selectTariff?.name.toLocaleLowerCase()}.png`}
          alt=""
          className={clsx({
            "w-64": onlyWidth > 1650,
            "w-44": onlyWidth < 1650 && onlyWidth > 1300,
            "w-36": onlyWidth < 1300,
          })}
        />
      </div>
    </div>
  );
};
