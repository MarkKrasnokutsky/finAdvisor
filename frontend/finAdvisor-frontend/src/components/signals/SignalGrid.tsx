import { ArrowRightUp } from "@/assets";
import React from "react";
import { Link } from "react-router-dom";
import { ChooseTariff, Line, SignalItem } from "@/components";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
import { useAuthContext } from "@/api/hooks/useAuth";
type SignalGridPtops = {
  signals: Signal[];
};

export const SignalGrid: React.FC<SignalGridPtops> = ({ signals }) => {
  const Signals = signals.map((signal, index) => (
    <div key={index}>
      <SignalItem signal={signal} />
      {index !== signals.length - 1 && <Line />}
    </div>
  ));
  const onlyWidth = useWindowWidth();

  const { authData } = useAuthContext();

  return (
    <div className="flex flex-col gap-4 h-full">
      <div className="flex justify-between">
        <h3 className="font-semibold text-[28px]">Список сигналов</h3>
        <Link to="signals">
          <ArrowRightUp className="text-primary transition-all dark:text-primary-dark hover:-translate-y-0.5" />
        </Link>
      </div>
      <div
        className={`w-full h-full overflow-y-auto ${
          onlyWidth < 620 && authData?.tariff !== null && "overflow-x-scroll"
        } scroll-container`}
      >
        {authData?.tariff === null ? (
          <ChooseTariff />
        ) : Signals.length > 0 ? (
          Signals
        ) : (
          <div className="flex justify-center items-center size-full text-3xl font-semibold">
            {" "}
            Нет сигналов
          </div>
        )}
      </div>
    </div>
  );
};
