import { useForm } from "react-hook-form";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "../ui/form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Link } from "react-router-dom";
// import { useRegister } from "@/api/hooks/useAuth";
// import { useState } from "react";
// import { InputDataError, InputDataErrorState } from "@/types";

const FormSchema = z
  .object({
    username: z.string().regex(/^[a-zA-Z0-9_]{4,40}$/, {
      message:
        "Имя пользователя должно состоять только из латинских букв, цифр и знака подчеркивания.",
    }),

    email: z
      .string()

      .email({
        message: "Пожалуйста, введите корректный адрес электронной почты.",
      }),
    password: z
      .string()
      .min(1, {
        message: "Нужно ввести пароль.",
      })
      .min(8, { message: "Пароль должен состоять минимум из 8 символов." })
      .max(40, {
        message: "Пароль должен состоять максимум из 40 символов.",
      })
      .regex(/^[a-zA-Z0-9_]{4,40}$/, {
        message:
          "Пароль должен состоять только из латинских букв, цифр и знака подчеркивания.",
      }),
    confirmPassword: z
      .string()
      .min(1, {
        message: "Нужно подтвердить пароль.",
      })
      .min(8, { message: "Пароль должен состоять минимум из 8 символов." })
      .max(40, {
        message: "Пароль должен состоять максимум из 40 символов.",
      }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Пароли не совпадают.",
  });

const RegistrationForm = () => {
  //   const [errorBackend, setErrorBackend] = useState<InputDataErrorState>({
  //     status: 0,
  //     message: "",
  //   });
  //   const registerMutation = useRegister();
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
    console.log(data);
    // try {
    //   await registerMutation.mutateAsync(data);
    // } catch (error: unknown) {
    //   const customError = error as InputDataError;
    //   setErrorBackend({
    //     status: customError.response?.status,
    //     message: customError.response?.data?.message,
    //   });
    // }
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
                  {/* {errorBackend.status === 409 && errorBackend.message} */}
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
                  {/* {errorBackend.status === 409 && errorBackend.message} */}
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

                <FormMessage>
                  {/* {errorBackend.status === 409 && errorBackend.message} */}
                </FormMessage>
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
                    // isPassword={true}
                    {...field}
                  />
                </FormControl>
                <FormMessage>
                  {/* {errorBackend.status === 401 && errorBackend.message} */}
                </FormMessage>
              </FormItem>
            )}
          />
          <Button
            className="w-full h-14 bg-accentMain text-lg rounded-xl hover:bg-accentMainHover"
            type="submit"
          >
            Создать
          </Button>
        </div>
        <p className="text-center text-white mt-2 max-550:text-sm">
          Есть аккаунт?{" "}
          <Link
            to="/login"
            className="text-accentMain transition-all hover:text-accentMainHover hover:underline"
          >
            Войдите
          </Link>
        </p>
      </form>
    </Form>
  );
};

export default RegistrationForm;
