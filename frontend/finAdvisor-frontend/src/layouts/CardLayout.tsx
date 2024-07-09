import { ReactNode } from "react";

type CardLayoutProps = {
  children: ReactNode;
  className?: string;
  columns?: number;
  rows?: number;
  rowStart?: number;
  rowEnd?: number;
};

export const CardLayout: React.FC<CardLayoutProps> = ({
  children,
  className,
  columns,
  rows,
  rowStart,
  rowEnd,
}) => {
  return (
    <div
      className={`p-8 h-full  bg-secondaryBg rounded-[30px] shadow-custom dark:shadow-customDark dark:bg-secondaryBg-dark col-span-${columns} row-span-${rows} col-start-${rowStart} col-end-${rowEnd} ${className}`}
    >
      {children}
    </div>
  );
};
