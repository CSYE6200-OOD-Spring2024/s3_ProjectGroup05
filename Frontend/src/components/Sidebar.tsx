import { useUser } from "@/store/AuthStore";
import { Link } from "react-router-dom";
import { Button } from "./ui/button";

export default function Sidebar() {

  const user = useUser();

  return (
    <div className="w-52 h-full flex flex-col p-4 space-y-2 border-r-2">
      <Link to="/">
        <Button variant={'ghost'} className="w-full justify-start">
          Courese Catalog
        </Button>
      </Link>
      <Link to={`/${user.name}`}>
        <Button variant={'ghost'} className="w-full justify-start">
          Your Courses
        </Button>
      </Link>
    </div>
  )
}
