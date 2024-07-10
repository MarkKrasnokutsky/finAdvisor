import { Tariff } from "@/types/tariff";

type TariifBuy = Omit<Tariff, "id"> & { img: string };

type TariffBuyCardProps = {
  tariff: TariifBuy;
};

export const TariffBuyCard: React.FC<TariffBuyCardProps> = ({ tariff }) => {
  return (
    <div className="flex flex-col text-center items-center gap-y-2">
      <h4 className="text-[28px] font-semibold">{tariff.name}</h4>
      <img className="w-40 " src={tariff.img} alt="тариф" />
      <p className="text-lg font-medium ">
        {tariff.instrumentCount} доступных инструментов
      </p>
      <p className="text-2xl font-semibold ">
        {tariff.cost}₽
        <span className="text-base text-secondary dark:text-profile-dark">
          /мес
        </span>
      </p>
      <button className="text-center w-full py-5 transition-all border rounded-[20px] border-primary text-primary font-medium hover:bg-primary hover:text-secondaryBg dark:border-primary-dark dark:text-primary-dark dark:hover:bg-primary-dark dark:hover:text-secondaryBg-dark">
        Выбрать
      </button>
    </div>
  );
};
// mt-4 mb-8
// mb-7 mt-4
