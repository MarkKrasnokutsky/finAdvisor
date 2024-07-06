type NotificationIconProps = {
  className?: string;
};

export const NotificationIcon: React.FC<NotificationIconProps> = ({
  className,
}) => {
  return (
    <svg
      width="20"
      height="22"
      viewBox="0 0 20 22"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`fill-current ${className}`}
    >
      <path d="M2.66668 7.33333C2.66668 5.38841 3.43929 3.52315 4.81456 2.14788C6.18983 0.772617 8.05509 0 10 0C11.9449 0 13.8102 0.772617 15.1855 2.14788C16.5607 3.52315 17.3333 5.38841 17.3333 7.33333V11.6389L19.1667 14.3889V18.3333H14.0233C13.8148 19.2442 13.3033 20.0574 12.5725 20.6398C11.8417 21.2222 10.9349 21.5394 10.0005 21.5394C9.06601 21.5394 8.15921 21.2222 7.42844 20.6398C6.69766 20.0574 6.18618 19.2442 5.97768 18.3333H0.833344V14.3889L2.66668 11.6389V7.33333ZM7.89901 18.3333C8.07719 18.7424 8.37091 19.0905 8.74413 19.3351C9.11735 19.5796 9.55383 19.7098 10 19.7098C10.4462 19.7098 10.8827 19.5796 11.2559 19.3351C11.6291 19.0905 11.9228 18.7424 12.101 18.3333H7.89901ZM10 1.83333C8.54132 1.83333 7.14237 2.4128 6.11092 3.44425C5.07947 4.4757 4.50001 5.87464 4.50001 7.33333V12.1944L2.66668 14.9444V16.5H17.3333V14.9444L15.5 12.1944V7.33333C15.5 5.87464 14.9205 4.4757 13.8891 3.44425C12.8576 2.4128 11.4587 1.83333 10 1.83333Z" />
    </svg>
  );
};