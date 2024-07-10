import { ArrowUpDown, CalendarIcon } from "@/assets";
import {
  getYesterdayDate,
  handleToggleFocus,
  todayMoscowDate,
} from "@/lib/utils";
import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";
import { useRef, useState } from "react";

import { Calendar, ToolsCombobox } from "@/components";
import { ru } from "date-fns/locale";
import { format } from "date-fns";
import { useClickOutside } from "@/api/hooks/useClickOutside";
import { useTools } from "@/api/hooks/useTools";

type SignalHeaderProps = {
  getDateHandler: (date: string) => void;
};

export const SignalHeader: React.FC<SignalHeaderProps> = ({
  getDateHandler,
}) => {
  const onlyWidth = useWindowWidth();

  const arrowRef = useRef<HTMLDivElement>(null);
  const caledarRef = useRef<HTMLDivElement>(null);
  const [isFocusedTools, setIsFocusedTools] = useState(false);
  const [isFocusedCalendar, setIsFocusedCalenar] = useState(false);
  const [date, setDate] = useState<Date | undefined>(new Date());
  const [filteredSignals, setFilteredSignals] = useState("");

  const { data: tools } = useTools();

  const handleToggleVisible = () => {
    setIsFocusedCalenar(true);
  };

  useClickOutside(caledarRef, setIsFocusedCalenar);
  useClickOutside(arrowRef, setIsFocusedTools);

  const getTodayDateHandler = (func: () => string) => {
    setFilteredSignals(func);
  };

  const handleDayPickerSelect = (date: Date | undefined) => {
    if (!date) {
      setDate(undefined);
      setFilteredSignals("");
    } else {
      setDate(date);
      setFilteredSignals(format(date, "yyyy-MM-dd"));
    }
  };

  getDateHandler(filteredSignals);

  return (
    <div
      className={clsx(
        "relative flex flex-wrap justify-between gap-y-6  font-medium text-primary dark:text-primary-dark",
        {
          "text-lg": onlyWidth > 1450,
          "": onlyWidth < 1450,
        }
      )}
    >
      <div className="flex gap-x-4 items-center">
        <div
          onClick={() => getTodayDateHandler(todayMoscowDate)}
          className={clsx(
            "cursor-pointer transition-all border border-primary rounded-[50px] hover:bg-profile-dark/50  dark:border-primary-dark dark:hover:bg-primary-dark/10 ",
            {
              "px-8 py-3": onlyWidth > 1450,
              "px-5 py-2": onlyWidth < 1450,
              hidden: onlyWidth < 1000,
            }
          )}
        >
          Сегодня
        </div>
        <div
          onClick={() => getTodayDateHandler(getYesterdayDate)}
          className={clsx(
            "cursor-pointer transition-all border border-primary rounded-[50px]  hover:bg-profile-dark/50 dark:border-primary-dark dark:hover:bg-primary-dark/10",
            {
              "px-8 py-3": onlyWidth > 1450,
              "px-5 py-2": onlyWidth < 1450,
              hidden: onlyWidth < 1000,
            }
          )}
        >
          Вчера
        </div>
        <div
          className={clsx("flex items-center gap-x-4 pr-4 ", {
            "border rounded-full border-primary dark:border-primary-dark":
              filteredSignals,
          })}
        >
          <div
            ref={caledarRef}
            onClick={handleToggleVisible}
            className={clsx(
              "relative cursor-pointer transition-all flex items-center justify-center  border border-primary rounded-full  dark:border-primary-dark ",
              {
                "hover:bg-profile-dark/50 dark:hover:bg-primary-dark/10":
                  !isFocusedCalendar,
                "border-none": isFocusedCalendar,
                "p-4": onlyWidth > 1450,
                "p-3": onlyWidth < 1450,
                "bg-primary dark:bg-primary-dark text-primary-dark hover:text-primary dark:text-primary dark:hover:text-primary-dark":
                  filteredSignals,
              }
            )}
          >
            {isFocusedCalendar ? (
              <div className="absolute left-0 top-0 z-40">
                <Calendar
                  locale={ru}
                  mode="single"
                  selected={date}
                  onSelect={handleDayPickerSelect}
                  className="rounded-md border  bg-secondaryBg border-primary dark:border-primary-dark  dark:bg-secondaryBg-dark"
                />
              </div>
            ) : (
              <CalendarIcon
                className={clsx(``, {
                  "text-primary dark:text-primary-dark": !filteredSignals,
                })}
              />
            )}
          </div>
          {filteredSignals}
        </div>
      </div>
      {isFocusedTools && tools ? (
        <ToolsCombobox tools={tools} setIsFocusedTools={setIsFocusedTools} />
      ) : (
        <div
          onClick={() =>
            handleToggleFocus(arrowRef, isFocusedTools, setIsFocusedTools)
          }
          ref={arrowRef}
          className={clsx(
            "flex gap-x-3 cursor-pointer transition-all border border-primary rounded-[50px] p-3 hover:bg-profile-dark/50   dark:border-primary-dark dark:hover:bg-primary-dark/10"
            // {
            //   "px-8 py-3": onlyWidth > 1450,
            //   "px-5 py-2": onlyWidth < 1450,
            // }
          )}
        >
          <p>Инструменты</p>
          <div
            tabIndex={0}
            className="flex flex-col items-center justify-center p-2 transition-all bg-primary rounded-full focus:rotate-180 dark:bg-primary-dark"
          >
            <ArrowUpDown className="text-secondaryBg dark:text-secondaryBg-dark" />
          </div>
        </div>
      )}
    </div>
  );
};

export default SignalHeader;