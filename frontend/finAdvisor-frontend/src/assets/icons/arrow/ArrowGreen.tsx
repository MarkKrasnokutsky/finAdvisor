type ArrowGreenProps = {
  className?: string;
};

export const ArrowGreen: React.FC<ArrowGreenProps> = ({ className }) => {
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
        d="M11.2238 6.08301L2.14922 15.1575"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path d="M14.0605 2.94306L10.65 3.79568C10.0467 3.94649 9.83864 4.69777 10.2783 5.13748L12.1692 7.02835C12.6089 7.46805 13.3602 7.25996 13.511 6.65669L14.3636 3.24623C14.4094 3.06314 14.2436 2.89729 14.0605 2.94306Z" />
    </svg>
  );
};
