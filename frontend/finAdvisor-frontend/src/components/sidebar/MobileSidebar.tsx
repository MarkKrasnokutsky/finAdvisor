import { Burger, Close } from "@/assets";
import { useState } from "react";
import { ActiveMenu } from "./ActiveMenu";

export const MobileSidebar: React.FC = () => {
  const [toggleMenu, setToggleMenu] = useState(false);

  const openMenuHandler = () => {
    setToggleMenu(true);
  };
  const closeMenuHandler = () => {
    setToggleMenu(false);
  };
  return (
    <div
      onClick={openMenuHandler}
      className="flex items-center justify-center size-full"
    >
      {toggleMenu ? (
        <>
          <Close
            onClick={closeMenuHandler}
            className="stroke-primary  dark:stroke-primary-dark z-[51]"
          />
          <ActiveMenu onClick={closeMenuHandler} />
        </>
      ) : (
        <Burger className="cursor-pointer text-secondary dark:" />
      )}
    </div>
  );
};
