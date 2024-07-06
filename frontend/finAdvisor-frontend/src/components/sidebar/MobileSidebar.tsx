import { Burger } from "@/assets";
import { useState } from "react";

export const MobileSidebar: React.FC = () => {
  const [toggleMenu, setToggleMenu] = useState(false);

  const toggleMenuHandler = () => {
    setToggleMenu(!toggleMenu);
  };
  return (
    <div onClick={toggleMenuHandler}>
      <Burger className="cursor-pointer text-secondary dark:" />
    </div>
  );
};
