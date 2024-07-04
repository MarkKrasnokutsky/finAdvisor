type HelpProps = {
  className?: string;
};

export const Help: React.FC<HelpProps> = ({ className }) => {
  return (
    <svg
      width="30"
      height="30"
      viewBox="0 0 30 30"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <circle cx="15" cy="15" r="11.25" strokeWidth="2" />
      <circle cx="15" cy="22.5" r="0.9375" />
      <path
        d="M15 20V18.929C15 18.0363 15.4462 17.2025 16.189 16.7073L16.9371 16.2086C18.0697 15.4535 18.75 14.1824 18.75 12.8212V12.5C18.75 10.4289 17.0711 8.75 15 8.75V8.75C12.9289 8.75 11.25 10.4289 11.25 12.5V12.5"
        strokeWidth="2"
      />
    </svg>
  );
};
