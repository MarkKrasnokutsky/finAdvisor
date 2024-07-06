type ToolProps = {
  className?: string;
};

export const Tool: React.FC<ToolProps> = ({ className }) => {
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
        d="M3.75 13.5C3.75 12.9477 4.19772 12.5 4.75 12.5H7.75C8.30228 12.5 8.75 12.9477 8.75 13.5V20.25C8.75 20.8023 8.30228 21.25 7.75 21.25H4.75C4.19772 21.25 3.75 20.8023 3.75 20.25V13.5ZM12.5 9.75C12.5 9.19772 12.9477 8.75 13.5 8.75H16.5C17.0523 8.75 17.5 9.19772 17.5 9.75V24C17.5 24.5523 17.0523 25 16.5 25H13.5C12.9477 25 12.5 24.5523 12.5 24V9.75Z"
        strokeWidth="2"
        strokeLinejoin="round"
      />
      <path
        d="M15 27.5V25"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M21.25 8.5C21.25 7.94772 21.6977 7.5 22.25 7.5H25.25C25.8023 7.5 26.25 7.94772 26.25 8.5V12.125C26.25 12.6773 25.8023 13.125 25.25 13.125H22.25C21.6977 13.125 21.25 12.6773 21.25 12.125V8.5Z"
        strokeWidth="2"
        strokeLinejoin="round"
      />
      <path
        d="M6.25 12.5V6.25M23.75 21.25V13.125M23.75 7.5V2.5"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};
