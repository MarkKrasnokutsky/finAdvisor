import { createBrowserRouter } from "react-router-dom";

import App from "@/App";
import Home from "@/pages/Home";
import Registration from "@/pages/auth/Registration";
import Login from "@/pages/auth/Login";
import AuthLayout from "@/layouts/AuthLayout";
import { PrivateRoute, PublicRoute } from "@/components";

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
            <Home />
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
