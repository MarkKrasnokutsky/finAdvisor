import { useTitle } from "@/api/hooks/useContext";
import { NotificationIcon } from "@/assets";
import { useWindowWidth } from "@react-hook/window-size";
import { MobileSidebar } from "./sidebar/MobileSidebar";

export const Header: React.FC = () => {
  const notifications = ["1"];
  const { title } = useTitle();
  const onlyWidth = useWindowWidth();
  return (
    <div className="flex justify-between items-center mt-16 mb-16">
      <h2 className="font-semibold text-4xl text-primary dark:text-primary-dark">
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
          <div className="flex relative items-center justify-center rounded-full size-16 bg-secondaryBg text-notification transition-all hover:text-primary dark:bg-secondaryBg-dark dark:notification-dark dark:hover:text-primary-dark ">
            <MobileSidebar />
          </div>
        )}
      </div>
    </div>
  );
};
