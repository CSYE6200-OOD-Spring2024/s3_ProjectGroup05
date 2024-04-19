export type course = {
  title: string;
  semester: string;
  hours: number;
  enrollment: number;
  seats: number;
  instructor: string;
  classTiming: {
    startTime: string;
    endTime: string;
    day: string;
  }
  crn: number;
}