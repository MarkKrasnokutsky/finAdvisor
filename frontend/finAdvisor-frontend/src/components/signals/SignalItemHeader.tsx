import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

export const SignalItemHeader: React.FC = () => {
  const onlyWidth = useWindowWidth();

  return (
    <div
      className={clsx(
        `grid grid-cols-5 items-center py-4   text-secondary font-medium pr-14 overflow-x`,
        {
          "gap-x-6": onlyWidth > 1650,
          "gap-x-2": onlyWidth < 1650,
          "text-sm": onlyWidth < 1450,
          "w-[700px] text-xs": onlyWidth < 620,
        }
      )}
    >
      <div className="">Инструменты</div>
      <div
        className={clsx("", {
          "text-center": onlyWidth > 1650,
          "text-right": onlyWidth < 1650,
        })}
      >
        Дата сигналов
      </div>
      <div
        className={clsx("", {
          "text-center": onlyWidth > 1650,
          "text-right": onlyWidth < 1650,
        })}
      >
        {" "}
        Фиксация прибыли
      </div>
      <div className=" pl-[40%]">Точка входа</div>
      <div className="text-right pr-1">Стоп</div>
    </div>
  );
};
