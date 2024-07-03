import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

const AuthLayout: React.FC<React.PropsWithChildren> = ({ children }) => {
  const onlyWidth = useWindowWidth();
  return (
    <div className="h-screen w-screen bg-background-dark  relative">
      <div className="fixed h-screen top-20 -left-64 blur-[70px] z-10 animate-blob">
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
          className={clsx({
            "h-[70vh]": onlyWidth > 1600,
            "h-[60vh]": onlyWidth < 1600 && onlyWidth > 1400,
            "h-[55vh]": onlyWidth < 1400 && onlyWidth > 1150,
            "h-[50vh]": onlyWidth < 1150 && onlyWidth > 960,
            "h-[40vh]": onlyWidth < 960 && onlyWidth > 800,
            "h-0": onlyWidth < 800,
          })}
        >
          {" "}
          <defs>
            {" "}
            <linearGradient id="sw-gradient" x1="0" x2="1" y1="1" y2="0">
              {" "}
              <stop
                id="stop1"
                stopColor="rgba(45, 189, 222, 1)"
                offset="0%"
              ></stop>{" "}
              <stop
                id="stop2"
                stopColor="rgba(99, 19, 177, 1)"
                offset="100%"
              ></stop>{" "}
            </linearGradient>{" "}
          </defs>{" "}
          <path
            fill="#ffffff"
            d="M25.9,-29.8C32.7,-25.2,36.8,-16.2,39.2,-6.3C41.7,3.5,42.5,14.2,38,21.9C33.5,29.5,23.8,34.1,13.9,37.2C4.1,40.3,-5.9,41.9,-14.8,39.3C-23.7,36.6,-31.4,29.6,-34.7,21.3C-37.9,13,-36.6,3.4,-34.9,-6.2C-33.3,-15.9,-31.4,-25.6,-25.6,-30.3C-19.7,-35.1,-9.9,-35,-0.1,-34.8C9.6,-34.6,19.1,-34.4,25.9,-29.8Z"
            width="100%"
            height="100%"
            transform="translate(50 50)"
            strokeWidth="0"
          ></path>{" "}
        </svg>
      </div>
      <div
        className={`${
          onlyWidth > 800 ? "fixed" : "hidden"
        }  right-24 top-96 blur-[70px] z-10 animate-blob animation-delay-2000`}
      >
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
          className="h-96"
        >
          {" "}
          <defs>
            {" "}
            <linearGradient id="sw-gradient1" x1="0" x2="1" y1="1" y2="0">
              {" "}
            </linearGradient>{" "}
          </defs>{" "}
          <path
            fill="url(#sw-gradient)"
            d="M25.9,-29.8C32.7,-25.2,36.8,-16.2,39.2,-6.3C41.7,3.5,42.5,14.2,38,21.9C33.5,29.5,23.8,34.1,13.9,37.2C4.1,40.3,-5.9,41.9,-14.8,39.3C-23.7,36.6,-31.4,29.6,-34.7,21.3C-37.9,13,-36.6,3.4,-34.9,-6.2C-33.3,-15.9,-31.4,-25.6,-25.6,-30.3C-19.7,-35.1,-9.9,-35,-0.1,-34.8C9.6,-34.6,19.1,-34.4,25.9,-29.8Z"
            width="100%"
            height="100%"
            transform="translate(50 50)"
            strokeWidth="0"
          ></path>{" "}
        </svg>
      </div>

      <div className="flex items-center justify-center size-full px-2 ">
        <div className="max-w-lg flex items-center justify-center size-full z-50">
          <div className="w-full py-16 rounded-3xl ">
            <div className="flex flex-col items-center">{children}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;
