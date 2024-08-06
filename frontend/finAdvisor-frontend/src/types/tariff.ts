export type Tariff = {
  id: number;
  cost: number;
  name: string;
  instrumentCount: number;
};

export type TariffChangeInput = {
  name: string;
  duration: string;
};
