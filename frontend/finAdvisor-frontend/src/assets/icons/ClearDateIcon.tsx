type ClearDateIconProps = {
  className?: string;
};

export const ClearDateIcon: React.FC<ClearDateIconProps> = () => {
  return (
    <svg
      width="8"
      height="8"
      viewBox="0 0 8 8"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M7 1L1 7"
        stroke="#E9E9E9"
        strokeWidth="0.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M1 1L7 7"
        stroke="#E9E9E9"
        strokeWidth="0.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
