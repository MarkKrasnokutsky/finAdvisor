import { useState } from "react";

import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link } from "react-router-dom";

import { ErrorsState, ResponseErrors } from "@/types/auth";
import { useResetPassword } from "@/api/hooks/useAuth";

import {
  Button,
  Input,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
  FormDescription,
} from "@/components";

const FormSchema = z
  .object({
    email: z.string().email({
      message: "Пожалуйста, введите корректный адрес электронной почты.",
    }),
    code: z.string().min(1, {
      message: "Нужно заполнить поле.",
    }),
    newPassword: z
      .string()
      .regex(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$/, {
        message:
          "Пароль пользователя должен быть от 8 до 40 символов. В пароле можно использовать цифры, символы и буквы латинского алфавита. При этом обязательно в пароле должна быть хотя бы одна цифра, одна буква в нижнем регистре и одна буква в верхнем регистре.",
      })
      .min(1, {
        message: "Нужно подтвердить пароль.",
      })
      .min(8, { message: "Пароль должен состоять минимум из 8 символов." })
      .max(40, {
        message: "Пароль должен состоять максимум из 40 символов.",
      }),

    confirmPassword: z.string().min(1, {
      message: "Нужно подтвердить пароль.",
    }),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Пароли не совпадают.",
  });

export const ForgotPasswordForm = () => {
  const [errorBackend, setErrorBackend] = useState<ErrorsState>({
    status: 0,
    data: "",
  });

  const resetPasswordMutation = useResetPassword();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      email: "",
      code: "",
      newPassword: "",
      confirmPassword: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    try {
      await resetPasswordMutation.mutateAsync(data);
    } catch (error: unknown) {
      const customError = error as ResponseErrors;
      setErrorBackend({
        status: customError.response?.status,
        data: customError.response?.data,
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
                  {errorBackend.status === 404 && errorBackend.data}
                </FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="newPassword"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    isPassword={true}
                    placeholder="Введите ваш пароль"
                    {...field}
                  />
                </FormControl>

                <FormMessage></FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    isPassword={true}
                    placeholder="Подтвердите пароль"
                    {...field}
                  />
                </FormControl>
                <FormMessage></FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="code"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    isPassword={false}
                    placeholder="Введите код подтверждения"
                    {...field}
                  />
                </FormControl>
                <FormMessage>
                  {errorBackend.status === 400 && errorBackend.data}
                </FormMessage>
                <FormDescription className="text-left  max-550:text-sm">
                  Код действует в течение 5 минут. После этого его нужно будет
                  перевыпустить.{" "}
                  <Link
                    to="/dashboard/request-code"
                    className="cursor-pointer not-italic text-accent transition-all hover:text-accent-hover hover:underline"
                  >
                    Отправить код заново.
                  </Link>
                </FormDescription>
              </FormItem>
            )}
          />
          <Button
            className="w-full h-14 bg-accent text-lg rounded-xl hover:bg-accent-hover"
            type="submit"
          >
            Восстановить
          </Button>
        </div>
        <p className="text-center text-white mt-2 max-550:text-sm">
          Есть аккаунт?{" "}
          <Link
            to="/dashboard/login"
            className="text-accent transition-all hover:text-accent-hover hover:underline"
          >
            Войдите
          </Link>
        </p>
      </form>
    </Form>
  );
};
