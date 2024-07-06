type ArrowRedProps = {
  className?: string;
};

export const ArrowRed: React.FC<ArrowRedProps> = ({ className }) => {
  return (
    <svg
      width="17"
      height="18"
      viewBox="0 0 17 18"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`fill-current ${className}`}
    >
      <path
        d="M11.4282 11.917L2.35369 2.84246"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path d="M14.5682 14.7532L13.7156 11.3427C13.5647 10.7395 12.8135 10.5314 12.3738 10.9711L10.4829 12.862C10.0432 13.3017 10.2513 14.0529 10.8545 14.2038L14.265 15.0564C14.4481 15.1022 14.6139 14.9363 14.5682 14.7532Z" />
    </svg>
  );
};
