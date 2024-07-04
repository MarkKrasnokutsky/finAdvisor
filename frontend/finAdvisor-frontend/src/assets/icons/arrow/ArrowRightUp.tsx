type ArrowRightUpProps = {
  className?: string;
};

export const ArrowRightUp: React.FC<ArrowRightUpProps> = ({ className }) => {
  return (
    <svg
      width="42"
      height="42"
      viewBox="0 0 42 42"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`fill-current rounded-full border border-primary dark:border-primary-dark ${className}`}
    >
      <path d="M27.8285 14.1711L27.8285 13.6711L28.3285 13.6711L28.3285 14.1711L27.8285 14.1711ZM15.3787 27.328C15.1835 27.5232 14.8669 27.5232 14.6716 27.328C14.4764 27.1327 14.4764 26.8161 14.6716 26.6209L15.3787 27.328ZM17.5858 13.6711H27.8285L27.8285 14.6711H17.5858L17.5858 13.6711ZM28.3285 14.1711L28.3285 24.4138L27.3285 24.4138L27.3285 14.1711L28.3285 14.1711ZM28.182 14.5247L15.3787 27.328L14.6716 26.6209L27.4749 13.8176L28.182 14.5247Z" />
    </svg>
  );
};
