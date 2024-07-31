import { ArrowGreen, ArrowRed } from "@/assets";
// import sberLogo from "@/assets/sber.png";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { useEffect, useState } from "react";

type SignalItemProps = {
  signal: Signal;
  isPages?: boolean;
};

export const SignalItem: React.FC<SignalItemProps> = ({ signal, isPages }) => {
  const [svgContent, setSvgContent] = useState("");
  // console.log("svgContent: ", svgContent);

  useEffect(() => {
    const fetchSVG = async () => {
      const response = await fetch(
        `https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/instrument_icons/${signal.secid}.svg`,
        {
          method: "GET",
          mode: "no-cors",
        }
      );

      if (response.ok) {
        const svg = await response.text();
        setSvgContent(svg);
      } else {
        console.error(
          "Ошибка при загрузке SVG:",
          response.status,
          response.statusText
        );
      }
    };

    // fetchSVG();
  }, [signal.secid]);

  const fetchSVG = async () => {
    try {
      const response = await fetch(
        `https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/instrument_icons/${signal.secid}.svg`,
        {
          method: "GET",
          mode: "no-cors",
        }
      );

      console.log("Response status:", response.status);
      console.log("Response headers:", response.headers.get("content-type"));

      if (response.ok) {
        const svg = await response.text();
        setSvgContent(svg);
      } else {
        console.error(
          "Ошибка при загрузке SVG:",
          response.status,
          response.statusText
        );
      }
    } catch (error) {
      console.error("Ошибка при выполнении fetch:", error);
    }
  };
  // fetchSVG();
  const open = signal.open.toFixed(2);
  // const open = Math.floor(signal.open * 100) / 100;
  // const open = signal.open.toString().replace(/(\d{2})\d*/, "$1");
  // const open = signal.open;
  // const stop = 32.2;
  const stop = signal.stop.toFixed(2);
  const profitFix = signal.profitFix.toFixed(2);
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx(
        `grid grid-cols-${
          isPages ? 5 : 4
        } grid-flow-col items-center py-4 pr-10`,
        {
          "gap-x-6": onlyWidth > 1650,
          "gap-x-2": onlyWidth < 1650,
          "w-[700px]": onlyWidth < 620,
        }
      )}
    >
      <div
        className={clsx("flex items-center p-1 w-44", {
          "gap-x-6 ": onlyWidth > 1650,
          "gap-x-2 ": onlyWidth < 1650,
        })}
      >
        <div
          dangerouslySetInnerHTML={{ __html: svgContent }}
          className="size-4"
        />
        <img
          src={`https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/instrument_icons/${signal.secid}.svg`}
          alt="logo"
          className={clsx({
            "size-12": onlyWidth > 1920,
            "size-10": onlyWidth < 1920,
          })}
        />
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
      <div className="flex items-center justify-left pl-[20%] gap-x-1 text-arrow-green stroke-arrow-green dark:text-arrow-greenDark dark:stroke-arrow-greenDark col-span-1">
        <ArrowGreen />
        <span
          className={clsx("font-medium", {
            "text-lg": onlyWidth > 1650,
            "text-base": onlyWidth < 1650 && onlyWidth > 1270,
            "text-sm": onlyWidth < 1270,
          })}
        >
          {profitFix}
        </span>
      </div>
      {isPages && (
        <div
          className={clsx("font-medium", {
            "text-lg text-center": onlyWidth > 1650,
            "text-base text-right": onlyWidth < 1650 && onlyWidth > 1270,
            "text-sm text-right": onlyWidth < 1270,
          })}
        >
          {open}
        </div>
      )}
      <div className="flex items-center justify-left pl-[70%] gap-x-1 text-arrow-red stroke-arrow-red dark:text-arrow-redDark dark:stroke-arrow-redDark">
        <ArrowRed />
        <span
          className={clsx("font-medium", {
            "text-lg": onlyWidth > 1650,
            "text-base": onlyWidth < 1650 && onlyWidth > 1270,
            "text-sm": onlyWidth < 1270,
          })}
        >
          {stop}
        </span>
      </div>
    </div>
  );
};
