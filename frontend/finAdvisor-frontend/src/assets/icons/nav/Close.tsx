type CloseProps = {
  className?: string;
  onClick: (event: React.MouseEvent<SVGElement, MouseEvent>) => void;
};

export const Close: React.FC<CloseProps> = ({ className, onClick }) => {
  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current ${className}`}
      onClick={(event) => {
        event.stopPropagation();
        onClick(event);
      }}
    >
      <path
        d="M18 6L6 18"
        strokeWidth="1.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M6 6L18 18"
        strokeWidth="1.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
