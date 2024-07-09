/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ["class"],
  content: [
    "./pages/**/*.{ts,tsx}",
    "./components/**/*.{ts,tsx}",
    "./app/**/*.{ts,tsx}",
    "./src/**/*.{ts,tsx}",
  ],
  prefix: "",
  theme: {
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    extend: {
      fontFamily: { body: ["Montserrat"] },
      boxShadow: {
        custom: "0 4px 25px 0 rgba(197, 192, 192, 0.6)",
        customDark: "0 4px 25px 0 rgba(6, 7, 9, 0.6);",
        customDarkTools: "8px 8px 20px 0 #0e1011",
      },
      scrollbar: ["dark"],

      colors: {
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: {
          DEFAULT: "#D0D0D0",
          dark: "#0B0D0F",
        },
        secondaryBg: {
          DEFAULT: "#D9D9D9",
          dark: "#111314",
        },

        foreground: "hsl(var(--foreground))",
        primary: {
          DEFAULT: "#0B0D0F",
          dark: "#E9E9E9",
        },
        logo: {
          DEFAULT: "#ECECEC",
        },
        profile: {
          dark: "#B3B3B3",
        },
        nav: {
          DEFAULT: "#4D4D4D",
          dark: "#8C8C8C",
          hover: "#0B0D0F",
          hoverDark: "#E9E9E9",
          focus: "#A9A9A9",
          focusDark: "#E9E9E9",
        },
        telegram: {
          DEFAULT: "#0D72D0",
          dark: "#0B62B3",
        },
        notification: {
          DEFAULT: "#767676",
          dark: "#8C8C8C",
          activeDark: "#820101",
          active: "#BD3D2B",
        },
        arrow: {
          green: "#3AAA13",
          red: "#BD3D2B",
          greenDark: "#1D6E00",
          redDark: "#6E0D00",
        },
        secondary: {
          DEFAULT: "#8C8C8C",
          dark: "#353535",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "#0B62B3",
          hover: "#0a559b",
          // foreground: "hsl(var(--accent-foreground))",
        },
        search: {
          DEFAULT: "#B7B7B7",
          dark: "#1A1C1D",
          text: "#4A4C52",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
        blob: {
          "0%, 100%": {
            transform: "translate(0px, 0px) scale(1)",
          },
          "25%": {
            transform: "translate(70px, -70px) scale(1.1)", // + r, + d
          },
          "50%": {
            transform: "translate(0px, 70px) scale(0.9)",
          },
          "75%": {
            transform: "translate(-70px, 70px) scale(0.9)",
          },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
        blob: "blob 7s  infinite",
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
};
