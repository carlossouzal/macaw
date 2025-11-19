package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.Student.StudentStatus;
import jakarta.persistence.Converter;

@Converter
public class StudentStatusConverter extends LowercaseEnumConverter<StudentStatus> {

  public StudentStatusConverter() {
    super(StudentStatus.class);
  }
}
