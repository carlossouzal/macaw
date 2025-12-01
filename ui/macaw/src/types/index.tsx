type Semester = {
  id: string;
  name: string;
  year: number;
  order_in_year: "FALL" | "SPRING";
  start_date: string;
  end_date: string;
  is_active: boolean;
};

type Teacher = {
  id: string;
  first_name: string;
  last_name: string;
  email: string;
  max_daily_hours: number;
  created_at: string;
  specialization: Specialization | undefined;
};

type Specialization = {
    id: string;
    name: string;
    description: string;
    room_type: RoomType | undefined;
};

type RoomType = {
    id: string;
    name: string;
    description: string;
};

type Student = {
  id: number;
  first_name: string;
  last_name: string;
  email: string;
  grade_level: number;
  enrollment_year: number;
  expected_graduation_year: number;
  status: "ACTIVE" | "INACTIVE";
  created_at: string;
  gpa: number;
};

type Classroom = {
  id: number;
  name: string;
  capacity: number;
  equipment: string;
  floor: number;
  created_at: string;
  room_type: RoomType;
}

type Course = {
  id: number;
  code: string;
  name: string;
  description: string;
  credits: number;
  hours_per_week: number;
  course_type: "CORE" | "ELECTIVE";
  grade_level_min: number;
  grade_level_max: number;
  semester_order: "FALL" | "SPRING";
  created_at: string;
  specialization: Specialization | null;
  prerequisite: Course | null;
};

type ScheduleAssignment = {
  id: number;
  classroom: Classroom;
  day_of_week: "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY" | "SUNDAY";
  time_slot: number;
  is_active: boolean;
};

type CourseSection = {
  id: number;
  code: string;
  created_at: string;
  course: Course | null;
  semester: Semester | null;
  teacher: Teacher | null;
  schedule_assignments: ScheduleAssignment[] | null;
  students: Student[] | null;
};

type ResponseList<T> = {
  total: number;
  page: number;
  size: number;
  items: T[];
};

export type { Semester, ResponseList, Teacher, Specialization, RoomType, Student, Classroom, Course, CourseSection, ScheduleAssignment };