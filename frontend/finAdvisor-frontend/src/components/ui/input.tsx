import * as React from "react";

import { cn } from "@/lib/utils";

import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { Search } from "@/assets";

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  showPassword?: boolean;
  setShowPassword?: (show: boolean) => void;
  isPassword?: boolean;
  isSearch?: boolean;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, isPassword, isSearch, ...props }, ref) => {
    const [showPassword, setShowPassword] = React.useState<boolean>(false);
    return (
      <div className="relative">
        <input
          type={isPassword ? (showPassword ? "text" : "password") : type}
          className={cn(
            "flex h-14 w-full rounded-md border border-input/50 bg-background/0 px-3 py-2 text-lg text-white  ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-white/50 placeholder:text-wrap focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50",
            className
          )}
          ref={ref}
          {...props}
        />

        {isSearch && (
          <div className="absolute top-1 left-1 text-search-text flex gap-1 items-center">
            <Search />
            <p className="text-xs font-medium">{/* Поиск */}</p>
          </div>
        )}

        {isPassword && (
          <button
            type="button"
            onClick={() => setShowPassword(!showPassword)}
            className="absolute top-1/4 right-3"
          >
            {showPassword ? (
              <AiOutlineEye className="size-6 text-white" />
            ) : (
              <AiOutlineEyeInvisible className="size-6 text-white" />
            )}
          </button>
        )}
      </div>
    );
  }
);
Input.displayName = "Input";

export { Input };
