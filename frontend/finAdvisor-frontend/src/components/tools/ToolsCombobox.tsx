import { useClickOutside } from "@/api/hooks/useClickOutside";
import { ArrowUpDown } from "@/assets";
import { Tool } from "@/types/tools";
// import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { useRef, useState } from "react";
import { Input } from "@/components";
import { ToolsComboboxItem } from "./ToolsComboboxItem";
import { useFilterTool } from "@/api/hooks/useContext";

type ToolsComboboxProps = {
  tools: Tool[];
  setIsFocusedTools: React.Dispatch<React.SetStateAction<boolean>>;
};

export const ToolsCombobox: React.FC<ToolsComboboxProps> = ({
  tools,
  setIsFocusedTools,
}) => {
  const blockRef = useRef<HTMLDivElement>(null);

  useClickOutside(blockRef, setIsFocusedTools);

  const [value, setValue] = useState("");

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const { FilterToolData } = useFilterTool();

  const filterAndFoundTools = (tools: Tool[], value: string) => {
    const foundTools = tools.filter(
      (tool) =>
        tool.shortname.toLowerCase().includes(value.toLowerCase()) ||
        tool.secid.toLowerCase().includes(value.toLowerCase())
    );

    return foundTools.sort((a, b) => {
      if (a.shortname === FilterToolData) return -1;
      if (b.shortname === FilterToolData) return 1;
      return 0;
    });
  };

  return (
    <div
      className={clsx(
        "absolute right-0 z-40 max-h-[424px] shadow-custom flex flex-col gap-x-3 cursor-pointer p-3 transition-all border border-profile-dark rounded-[24px]  bg-secondaryBg  dark:border-primary-dark dark:bg-primary dark:shadow-customDarkTools"
      )}
    >
      <div className="flex gap-x-3 ">
        <p>Инструменты</p>
        <div
          tabIndex={0}
          className="flex flex-col items-center justify-center p-2 transition-all bg-primary rounded-full rotate-180 dark:bg-primary-dark"
        >
          <ArrowUpDown className="text-secondaryBg dark:text-secondaryBg-dark" />
        </div>
      </div>

      <div className="" ref={blockRef}>
        <Input
          type="text"
          isSearch={value ? false : true}
          className="max-w-[155px] w-full mt-7 mb-4 p-0 pl-7 h-6 rounded-[8px] text-xs text-search-text bg-search dark:bg-search-dark border-none focus-visible:ring-0 focus-visible:ring-offset-0 placeholder:font-medium placeholder:text-xs placeholder:text-search-text "
          placeholder="Поиск..."
          onChange={handleChange}
        />
        <div className="flex flex-col w-full max-h-[315px] overflow-y-auto scroll-container-modal">
          {filterAndFoundTools(tools, value) ? (
            <div className="flex flex-col gap-y-2">
              {filterAndFoundTools(tools, value).map((tool, index) => (
                <ToolsComboboxItem key={index} tool={tool} />
              ))}
            </div>
          ) : (
            ""
          )}
        </div>
      </div>
    </div>
  );
};
