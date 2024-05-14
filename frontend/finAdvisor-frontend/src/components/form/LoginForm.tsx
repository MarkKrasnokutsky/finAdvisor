import { useForm } from "react-hook-form";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormMessage,
} from "../ui/form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Link } from "react-router-dom";
// import { useLogin } from "@/api/hooks/useAuth";
// import { useState } from "react";
// import { InputDataError, InputDataErrorState } from "@/types";

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
    }),

  password: z
    .string()
    .min(1, {
      message: "Нужно ввести пароль.",
    })
    .min(8, { message: "Пароль должен состоять минимум из 8 символов." })
    .max(40, {
      message: "Пароль должен состоять максимум из 40 символов.",
    }),
});

const LoginForm = () => {
  //   const [errorBackend, setErrorBackend] = useState<InputDataErrorState>({
  //     status: 0,
  //     message: "",
  //   });
  //   const loginMutation = useLogin();

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    console.log(data);
    // try {
    //   await loginMutation.mutateAsync(data);
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
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    isPassword={true}
                    placeholder="Введите ваш пароль"
                    // isPassword={true}
                    {...field}
                  />
                </FormControl>
                <FormMessage>
                  {/* {errorBackend.status === 401 && errorBackend.message} */}
                </FormMessage>

                <Link to={"/forgot-password"}>
                  <FormDescription>Восстановите пароль</FormDescription>
                </Link>
              </FormItem>
            )}
          />
          <Button
            className="w-full h-14 bg-accentMain text-lg rounded-xl hover:bg-accentMainHover"
            type="submit"
          >
            Войти
          </Button>
        </div>
        <p className="text-center text-white mt-2 max-550:text-sm">
          Нету аккаунта?{" "}
          <Link
            to="/register"
            className="text-accentMain transition-all hover:text-accentMainHover hover:underline"
          >
            Зарегистрируйтесь
          </Link>
        </p>
      </form>
    </Form>
  );
};

export default LoginForm;
