import { useFilterTool, useTitle } from "@/api/hooks/useContext";
import { useFilterSignals, useSignals } from "@/api/hooks/useSignals";
import { Spinner } from "@/assets";
import { Line, SignalHeader, SignalItem, SignalItemHeader } from "@/components";
import { CardLayout } from "@/layouts/CardLayout";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
import { useState } from "react";

const SignalsList = (signals: Signal[] | undefined) => {
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

  const { FilterToolData } = useFilterTool();

  const [dateSignals, setDateSignals] = useState("");
  console.log("dateSignals: ", dateSignals);

  const filterSignals = useFilterSignals(signals!, dateSignals, FilterToolData);

  const onlyWidth = useWindowWidth();

  const getDateHandler = (date: string) => {
    setDateSignals(date);
  };

  return (
    <div className="flex flex-col h-full">
      <CardLayout className="pb-0 pr-8">
        <div className="flex flex-col gap-y-5 h-full">
          <SignalHeader getDateHandler={getDateHandler} />

          {onlyWidth > 620 && <SignalItemHeader />}
          <div
            className={`flex flex-col w-full h-full overflow-y-auto ${
              onlyWidth < 620 && "overflow-x-scroll"
            } scroll-container`}
          >
            {onlyWidth < 620 && <SignalItemHeader />}
            {SignalsList(filterSignals && filterSignals)}
          </div>
        </div>
      </CardLayout>
    </div>
  );
};

export default Signals;
