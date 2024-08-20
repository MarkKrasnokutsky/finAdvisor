import { useAuthContext } from "@/api/hooks/useAuth";
import { useTitle } from "@/api/hooks/useContext";
import { useTools } from "@/api/hooks/useTools";
import { Spinner } from "@/assets";
import { ToolsItem } from "@/components";
import { CardLayout } from "@/layouts/CardLayout";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
// import { useWindowWidth } from "@react-hook/window-size";

const Tools: React.FC = () => {
  useTitle("Список инструментов");
  //   const onlyWidth = useWindowWidth();
  const { authData } = useAuthContext();
  const { data: tools } = useTools();

  const isCheck = authData?.tariff ? authData.tariff.instrumentCount : 0;
  const instrumentCount = isCheck;

  const onlyWidth = useWindowWidth();

  return (
    <div className="flex flex-col h-full">
      <CardLayout className={`pb-2 ${onlyWidth > 600 ? "pr-8" : "pr-4 pl-4"} `}>
        <div className="flex flex-col gap-y-[45px] h-full">
          <h3 className="font-semibold text-[28px]">
            Всего доступно:{" "}
            <span className="text-xl pl-4 text-telegram dark:text-telegram-dark">
              {instrumentCount} инструментов
            </span>
          </h3>
          {/* justify-center */}
          <div
            className={clsx(
              "flex flex-wrap gap-x-5  w-full  gap-y-4 items-start overflow-y-auto scroll-container",
              {
                "justify-center h-full": !tools,
              }
            )}
          >
            {tools ? (
              tools.map((tool, index) => <ToolsItem key={index} tool={tool} />)
            ) : (
              <Spinner className=" w-full  m-auto size-14 fill-primary dark:fill-primary-dark" />
            )}
          </div>
        </div>
      </CardLayout>
    </div>
  );
};

export default Tools;
