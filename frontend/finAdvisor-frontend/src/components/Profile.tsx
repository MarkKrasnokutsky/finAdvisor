import { useWindowWidth } from "@react-hook/window-size";
import clsx from "clsx";

type ProfileProps = {
  username: string;
  email: string;
};

export const Profile: React.FC<ProfileProps> = ({ username, email }) => {
  const onlyWidth = useWindowWidth();
  return (
    <div className="flex flex-col justify-between h-full ">
      <div
        className={clsx("flex flex-col", {
          "gap-y-6": onlyWidth > 1400,
          "gap-y-2": onlyWidth < 1400,
        })}
      >
        <h3 className="font-semibold text-[28px]">Профиль</h3>
        <div
          className={clsx("flex flex-col", {
            " gap-y-5": onlyWidth > 1600,
            "gap-y-2": onlyWidth < 1600,
          })}
        >
          <div className="">
            <span
              className={clsx("text-secondary dark:text-profile-dark", {
                "text-sm": onlyWidth > 1400,
                "text-xs": onlyWidth < 1400,
              })}
            >
              Никнейм
            </span>
            <p
              className={clsx("font-medium", {
                "text-lg": onlyWidth > 1400,
                "text-base": onlyWidth < 1400,
              })}
            >
              {username}
            </p>
          </div>
          <div className="">
            <span
              className={clsx(" text-secondary dark:text-profile-dark", {
                "text-sm": onlyWidth > 1400,
                "text-xs": onlyWidth < 1400,
              })}
            >
              Электронная почта
            </span>
            <p
              className={clsx("font-medium", {
                "text-lg": onlyWidth > 1400,
                "text-base": onlyWidth < 1400,
              })}
            >
              {email}
            </p>
          </div>
        </div>
      </div>
      <div className="">
        <p
          className={clsx("text-sm text-secondary dark:text-profile-dark", {
            "text-sm": onlyWidth > 1400,
            "text-xs": onlyWidth < 1400,
          })}
        >
          Получайте уведомления обо всех сигналах прямо у себя в{" "}
          {/* SECRET RED FLAG 2/4 - ссылка на тг канал */}
          <a
            href="https://t.me/profitpicks_news"
            className="cursor-pointer text-telegram dark:text-telegram-dark"
          >
            telegram
          </a>
        </p>
      </div>
    </div>
  );
};
