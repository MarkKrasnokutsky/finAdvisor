import { useAuthContext } from "@/api/hooks/useAuth";
import { useTitle } from "@/api/hooks/useContext";
import { Spinner } from "@/assets";
import { TariffBuyCard, TariffItem } from "@/components";
import { CardLayout } from "@/layouts/CardLayout";

import simple from "@/assets/tariffs/simple.png";
import plus from "@/assets/tariffs/plus.png";
import pro from "@/assets/tariffs/pro.png";
import vip from "@/assets/tariffs/vip.png";
import { useWindowWidth } from "@react-hook/window-size";

const tariffs = [
  { name: "Simple", img: simple, instrumentCount: 15, cost: 2490 },
  { name: "Plus", img: plus, instrumentCount: 35, cost: 3490 },
  { name: "Pro", img: pro, instrumentCount: 88, cost: 4990 },
  { name: "VIP", img: vip, instrumentCount: 175, cost: 7490 },
];

const Tariff: React.FC = () => {
  useTitle("Тарифы");
  // const paymentData = usePayment();
  const { authData } = useAuthContext();
  console.log("authData2: ", authData);

  const filterTariffs = authData?.tariff
    ? tariffs.filter(
        (a) =>
          a.name.toLocaleLowerCase() !==
          authData?.tariff.name.toLocaleLowerCase()
      )
    : tariffs;

  const isTariff = authData?.tariff ? true : false;
  const onlyWidth = useWindowWidth();
  return (
    <div className="flex flex-col gap-y-5 max-h-full overflow-auto scroll-container">
      {isTariff && (
        <CardLayout className=" border-primary border-4 ">
          {authData ? (
            <TariffItem
              name={authData.tariff.name}
              cost={authData.tariff.cost}
              tariffExpiration={authData.tariffExpiration}
              instrumentCount={authData.tariff.instrumentCount}
              isPage
              isTariff={isTariff}
            />
          ) : (
            <Spinner className="size-14 fill-primary dark:fill-primary-dark" />
          )}
        </CardLayout>
      )}

      <div
        className={`flex justify-around gap-5 ${
          onlyWidth < 800 && "flex-wrap"
        }`}
      >
        {filterTariffs.map((tariff, index) => (
          <CardLayout key={index}>
            <TariffBuyCard key={index} tariff={tariff} />
          </CardLayout>
        ))}
      </div>
    </div>
  );
};

export default Tariff;
