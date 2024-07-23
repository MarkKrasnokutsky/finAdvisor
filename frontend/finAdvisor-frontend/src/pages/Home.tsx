import { useAuthContext } from "@/api/hooks/useAuth";
import { useTitle } from "@/api/hooks/useContext";
import { useSignals } from "@/api/hooks/useSignals";
import { useTools } from "@/api/hooks/useTools";
import { Spinner } from "@/assets";
import { Profile, SignalGrid, TariffItem, ToolsGrid } from "@/components";
import { CardLayout } from "@/layouts/CardLayout";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

const Home: React.FC = () => {
  useTitle("Панель управления");
  const { authData } = useAuthContext();

  const { data: tools } = useTools();
  const { data: signals } = useSignals();

  const onlyWidth = useWindowWidth();

  const isTariff = authData?.tariff ? true : false;

  const sortSignals =
    typeof signals !== "string"
      ? signals?.sort((a, b) => {
          return new Date(b.date).getTime() - new Date(a.date).getTime();
        })
      : [];

  const sortTools = typeof tools !== "string" ? tools : [];
  return (
    <div
      className={clsx("gap-5 w-full h-full", {
        "grid grid-cols-12 grid-rows-4": onlyWidth > 860,
        "flex flex-col scroll-container": onlyWidth < 860,
      })}
    >
      <CardLayout
        columns={8}
        // columns={onlyWidth > 1920 ? 10 : 8}
        rows={2}
        className="pb-0 pr-4"
      >
        {sortSignals ? (
          <SignalGrid signals={sortSignals} />
        ) : (
          <Spinner className="size-14 fill-primary dark:fill-primary-dark" />
        )}
      </CardLayout>
      <CardLayout
        columns={4}
        // columns={onlyWidth > 1920 ? 2 : 4}
        rows={2}
        className=""
      >
        {authData ? (
          <Profile username={authData.username} email={authData.email} />
        ) : (
          <Spinner className="size-14 fill-primary dark:fill-primary-dark" />
        )}
      </CardLayout>
      <CardLayout columns={5} rows={2} rowStart={3} rowEnd={4}>
        {authData ? (
          <TariffItem
            name={authData.tariff?.name || "Нет тарифа"}
            cost={authData.tariff?.cost || 0}
            tariffExpiration={authData.tariffExpiration || ""}
            instrumentCount={authData.tariff?.instrumentCount || 0}
            isTariff={isTariff}
          />
        ) : (
          <Spinner className="size-14 fill-primary dark:fill-primary-dark" />
        )}
      </CardLayout>
      <CardLayout columns={7} rows={2}>
        {sortTools ? (
          <ToolsGrid tools={sortTools} />
        ) : (
          <Spinner className="size-14 fill-primary dark:fill-primary-dark" />
        )}
      </CardLayout>

      {/* <div className={`col-span-8 row-span-2 bg-slate-400`}></div>
      <div className={`col-span-4 row-span-2 bg-slate-400`}></div>
      <div className="col-span-5 row-span-2 bg-slate-400"></div>
      <div className="col-span-7 row-span-2 bg-slate-400"></div> */}
    </div>
  );
};

export default Home;
