export type InputData = {
  username: string;
  password: string;
  email?: string;
};

export type ResponseErrors = {
  response?: {
    data?: string;
    status?: number;
  };
};

export type ErrorsState = {
  data?: string;
  status?: number;
};

export type Tariff = {
  id: number;
  name: string;
  cost?: number;
};
export interface ResponseData {
  username: string;
  tariff: Tariff;
  email: string;
}
