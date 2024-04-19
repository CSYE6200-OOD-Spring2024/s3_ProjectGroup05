import { BookCopy } from "lucide-react";
import { ModeToggle } from "./ModeToggle";
import User from "./User";

export default function Navbar() {
  return (
    <nav className="h-16 border-b-2 sticky top-0 backdrop-blur">
      <div className="h-full flex items-center justify-between w-full p-6">
        <div>
          <BookCopy size={32} />
        </div>
        <div className="flex items-center space-x-2">
          <ModeToggle />
          <User /> 
        </div>
      </div>
    </nav>
  )
}
