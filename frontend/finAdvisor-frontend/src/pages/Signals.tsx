import { useTitle } from "@/api/hooks/useContext";

const Signals: React.FC = () => {
  useTitle("Список сигналов");
  return <div className="w-full">Signals</div>;
};

export default Signals;
