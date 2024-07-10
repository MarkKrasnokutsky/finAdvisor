import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import Home from "@/pages/Home";
import Registration from "@/pages/auth/Registration";
import Login from "@/pages/auth/Login";
import AuthLayout from "@/layouts/AuthLayout";
import { PrivateRoute, PublicRoute } from "@/components";
import { DashboardLayout } from "@/layouts/DashboardLayout";
import Signals from "@/pages/Signals";
import { FilterToolProvider } from "@/context/FilterToolContext";
import Tools from "@/pages/Tools";
import Tariff from "@/pages/Tariff";

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
                <Signals />
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
              <Tools />
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "tariffs",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Tariff />
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
    ],
  },
]);

export default router;
