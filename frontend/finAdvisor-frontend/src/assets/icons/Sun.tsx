type SunProps = {
  className?: string;
};

export const Sun: React.FC<SunProps> = ({ className }) => {
  return (
    <svg
      width="28"
      height="28"
      viewBox="0 0 28 28"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M13.9999 1.45801C14.232 1.45801 14.4545 1.5502 14.6186 1.71429C14.7827 1.87838 14.8749 2.10094 14.8749 2.33301V3.49967C14.8749 3.73174 14.7827 3.9543 14.6186 4.11839C14.4545 4.28249 14.232 4.37467 13.9999 4.37467C13.7679 4.37467 13.5453 4.28249 13.3812 4.11839C13.2171 3.9543 13.1249 3.73174 13.1249 3.49967V2.33301C13.1249 2.10094 13.2171 1.87838 13.3812 1.71429C13.5453 1.5502 13.7679 1.45801 13.9999 1.45801ZM5.13209 5.13184C5.29615 4.96798 5.51854 4.87594 5.75042 4.87594C5.98229 4.87594 6.20469 4.96798 6.36875 5.13184L6.82725 5.58917C6.98672 5.75413 7.07502 5.97511 7.07314 6.20454C7.07125 6.43396 6.97933 6.65347 6.81718 6.81577C6.65502 6.97808 6.4356 7.07021 6.20618 7.07231C5.97676 7.07441 5.75569 6.98632 5.59059 6.82701L5.13209 6.36851C4.96823 6.20445 4.87619 5.98205 4.87619 5.75017C4.87619 5.5183 4.96823 5.2959 5.13209 5.13184ZM22.8678 5.13184C23.0316 5.2959 23.1236 5.5183 23.1236 5.75017C23.1236 5.98205 23.0316 6.20445 22.8678 6.36851L22.4093 6.82701C22.2434 6.98157 22.024 7.06571 21.7973 7.06171C21.5706 7.05771 21.3543 6.96588 21.194 6.80557C21.0337 6.64525 20.9419 6.42897 20.9379 6.20228C20.9339 5.9756 21.018 5.75621 21.1726 5.59034L21.6311 5.13184C21.7952 4.96798 22.0175 4.87594 22.2494 4.87594C22.4813 4.87594 22.7037 4.96798 22.8678 5.13184ZM13.9999 7.87467C12.3755 7.87467 10.8176 8.51999 9.66889 9.66865C8.52023 10.8173 7.87492 12.3752 7.87492 13.9997C7.87492 15.6241 8.52023 17.182 9.66889 18.3307C10.8176 19.4794 12.3755 20.1247 13.9999 20.1247C15.6244 20.1247 17.1823 19.4794 18.3309 18.3307C19.4796 17.182 20.1249 15.6241 20.1249 13.9997C20.1249 12.3752 19.4796 10.8173 18.3309 9.66865C17.1823 8.51999 15.6244 7.87467 13.9999 7.87467ZM6.12492 13.9997C6.12492 11.9111 6.9546 9.90806 8.43145 8.43121C9.9083 6.95436 11.9113 6.12467 13.9999 6.12467C16.0885 6.12467 18.0915 6.95436 19.5684 8.43121C21.0452 9.90806 21.8749 11.9111 21.8749 13.9997C21.8749 16.0883 21.0452 18.0913 19.5684 19.5681C18.0915 21.045 16.0885 21.8747 13.9999 21.8747C11.9113 21.8747 9.9083 21.045 8.43145 19.5681C6.9546 18.0913 6.12492 16.0883 6.12492 13.9997ZM1.45825 13.9997C1.45825 13.7676 1.55044 13.5451 1.71453 13.381C1.87863 13.2169 2.10119 13.1247 2.33325 13.1247H3.49992C3.73198 13.1247 3.95454 13.2169 4.11864 13.381C4.28273 13.5451 4.37492 13.7676 4.37492 13.9997C4.37492 14.2317 4.28273 14.4543 4.11864 14.6184C3.95454 14.7825 3.73198 14.8747 3.49992 14.8747H2.33325C2.10119 14.8747 1.87863 14.7825 1.71453 14.6184C1.55044 14.4543 1.45825 14.2317 1.45825 13.9997ZM23.6249 13.9997C23.6249 13.7676 23.7171 13.5451 23.8812 13.381C24.0453 13.2169 24.2679 13.1247 24.4999 13.1247H25.6666C25.8987 13.1247 26.1212 13.2169 26.2853 13.381C26.4494 13.5451 26.5416 13.7676 26.5416 13.9997C26.5416 14.2317 26.4494 14.4543 26.2853 14.6184C26.1212 14.7825 25.8987 14.8747 25.6666 14.8747H24.4999C24.2679 14.8747 24.0453 14.7825 23.8812 14.6184C23.7171 14.4543 23.6249 14.2317 23.6249 13.9997ZM21.1726 21.1723C21.3367 21.0085 21.559 20.9164 21.7909 20.9164C22.0228 20.9164 22.2452 21.0085 22.4093 21.1723L22.8678 21.6308C22.9537 21.7109 23.0227 21.8075 23.0705 21.9149C23.1183 22.0222 23.144 22.1381 23.1461 22.2556C23.1482 22.3731 23.1266 22.4898 23.0826 22.5987C23.0386 22.7077 22.9731 22.8066 22.89 22.8897C22.8069 22.9728 22.7079 23.0383 22.599 23.0823C22.49 23.1263 22.3733 23.1479 22.2558 23.1459C22.1383 23.1438 22.0225 23.1181 21.9151 23.0703C21.8078 23.0224 21.7112 22.9535 21.6311 22.8675L21.1726 22.409C21.0087 22.2449 20.9167 22.0226 20.9167 21.7907C20.9167 21.5588 21.0087 21.3364 21.1726 21.1723ZM6.82725 21.1723C6.99111 21.3364 7.08315 21.5588 7.08315 21.7907C7.08315 22.0226 6.99111 22.2449 6.82725 22.409L6.36875 22.8675C6.28865 22.9535 6.19205 23.0224 6.08471 23.0703C5.97738 23.1181 5.86152 23.1438 5.74403 23.1459C5.62654 23.1479 5.50984 23.1263 5.40089 23.0823C5.29194 23.0383 5.19296 22.9728 5.10987 22.8897C5.02679 22.8066 4.96128 22.7077 4.91728 22.5987C4.87327 22.4898 4.85166 22.3731 4.85373 22.2556C4.8558 22.1381 4.88152 22.0222 4.92934 21.9149C4.97716 21.8075 5.04612 21.7109 5.13209 21.6308L5.58942 21.1723C5.67068 21.091 5.76716 21.0265 5.87336 20.9825C5.97955 20.9385 6.09338 20.9159 6.20834 20.9159C6.32329 20.9159 6.43712 20.9385 6.54331 20.9825C6.64951 21.0265 6.74599 21.091 6.82725 21.1723ZM13.9999 23.6247C14.232 23.6247 14.4545 23.7169 14.6186 23.881C14.7827 24.0451 14.8749 24.2676 14.8749 24.4997V25.6663C14.8749 25.8984 14.7827 26.121 14.6186 26.2851C14.4545 26.4492 14.232 26.5413 13.9999 26.5413C13.7679 26.5413 13.5453 26.4492 13.3812 26.2851C13.2171 26.121 13.1249 25.8984 13.1249 25.6663V24.4997C13.1249 24.2676 13.2171 24.0451 13.3812 23.881C13.5453 23.7169 13.7679 23.6247 13.9999 23.6247Z"
        fill="#353535"
      />
    </svg>
  );
};