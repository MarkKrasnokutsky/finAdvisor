import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import Home from "@/pages/Home";
import Login from "@/pages/auth/Login";
import AuthLayout from "@/layouts/AuthLayout";
import { PrivateRoute, PublicRoute } from "@/components";
import { DashboardLayout } from "@/layouts/DashboardLayout";
// import Signals from "@/pages/Signals";
import { FilterToolProvider } from "@/context/FilterToolContext";
// import Tools from "@/pages/Tools";
// import Tariff from "@/pages/Tariff";
import React, { Suspense } from "react";
import Registration from "@/pages/auth/Registration";
import RequestCode from "@/pages/auth/RequestCode";
import ForgotPassword from "@/pages/auth/ForgotPassword";

const Signals = React.lazy(() => import("@/pages/Signals"));
const Tools = React.lazy(() => import("@/pages/Tools"));
const Tariff = React.lazy(() => import("@/pages/Tariff"));

const router = createBrowserRouter([
  {
    path: "/dashboard",
    element: <App />,
    errorElement: <h1>Ошибка</h1>,
    children: [
      {
        path: "",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Home />
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "signals",
        element: (
          <FilterToolProvider>
            <PrivateRoute>
              <DashboardLayout>
                <Suspense fallback={<div>Загрузка страницы Сигналов</div>}>
                  <Signals />
                </Suspense>
              </DashboardLayout>
            </PrivateRoute>
          </FilterToolProvider>
        ),
      },
      {
        path: "tools",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Suspense fallback={<div>Загрузка страницы инструментов</div>}>
                <Tools />
              </Suspense>
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "tariffs",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Suspense fallback={<div>Загрузка страницы Тарифов</div>}>
                <Tariff />
              </Suspense>
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "help",
        element: (
          <PrivateRoute>
            <DashboardLayout>{/* <Home /> */}</DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "login",
        element: (
          <PublicRoute>
            <AuthLayout>
              <Login />
            </AuthLayout>
          </PublicRoute>
        ),
      },
      {
        path: "register",
        element: (
          <PublicRoute>
            <AuthLayout>
              <Registration />
            </AuthLayout>
          </PublicRoute>
        ),
      },
      {
        path: "request-code",
        element: (
          <PublicRoute>
            <AuthLayout>
              <RequestCode />
            </AuthLayout>
          </PublicRoute>
        ),
      },
      {
        path: "forgot-password",
        element: (
          <PublicRoute>
            <AuthLayout>
              <ForgotPassword />
            </AuthLayout>
          </PublicRoute>
        ),
      },
    ],
  },
]);

export default router;
