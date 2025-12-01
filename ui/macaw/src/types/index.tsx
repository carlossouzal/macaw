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

type ResponseList<T> = {
  total: number;
  page: number;
  size: number;
  items: T[];
};

export type { Semester, ResponseList, Teacher, Specialization, RoomType, Student };