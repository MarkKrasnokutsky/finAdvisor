type BurgerProps = {
  className?: string;
};

export const Burger: React.FC<BurgerProps> = ({ className }) => {
  return (
    <svg
      width="20"
      height="10"
      viewBox="0 0 20 10"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={` stroke-current ${className}`}
    >
      <path d="M1 1H19" strokeLinecap="round" />
      <path d="M1 5H19" strokeLinecap="round" />
      <path d="M1 9H19" strokeLinecap="round" />
    </svg>
  );
};
