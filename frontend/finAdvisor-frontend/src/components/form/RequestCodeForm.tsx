import { useState } from "react";

import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link } from "react-router-dom";

import { useSendCode } from "@/api/hooks/useAuth";
import { ErrorsState, ResponseErrors } from "@/types/auth";

import {
  Button,
  Input,
  Form,
  FormControl,
  //   FormDescription,
  FormField,
  FormItem,
  FormMessage,
} from "@/components";

const FormSchema = z.object({
  email: z.string().email({
    message: "Пожалуйста, введите корректный адрес электронной почты.",
  }),
});

export const RequestCodeForm = () => {
  const [errorBackend, setErrorBackend] = useState<ErrorsState>({
    data: "",
    status: 0,
  });
  const sendCodeMutation = useSendCode();

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      email: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    console.log("data: ", data);
    try {
      await sendCodeMutation.mutateAsync(data);
    } catch (error: unknown) {
      const customError = error as ResponseErrors;
      setErrorBackend({
        data: customError.response?.data,
        status: customError.response?.status,
      });
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="w-full max-700:w-full"
      >
        <div className="space-y-6">
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Введите электронную почту" {...field} />
                </FormControl>

                <FormMessage>
                  {errorBackend.status && errorBackend.data}
                </FormMessage>
              </FormItem>
            )}
          />
          <Button
            className="w-full h-14 bg-accent text-lg rounded-xl hover:bg-accent-hover"
            type="submit"
          >
            Отправить
          </Button>
        </div>
        <p className="text-center text-white mt-2 max-550:text-sm">
          Нету аккаунта?{" "}
          <Link
            to="/dashboard/register"
            className="text-accent transition-all hover:text-accent-hover hover:underline"
          >
            Зарегистрируйтесь
          </Link>
        </p>
      </form>
    </Form>
  );
};
