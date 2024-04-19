import { courseTable } from "@/types/courseTable"
import { columns } from "./Columns"
import DataTable from "./DataTable"

interface tableProps {
  data: courseTable[],
  from: string,
}

export default function Table({ data, from }: tableProps) {

  return (
    <div className="container mx-auto p-6 overflow-auto">
      <h1 className="text-5xl font-bold pb-4">{from ==='adminDashboard' ? "Admin Dashboard" : from === 'userDashboard'? "Register for Course" : "Your Courses"}</h1>
      <DataTable columns={columns} data={data} from={from}/>
    </div>
  )
}
