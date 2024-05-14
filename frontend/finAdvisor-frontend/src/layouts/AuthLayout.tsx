import React from "react";

const AuthLayout: React.FC<React.PropsWithChildren> = ({ children }) => {
  return (
    <div className="h-screen w-screen bg-backgroundMain  relative">
      <div className="fixed h-screen -left-64 blur-[70px] z-10 animate-blob">
        {/* <svg
          viewBox="0 0 200 200"
          xmlns="http://www.w3.org/2000/svg"
          className="h-screen "
        >
          <path
            fill="#0095F0"
            d="M40.4,-72.7C48.5,-65.2,48.7,-46.7,52.8,-32.8C56.9,-18.8,64.9,-9.4,70.5,3.3C76.1,15.9,79.5,31.9,75.5,46.1C71.6,60.3,60.4,72.7,46.6,79.5C32.8,86.2,16.4,87.3,0.6,86.2C-15.1,85.1,-30.3,81.9,-40.9,73.3C-51.5,64.7,-57.6,50.7,-61.2,37.6C-64.8,24.5,-65.9,12.3,-67.3,-0.8C-68.7,-13.9,-70.4,-27.8,-66.9,-41.1C-63.5,-54.4,-54.8,-67.2,-42.8,-72.5C-30.8,-77.8,-15.4,-75.6,0.4,-76.2C16.1,-76.8,32.2,-80.2,40.4,-72.7Z"
            transform="translate(100 100)"
          />
          
        </svg> */}
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
          className="h-screen "
        >
          {" "}
          <defs>
            {" "}
            <linearGradient id="sw-gradient" x1="0" x2="1" y1="1" y2="0">
              {" "}
              <stop
                id="stop1"
                stop-color="rgba(45, 189, 222, 1)"
                offset="0%"
              ></stop>{" "}
              <stop
                id="stop2"
                stop-color="rgba(99, 19, 177, 1)"
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
            stroke-width="0"
          ></path>{" "}
        </svg>
      </div>
      <div className="fixed right-24 top-96 blur-[70px] z-10 animate-blob animation-delay-2000">
        {/* <svg
          viewBox="0 0 200 200"
          xmlns="http://www.w3.org/2000/svg"
          className="h-screen "
        >
          <path
            fill="#0095F0"
            d="M40.4,-72.7C48.5,-65.2,48.7,-46.7,52.8,-32.8C56.9,-18.8,64.9,-9.4,70.5,3.3C76.1,15.9,79.5,31.9,75.5,46.1C71.6,60.3,60.4,72.7,46.6,79.5C32.8,86.2,16.4,87.3,0.6,86.2C-15.1,85.1,-30.3,81.9,-40.9,73.3C-51.5,64.7,-57.6,50.7,-61.2,37.6C-64.8,24.5,-65.9,12.3,-67.3,-0.8C-68.7,-13.9,-70.4,-27.8,-66.9,-41.1C-63.5,-54.4,-54.8,-67.2,-42.8,-72.5C-30.8,-77.8,-15.4,-75.6,0.4,-76.2C16.1,-76.8,32.2,-80.2,40.4,-72.7Z"
            transform="translate(100 100)"
          />
          
        </svg> */}
        <svg
          id="sw-js-blob-svg"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
          className="h-96 "
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
            stroke-width="0"
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
