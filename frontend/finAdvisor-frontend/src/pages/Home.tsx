import { useMe } from "@/api/hooks/useAuth";

const Home = () => {
  const { isLoading } = useMe();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return <div>Home</div>;
};

export default Home;
