import { useAuthContext } from "@/api/hooks/useAuth";
import { useTitle } from "@/api/hooks/useContext";
import { useTools } from "@/api/hooks/useTools";
import { ToolsItem } from "@/components";
import { CardLayout } from "@/layouts/CardLayout";
import { useWindowWidth } from "@react-hook/window-size";

const Tools: React.FC = () => {
  useTitle("Список инструментов");
  const onlyWidth = useWindowWidth();
  const { authData } = useAuthContext();
  const { data: tools } = useTools();
  return (
    <div className="flex flex-col h-full">
      <CardLayout className="pb-0 pr-8">
        <div className="flex flex-col gap-y-[45px] h-full">
          <h3 className="font-semibold text-[28px]">
            Всего доступно:{" "}
            <span className="text-xl pl-4 text-telegram dark:text-telegram-dark">
              {authData?.tariff.instrumentCount} инструментов
            </span>
          </h3>
          <div className="flex flex-wrap gap-x-5 gap-y-4 justify-start items-start overflow-y-auto scroll-container">
            {tools &&
              tools.map((tool, index) => <ToolsItem key={index} tool={tool} />)}
          </div>
        </div>
      </CardLayout>
    </div>
  );
};

export default Tools;
