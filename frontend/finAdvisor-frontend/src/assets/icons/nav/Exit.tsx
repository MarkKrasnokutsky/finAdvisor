type ExitProps = {
  className?: string;
};

export const Exit: React.FC<ExitProps> = ({ className }) => {
  return (
    <svg
      width="28"
      height="28"
      viewBox="0 0 28 28"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current w-[35px] ${className}`}
    >
      <path
        d="M17.5 9.625V7.4375C17.5 6.85734 17.2695 6.30094 16.8593 5.8907C16.4491 5.48047 15.8927 5.25 15.3125 5.25H4.8125C4.23234 5.25 3.67594 5.48047 3.2657 5.8907C2.85547 6.30094 2.625 6.85734 2.625 7.4375V20.5625C2.625 21.1427 2.85547 21.6991 3.2657 22.1093C3.67594 22.5195 4.23234 22.75 4.8125 22.75H15.3125C15.8927 22.75 16.4491 22.5195 16.8593 22.1093C17.2695 21.6991 17.5 21.1427 17.5 20.5625V18.375M21 9.625L25.375 14M25.375 14L21 18.375M25.375 14H10.4453"
        strokeLinecap="round"
        strokeWidth="2"
        strokeLinejoin="round"
      />
    </svg>
  );
};
