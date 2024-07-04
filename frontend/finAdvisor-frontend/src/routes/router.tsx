import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import Home from "@/pages/Home";
import Registration from "@/pages/auth/Registration";
import Login from "@/pages/auth/Login";
import AuthLayout from "@/layouts/AuthLayout";
import { PrivateRoute, PublicRoute } from "@/components";
import { DashboardLayout } from "@/layouts/DashboardLayout";
import Signals from "@/pages/Signals";
import { AuthProvider } from "@/context/AuthContext";

const router = createBrowserRouter([
  {
    path: "/dashboard",
    element: <App />,
    errorElement: <h1>Ошибка</h1>,
    children: [
      {
        path: "",
        element: (
          <AuthProvider>
            <PrivateRoute>
              <DashboardLayout>
                <Home />
              </DashboardLayout>
            </PrivateRoute>
          </AuthProvider>
        ),
      },
      {
        path: "signals",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Signals />
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "tools",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Home />
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "tariffs",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Home />
            </DashboardLayout>
          </PrivateRoute>
        ),
      },
      {
        path: "help",
        element: (
          <PrivateRoute>
            <DashboardLayout>
              <Home />
            </DashboardLayout>
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
