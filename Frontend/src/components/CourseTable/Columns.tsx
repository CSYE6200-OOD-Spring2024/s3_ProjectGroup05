import { ColumnDef } from "@tanstack/react-table";
import { courseTable } from "@/types/courseTable";
import { Checkbox } from "../ui/checkbox";
import { getUser } from "@/store/AuthStore";
import { Role } from "@/lib/role";

const user = getUser();

export const columns: ColumnDef<courseTable>[] = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() ||
          (table.getIsSomePageRowsSelected() && "indeterminate")
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
  },
  {
    accessorKey: "title",
    header: "Title",
  },
  {
    accessorKey: "crn",
    header: "CRN",
  },
  {
    accessorKey: "semester",
    header: "Semester",
  },
  {
    accessorKey: "hours",
    header: "Hours",
  },
  {
    accessorKey: `${user.role === Role.ADMIN ? "seats" : "enrolled"}`,
    header: `${user.role === Role.ADMIN ? "Seats" : "Enrolled"}`,
    cell: ({ row }) => {
      const { enrolled, seats } = row.original;

      if (user.role === Role.ADMIN) {
        return (
          <span>{seats}</span>
        )
      }
      else {
        return (
            <div className={`flex ${seats - enrolled < 20 ? "text-red-500" : "text-green-500"}`}>
              <span>{enrolled}</span>
              <span className="mx-1"> of </span>
              <span>{seats}</span>
            </div>
        )
      }
    }
  },
  {
    accessorKey: "instructor",
    header: "Instructor",
  },
  {
    accessorKey: "time",
    header: "Day & Time",
  },
] 