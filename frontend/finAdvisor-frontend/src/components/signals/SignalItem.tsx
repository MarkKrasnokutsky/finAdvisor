import { ArrowGreen, ArrowRed } from "@/assets";
// import sberLogo from "@/assets/sber.png";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

type SignalItemProps = {
  signal: Signal;
};

export const SignalItem: React.FC<SignalItemProps> = ({ signal }) => {
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx("grid grid-cols-4 items-center py-4", {
        "gap-x-6": onlyWidth > 1650,
        "gap-x-2": onlyWidth < 1650,
        "w-[500px]": onlyWidth < 620,
      })}
    >
      <div
        className={clsx("flex items-center p-1 w-44", {
          "gap-x-6 ": onlyWidth > 1650,
          "gap-x-2 ": onlyWidth < 1650,
        })}
      >
        <div
          className={clsx("rounded-full bg-white", {
            "size-12": onlyWidth > 1920,
            "size-10": onlyWidth < 1920,
          })}
        ></div>
        {/* <img src={sberLogo} alt="logo" className="size-12" /> */}
        <div>
          <p
            className={clsx("font-semibold", {
              "text-sm": onlyWidth < 1650 && onlyWidth > 1270,
              "text-xs": onlyWidth < 1270,
            })}
          >
            {signal.shortname}
          </p>
          <span
            className={clsx("text-secondary font-medium", {
              "text-sm": onlyWidth < 1650 && onlyWidth > 1270,
              "text-xs": onlyWidth < 1270,
            })}
          >
            {signal.secid}
          </span>
        </div>
      </div>
      <div
        className={clsx("font-medium", {
          "text-lg text-center": onlyWidth > 1650,
          "text-base text-right": onlyWidth < 1650 && onlyWidth > 1270,
          "text-sm text-right": onlyWidth < 1270,
        })}
      >
        {signal.date}
      </div>
      <div className="flex items-center justify-center gap-x-1 text-arrow-green stroke-arrow-green dark:text-arrow-greenDark dark:stroke-arrow-greenDark col-span-1">
        <ArrowGreen />
        <span
          className={clsx("font-medium", {
            "text-lg": onlyWidth > 1650,
            "text-base": onlyWidth < 1650 && onlyWidth > 1270,
            "text-sm": onlyWidth < 1270,
          })}
        >
          {signal.open}
        </span>
      </div>
      <div className="flex items-center justify-end gap-x-1 text-arrow-red stroke-arrow-red dark:text-arrow-redDark dark:stroke-arrow-redDark">
        <ArrowRed />
        <span
          className={clsx("font-medium", {
            "text-lg": onlyWidth > 1650,
            "text-base": onlyWidth < 1650 && onlyWidth > 1270,
            "text-sm": onlyWidth < 1270,
          })}
        >
          32.2
        </span>
      </div>
    </div>
  );
};
