package edu.maplewood.master_schedule.config;

public final class Constants {

  private Constants() {
  }

  public static final String API = "/api";
  public static final String V1 = API + "/v1";

  // resource paths
  public static final String SEMESTERS = V1 + "/semesters";
  public static final String STUDENTS = V1 + "/students";
  public static final String SCHEDULES = V1 + "/schedules";
  public static final String TEACHERS = V1 + "/teachers";
  public static final String CLASSROOM = V1 + "/classrooms";
  public static final String COURSE = V1 + "/courses";
  public static final String COURSE_SECTION = V1 + "/course-sections";

  public static final String DEFAULT_SORT_BY = "id";
  public static final String DEFAULT_SORT_DIRECTION = "ASC";
  public static final Integer DEFAULT_PAGE_SIZE = 20;
  public static final Integer DEFAULT_PAGE_NUMBER = 0;
}
