export type CheckPaymentResponse = {
  status: string;
};
export type CreatePaymentResponse = {
  id: string;
  confirmation: {
    confirmation_url: string;
  };
};

export type CreatePaymentRequest = {
  value: string;
  description: string;
};
