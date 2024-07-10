import { useTitle } from "@/api/hooks/useContext";
import { NotificationIcon } from "@/assets";
import { useWindowWidth } from "@react-hook/window-size";
import { MobileSidebar } from "./sidebar/MobileSidebar";
import clsx from "clsx";

export const Header: React.FC = () => {
  const notifications = ["1"];
  const { title } = useTitle();
  const onlyWidth = useWindowWidth();
  return (
    <div
      className={clsx("flex justify-between items-center ", {
        "my-16": onlyWidth > 1600,
        "my-10": onlyWidth < 1600 && onlyWidth > 1300,
        "my-4": onlyWidth < 1300,
      })}
    >
      <h2
        className={clsx(
          `font-semibold text-primary dark:text-primary-dark`,
          { "text-4xl": onlyWidth > 850 },
          { "text-3xl": onlyWidth < 850 },
          { "text-2xl": onlyWidth < 600 }
        )}
      >
        {title}
      </h2>
      <div className="flex gap-x-2">
        <div className="flex relative items-center justify-center rounded-full size-16 bg-secondaryBg text-notification transition-all hover:text-primary dark:bg-secondaryBg-dark dark:notification-dark dark:hover:text-primary-dark ">
          {notifications && (
            <div className="absolute right-6 top-5 size-[6px] rounded-full bg-notification-active dark:bg-notification-activeDark"></div>
          )}
          <NotificationIcon />
        </div>
        {onlyWidth < 1000 && (
          <div className="flex items-center justify-center rounded-full size-16 bg-secondaryBg text-notification transition-all hover:text-primary dark:bg-secondaryBg-dark dark:notification-dark dark:hover:text-primary-dark ">
            <MobileSidebar />
          </div>
        )}
      </div>
    </div>
  );
};
