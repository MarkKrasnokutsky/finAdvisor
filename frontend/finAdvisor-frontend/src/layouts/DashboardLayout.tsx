import { Header, Sidebar } from "@/components";
import { TitleProvider } from "@/context/TitleContext";
import { useWindowWidth } from "@react-hook/window-size";

export const DashboardLayout: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  const onlyWidth = useWindowWidth();

  return (
    <TitleProvider>
      <div className="h-screen w-full flex bg-background font-body dark:bg-background-dark ">
        {onlyWidth > 1000 && <Sidebar />}
        <div
          className={`flex flex-col w-full pl-5 ${
            onlyWidth > 1000 ? "pr-10" : "pr-5"
          } pb-12`}
        >
          <Header />
          <div className=" h-full overflow-y-auto">{children}</div>
        </div>
      </div>
    </TitleProvider>
  );
};
