import { ArrowRightUp } from "@/assets";
import { ToolsItem } from "@/components";
import { Tool } from "@/types/tools";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { Link } from "react-router-dom";

type ToolsGridProps = {
  tools: Tool[];
};

export const ToolsGrid: React.FC<ToolsGridProps> = ({ tools }) => {
  const onlyWidth = useWindowWidth();

  const countTools = (): number => {
    // if (onlyWidth <= 1320) return 3;
    // if (onlyWidth <= 1650) return 4;
    if (onlyWidth <= 2100) return 5;
    else return 11;
  };

  const toolsPreview = tools.slice(0, countTools());
  console.log("toolsPreview: ", toolsPreview);
  return (
    <div
      className={clsx("flex flex-col gap-y-10", {
        "gap-y-10": onlyWidth > 1450,
        "gap-y-6": onlyWidth < 1650,
      })}
    >
      <h3 className="font-semibold text-[28px]">Мои инструменты</h3>
      <div className="flex flex-wrap gap-4">
        {toolsPreview
          ? toolsPreview.map((tool, index) => (
              <ToolsItem key={index} tool={tool} />
            ))
          : ""}

        <Link
          to="tools"
          className={clsx(
            "flex flex-col items-center p-4 border max-w-32 w-full border-secondary rounded-[20px] dark:border-profile-dark",
            {
              "max-w-28 text-xs p-4": onlyWidth > 1900,
              "max-w-24 text-xs p-4": onlyWidth < 1900 && onlyWidth > 1720,
              "max-w-24 text-[9px] p-2": onlyWidth < 1720 && onlyWidth > 1450,
              "max-w-[90px] text-[10px] p-2": onlyWidth < 1450,
              //   "max-w-20 text-[8px] p-1": onlyWidth < 1350,
            }
          )}
        >
          <h4 className="font-medium text-center mb-2">
            Смотерть <br /> все
          </h4>
          <ArrowRightUp
            className={clsx("text-primary dark:text-primary-dark", {
              "size-10": onlyWidth > 1720,
              "size-8": onlyWidth < 1720 && onlyWidth > 1450,
              "size-7": onlyWidth < 1450,
            })}
          />
        </Link>
      </div>
    </div>
  );
};
