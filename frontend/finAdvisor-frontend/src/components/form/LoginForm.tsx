import { useState } from "react";

import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link } from "react-router-dom";

import { useLogin } from "@/api/hooks/useAuth";
import { ErrorsState, ResponseErrors } from "@/types/auth";

import {
  Button,
  Input,
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormMessage,
} from "@/components";

const FormSchema = z.object({
  username: z
    .string()
    .min(1, {
      message: "Нужно заполнить поле.",
    })
    .min(4, {
      message: "Имя пользователя должно состоять минимум из 4 символов.",
    })
    .max(40, {
      message: "Имя пользователя должно состоять максимум из 40 символов.",
    })
    .regex(/^[a-zA-Z0-9_]{4,40}$/, { message: "Некорректно введены данные" }),

  password: z
    .string()
    .min(1, {
      message: "Нужно ввести пароль.",
    })
    .min(8, { message: "Пароль должен состоять минимум из 8 символов." })
    .max(40, {
      message: "Пароль должен состоять максимум из 40 символов.",
    })
    .regex(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$/, {
      message:
        "Пароль пользователя должен быть от 8 до 40 символов. В пароле можно использовать цифры, символы и буквы латинского алфавита. При этом обязательно в пароле должна быть хотя бы одна цифра, одна буква в нижнем регистре и одна буква в верхнем регистре.",
    }),
});

export const LoginForm = () => {
  const [errorBackend, setErrorBackend] = useState<ErrorsState>({
    data: "",
    status: 0,
  });
  const loginMutation = useLogin();

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    try {
      await loginMutation.mutateAsync(data);
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
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Введите имя пользователя" {...field} />
                </FormControl>

                <FormMessage>
                  {errorBackend.status === 401 && errorBackend.data}
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
                {/* SECRET RED FLAG 4/4 */}
                {/* <Link to={"/forgot-password"}>
                  <FormDescription>Восстановите пароль</FormDescription>
                </Link> */}
              </FormItem>
            )}
          />
          <Button
            className="w-full h-14 bg-accent text-lg rounded-xl hover:bg-accent-hover"
            type="submit"
          >
            Войти
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
