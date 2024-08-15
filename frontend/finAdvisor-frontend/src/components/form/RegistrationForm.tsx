import { useState } from "react";

import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link } from "react-router-dom";

import { ErrorsState, ResponseErrors } from "@/types/auth";
import { useRegistration } from "@/api/hooks/useAuth";

import {
  Button,
  Input,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components";

const FormSchema = z
  .object({
    username: z.string().regex(/^[a-zA-Z0-9_]{4,40}$/, {
      message:
        "Имя пользователя должно состоять только из латинских букв, цифр и знака подчеркивания.",
    }),

    email: z.string().email({
      message: "Пожалуйста, введите корректный адрес электронной почты.",
    }),
    password: z
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
  .refine((data) => data.password === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Пароли не совпадают.",
  });

export const RegistrationForm = () => {
  const [errorBackend, setErrorBackend] = useState<ErrorsState>({
    status: 0,
    data: "",
  });

  const registerMutation = useRegistration();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    try {
      await registerMutation.mutateAsync(data);
    } catch (error: unknown) {
      const customError = error as ResponseErrors;
      setErrorBackend({
        status: customError.response?.status,
        data: customError.response?.data,
      });
    }
  };

  const handleOpenLegal = (name: string) => {
    window.open(
      `https://s3.timeweb.cloud/432b8bc2-cde0d2b0-8512-478d-a65f-555f9e22470f/legal/${name}.pdf`,
      "_blank"
    );
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
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Введите имя пользователя" {...field} />
                </FormControl>

                <FormMessage>
                  {errorBackend.status === 400 && errorBackend.data}
                </FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Введите электронную почту" {...field} />
                </FormControl>

                <FormMessage>
                  {errorBackend.status === 400 && errorBackend.data}
                </FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
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
          <Button
            className="w-full h-14 bg-accent text-lg rounded-xl hover:bg-accent-hover"
            type="submit"
          >
            Создать
          </Button>
        </div>
        <p className="text-white text-center text-balance mt-2  max-550:text-sm">
          Продолжая, вы соглашаетесь с{" "}
          <a
            className="underline cursor-pointer"
            onClick={() => handleOpenLegal("personal_data")}
          >
            обработкой персональных данных
          </a>
          ,{" "}
          <a
            className="underline cursor-pointer"
            onClick={() => handleOpenLegal("useragreement")}
          >
            пользовательским соглашением
          </a>{" "}
          и{" "}
          <a
            className="underline cursor-pointer"
            onClick={() => handleOpenLegal("risks")}
          >
            рисками
          </a>
        </p>
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
