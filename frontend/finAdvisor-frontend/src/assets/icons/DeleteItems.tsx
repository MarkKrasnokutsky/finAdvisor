type DeleteItemsProps = {
  className?: string;
  onClick: React.MouseEventHandler<SVGSVGElement>;
};

export const DeleteItems: React.FC<DeleteItemsProps> = ({
  className,
  onClick,
}) => {
  return (
    <svg
      width="12"
      height="12"
      viewBox="0 0 12 12"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current ${className}`}
      onClick={onClick}
    >
      <path
        d="M11 1L1 11M1 1L11 11"
        strokeWidth="1.5"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
