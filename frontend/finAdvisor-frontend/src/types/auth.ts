import { Tariff } from "./tariff";

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

export interface ResponseData {
  username: string;
  tariff: Tariff;
  email: string;
  tariffInception: string;
  tariffExpiration: string;
}

export type InputResetPasswordData = {
  email: string;
  code: string;
  newPassword: string;
  confirmPassword: string;
};
