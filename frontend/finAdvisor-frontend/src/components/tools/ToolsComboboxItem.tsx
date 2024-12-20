import { useFilterTool } from "@/api/hooks/useContext";
import { DeleteItems } from "@/assets";
import { Tool } from "@/types/tools";

type ToolsComboboxItemProps = {
  tool: Tool;
};

export const ToolsComboboxItem: React.FC<ToolsComboboxItemProps> = ({
  tool,
}) => {
  const { FilterToolData, setFilterToolData } = useFilterTool();

  const isSelected = FilterToolData === (tool.shortname || tool.secid);

  return (
    <div
      className={`flex gap-x-4 w-full items-center justify-between px-1 text-[10px] ${
        isSelected && "bg-search dark:bg-search-dark rounded-[5px]"
      }`}
    >
      <div
        onClick={() => setFilterToolData(tool.shortname)}
        className="flex items-center gap-x-2"
      >
        {/* <div className="bg-white size-6 rounded-full"></div> */}
        <img
          src={`https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/instrument_icons/${tool.secid}.svg`}
          alt=""
          className="size-6"
        />
        <div className="font-semibold ">{tool.shortname}</div>
      </div>

      {isSelected ? (
        <DeleteItems onClick={() => setFilterToolData("")} />
      ) : (
        <div className="font-medium text-secondary">{tool.secid}</div>
      )}
    </div>
  );
};
