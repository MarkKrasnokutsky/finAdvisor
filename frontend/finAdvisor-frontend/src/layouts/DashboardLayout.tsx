import { Header, Sidebar } from "@/components";
import { TitleProvider } from "@/context/TitleContext";
import { useWindowWidth } from "@react-hook/window-size";
import { Toaster, toast } from "sonner";

export const DashboardLayout: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  const onlyWidth = useWindowWidth();

  const isAcceptedCookie = localStorage.getItem("cookie");
  const handleAcceptCookie = () => {
    localStorage.setItem("cookie", "Accepted");
  };
  toast(
    <div className="flex text-[12px] w-max rounded-">
      <p className="">
        Мы используем куки, так как без них все работало бы плохо
      </p>
      <button
        className="text-nowrap border  rounded-[10px] px-2"
        onClick={() => (handleAcceptCookie(), toast.dismiss())}
      >
        Принять
      </button>
    </div>,
    { duration: Infinity }
  );

  return (
    <>
      {!isAcceptedCookie && (
        <Toaster position="bottom-center" visibleToasts={1} />
      )}
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
    </>
  );
};
