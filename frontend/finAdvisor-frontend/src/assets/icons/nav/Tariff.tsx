type TariffProps = {
  className?: string;
};

export const Tariff: React.FC<TariffProps> = ({ className }) => {
  return (
    <svg
      width="30"
      height="30"
      viewBox="0 0 30 30"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <path
        d="M15 27.5V7.5C15 5.1425 15 3.965 14.2675 3.2325C13.535 2.5 12.3575 2.5 10 2.5H7.5C5.1425 2.5 3.965 2.5 3.2325 3.2325C2.5 3.965 2.5 5.1425 2.5 7.5V22.5C2.5 24.8575 2.5 26.035 3.2325 26.7675C3.965 27.5 5.1425 27.5 7.5 27.5H15ZM15 27.5H22.5C24.8575 27.5 26.035 27.5 26.7675 26.7675C27.5 26.035 27.5 24.8575 27.5 22.5V15C27.5 12.6425 27.5 11.465 26.7675 10.7325C26.035 10 24.8575 10 22.5 10H15M23.125 20H19.375M23.125 15H19.375M10.625 17.5H6.875M10.625 12.5H6.875M10.625 7.5H6.875"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
