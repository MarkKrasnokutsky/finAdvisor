import clsx from "clsx";
import { ReactNode } from "react";
import { Link, useLocation } from "react-router-dom";

type NavProps = {
  links: {
    title: string;
    path: string;
    icon: ReactNode;
    default?: boolean;
  }[];
};

export const Nav: React.FC<NavProps> = ({ links }) => {
  const location = useLocation();
  const active = (path: string) => {
    return location.pathname === `/dashboard${path}`;
  };

  return (
    <nav className="flex flex-col space-y-4">
      {links.map((item, index) => (
        <Link
          to={`/dashboard${item.path}`}
          key={index}
          className={clsx(
            `flex gap-x-4 p-4 transition-all rounded-3xl text-nav  stroke-nav  dark:text-nav-dark  dark:stroke-nav-dark `,
            {
              "bg-nav-focus stroke-nav-hover text-nav-hover dark:bg-nav-focusDark dark:stroke-secondaryBg-dark dark:text-secondaryBg-dark":
                active(item.path),
              "hover:text-nav-hover hover:stroke-nav-hover dark:hover:text-nav-hoverDark dark:hover:stroke-nav-hoverDark ":
                !active(item.path),
            }
          )}
        >
          <i>{item.icon}</i>
          <p className={clsx("font-medium text-lg")}>{item.title}</p>
        </Link>
      ))}
    </nav>
  );
};

// dark:bg-nav-focusDark dark:stroke-nav-hoverDark dark:text-nav-hoverDark
