type LogoProps = {
  className?: string;
};

export const Logo: React.FC<LogoProps> = ({ className }) => {
  return (
    <svg
      // width="74"
      // height="64"
      viewBox="0 0 74 64"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M32.85 64C50.9925 64 65.6999 49.6731 65.6999 32C65.6999 29.1054 65.3054 26.3006 64.5659 23.6338L42.5456 42.9989L25.5503 35.7767L8.58974 53.5758C14.5967 59.9816 23.2416 64 32.85 64ZM1.98638 42.9842C0.701245 39.5585 0 35.859 0 32C0 14.3269 14.7074 0 32.85 0C43.1584 0 52.3578 4.62528 58.3805 11.8616L40.1503 28.4433L23.155 21.3322L1.98638 42.9842Z"
        fill="url(#paint0_linear_891_2047)"
      />
      <path
        d="M24.2954 26.1114L3.65015 46.1114L6.84389 50.3337L24.9798 32.1114L42.0892 39.0003L66.0423 16.7781L69.1219 19.8892L73.0001 7.66699L59.7688 10.0003L62.5063 13.2225L40.6064 32.7781L24.2954 26.1114Z"
        fill="#16C894"
      />
      <g filter="url(#filter0_f_891_2047)">
        <path
          d="M24.2954 26.1114L3.65015 46.1114L6.84389 50.3337L24.9798 32.1114L42.0892 39.0003L66.0423 16.7781L69.1219 19.8892L73.0001 7.66699L59.7688 10.0003L62.5063 13.2225L40.6064 32.7781L24.2954 26.1114Z"
          fill="#16C894"
        />
      </g>
      <defs>
        <filter
          id="filter0_f_891_2047"
          x="2.65015"
          y="6.66699"
          width="71.3499"
          height="44.667"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="BackgroundImageFix"
            result="shape"
          />
          <feGaussianBlur
            stdDeviation="0.5"
            result="effect1_foregroundBlur_891_2047"
          />
        </filter>
        <linearGradient
          id="paint0_linear_891_2047"
          x1="7.87031"
          y1="4.55556"
          x2="57.7916"
          y2="63.0752"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor="#054C80" />
          <stop offset="0.333333" stopColor="#1AA493" />
          <stop offset="0.666667" stopColor="#1AA493" />
          <stop offset="1" stopColor="#054C80" />
        </linearGradient>
      </defs>
    </svg>
  );
};
