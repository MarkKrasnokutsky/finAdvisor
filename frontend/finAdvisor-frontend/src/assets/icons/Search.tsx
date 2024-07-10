type SearchProps = {
  className?: string;
};

export const Search: React.FC<SearchProps> = ({ className }) => {
  return (
    <svg
      width="18"
      height="17"
      viewBox="0 0 18 17"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current ${className}`}
    >
      <ellipse cx="8.2613" cy="8" rx="5.05226" ry="5" />
      <path d="M15.3344 15L13.3135 13" strokeLinecap="round" />
    </svg>
  );
};
