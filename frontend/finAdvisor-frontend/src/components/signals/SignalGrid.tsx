import { ArrowRightUp } from "@/assets";
import React from "react";
import { Link } from "react-router-dom";
import { Line, SignalItem } from "@/components";
import { Signal } from "@/types/signals";
import { useWindowWidth } from "@react-hook/window-size";
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

  return (
    <div className="flex flex-col gap-4 h-full">
      <div className="flex justify-between">
        <h3 className="font-semibold text-[28px]">Список сигналов</h3>
        <Link to="signals">
          <ArrowRightUp className="text-primary dark:text-primary-dark" />
        </Link>
      </div>
      <div
        className={`w-full h-full overflow-y-auto ${
          onlyWidth < 620 && "overflow-x-scroll"
        } scroll-container`}
      >
        {Signals.length > 0 ? (
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
