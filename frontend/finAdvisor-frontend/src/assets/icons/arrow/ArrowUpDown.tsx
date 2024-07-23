type ArrowUpDownProps = {
  className?: string;
};

export const ArrowUpDown: React.FC<ArrowUpDownProps> = ({ className }) => {
  return (
    <svg
      width="12"
      height="6"
      viewBox="0 0 12 6"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current ${className}`}
    >
      <path
        d="M10.5 0.75L6.70711 4.54289C6.31658 4.93342 5.68342 4.93342 5.29289 4.54289L1.5 0.750001"
        strokeWidth="1.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
