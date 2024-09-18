import { useFilterTool, useTitle } from "@/api/hooks/useContext";
import { useFilterSignals, useSignals } from "@/api/hooks/useSignals";
import { Spinner } from "@/assets";
import {
  ChooseTariff,
  Line,
  SignalHeader,
  SignalItem,
  SignalItemHeader,
} from "@/components";
import { CardLayout } from "@/layouts/CardLayout";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
import { useState } from "react";
import { format } from "date-fns";
import { useAuthContext } from "@/api/hooks/useAuth";
import { ResponseData } from "@/types/auth";

const SignalsList = (signals: Signal[] | undefined, authData: ResponseData) => {
  if (authData?.tariff === null) {
    return <ChooseTariff />;
  }
  if (!signals) {
    return <Spinner className="size-14 fill-primary dark:fill-primary-dark" />;
  }

  if (signals && signals.length === 0) {
    return (
      <div className="flex justify-center items-center size-full text-3xl font-semibold">
        {" "}
        Нет сигналов
      </div>
    );
  }

  return signals.map((signal, index) => (
    <div key={index}>
      <SignalItem isPages signal={signal} />
      {index !== signals.length - 1 && <Line />}
    </div>
  ));
};

const Signals: React.FC = () => {
  useTitle("Список сигналов");
  const { data: signals } = useSignals();
  const { authData } = useAuthContext();
  console.log("authData: ", authData);

  const { FilterToolData } = useFilterTool();

  const [dateSignals, setDateSignals] = useState<string>("");

  const filterSignals = useFilterSignals(signals!, dateSignals, FilterToolData);

  const onlyWidth = useWindowWidth();

  const getDateHandler = (date: Date | undefined) => {
    setDateSignals(date ? format(date, "dd-MM-yyyy") : "");
  };

  console.log("authData === null: ", authData?.tariff === null);

  return (
    <div className="flex flex-col h-full">
      <CardLayout className="pb-0 pr-8">
        <div className="flex flex-col gap-y-5 h-full">
          <SignalHeader getDateHandler={getDateHandler} />

          {onlyWidth > 750 && <SignalItemHeader />}
          <div
            className={`flex flex-col w-full h-full overflow-y-auto ${
              onlyWidth < 750 && "overflow-x-scroll"
            } scroll-container`}
          >
            {onlyWidth < 750 && authData?.tariff !== null && (
              <SignalItemHeader />
            )}
            {SignalsList(filterSignals && filterSignals, authData!)}
          </div>
        </div>
      </CardLayout>
    </div>
  );
};

export default Signals;
