import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import Home from "./pages/Home.tsx";
import AuthLayout from "./layouts/AuthLayout.tsx";
import Login from "./pages/auth/Login.tsx";
import Registration from "./pages/auth/Registration.tsx";

const queryClient = new QueryClient();

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <h1>Ошибка</h1>,
    children: [
      {
        path: "/",
        element: <Home />,
      },

      {
        path: "login",
        element: (
          <AuthLayout>
            <Login />
          </AuthLayout>
        ),
      },
      {
        path: "register",
        element: (
          <AuthLayout>
            <Registration />
          </AuthLayout>
        ),
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </React.StrictMode>
);
