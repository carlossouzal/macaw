package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import edu.maplewood.master_schedule.repository.SemesterRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SemesterServiceTest {

  private SemesterRepository repository;
  private SemesterService service;

  @BeforeEach
  public void setUp() {
    repository = mock(SemesterRepository.class);
    service = new SemesterService(repository);
  }

  @Test
  public void testFindAll() {
    Semester semester = createSemester();
    when(repository.findAll()).thenReturn(List.of(semester));

    List<Semester> semesters = service.findAll();

    verify(repository).findAll();

    assertThat(semesters).containsExactly(semester);
  }

  public static Semester createSemester() {
    Semester semester = new Semester();
    semester.setId(1L);
    semester.setName("Fall");
    semester.setYear(2018);
    semester.setOrderInYear(OrderInYear.FALL);
    semester.setIsActive(true);
    return semester;
  }
}
