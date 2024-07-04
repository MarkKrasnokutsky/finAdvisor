// import sberLogo from "@/assets/sber.png";
import { Tool } from "@/types/tools";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
type ToolsItemProps = {
  tool: Tool;
};
export const ToolsItem: React.FC<ToolsItemProps> = ({ tool }) => {
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx(
        "flex flex-col items-center  border w-full border-secondary rounded-[20px] dark:border-profile-dark",
        {
          "max-w-28 text-xs p-4": onlyWidth > 1900,
          "max-w-24 text-[10px] p-4": onlyWidth < 1900 && onlyWidth > 1720,
          "max-w-24 text-[9px] p-2": onlyWidth < 1720 && onlyWidth > 1450,
          "max-w-[90px] text-[10px] p-2": onlyWidth < 1450,
          //   "max-w-20 text-[8px] p-1": onlyWidth < 1350,
          //   "max-w-20 text-[7px]": onlyWidth < 1280,
        }
      )}
    >
      <div
        className={clsx(" mb-2 bg-white rounded-full", {
          "size-10": onlyWidth > 1720,
          "size-8": onlyWidth < 1720 && onlyWidth > 1450,
          "size-7": onlyWidth < 1450,
        })}
      ></div>
      {/* <img src={sberLogo} alt="" className="size-10 mb-2" /> */}
      <h4 className="font-semibold ">{tool.shortname}</h4>
      <span className=" font-medium text-secondary">{tool.secid}</span>
    </div>
  );
};
