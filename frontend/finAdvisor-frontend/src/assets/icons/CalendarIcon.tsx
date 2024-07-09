type CalendarProps = {
  className?: string;
};

export const CalendarIcon: React.FC<CalendarProps> = ({ className }) => {
  return (
    <svg
      width="22"
      height="22"
      viewBox="0 0 22 22"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`stroke-current  ${className}`}
    >
      <rect x="2.75" y="5.5" width="16.5" height="13.75" rx="2" />
      <path
        d="M2.75 9.16667C2.75 7.60295 2.75 6.82109 3.16329 6.28248C3.26969 6.14381 3.39381 6.01969 3.53248 5.91329C4.07109 5.5 4.85295 5.5 6.41667 5.5H15.5833C17.147 5.5 17.9289 5.5 18.4675 5.91329C18.6062 6.01969 18.7303 6.14381 18.8367 6.28248C19.25 6.82109 19.25 7.60295 19.25 9.16667H2.75Z"
        className={`fill-current  ${className}`}
      />
      <path d="M6.41663 2.75L6.41663 5.5" strokeLinecap="round" />
      <path d="M15.5834 2.75L15.5834 5.5" strokeLinecap="round" />
      <rect
        x="6.41663"
        y="11"
        width="3.66667"
        height="1.83333"
        rx="0.5"
        className={`fill-current  ${className}`}
      />
      <rect
        x="6.41663"
        y="14.667"
        width="3.66667"
        height="1.83333"
        rx="0.5"
        className={`fill-current  ${className}`}
      />
      <rect
        x="11.9166"
        y="11"
        width="3.66667"
        height="1.83333"
        rx="0.5"
        className={`fill-current  ${className}`}
      />
      <rect
        x="11.9166"
        y="14.667"
        width="3.66667"
        height="1.83333"
        rx="0.5"
        className={`fill-current  ${className}`}
      />
    </svg>
  );
};
