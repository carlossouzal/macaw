package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.maplewood.master_schedule.controller.dto.request.CreateSemesterRequest;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponse;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponseList;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class SemesterMapperTest {

  private Semester createSemester(Long id) {
    Semester s = new Semester();
    s.setId(id);
    s.setName("Fall");
    s.setYear(2025);
    s.setOrderInYear(OrderInYear.FALL);
    s.setStartDate(LocalDate.of(2025, 9, 1));
    s.setEndDate(LocalDate.of(2025, 12, 20));
    s.setIsActive(true);
    s.setCreatedAt(LocalDateTime.of(2025, 9, 1, 0, 0, 0));
    return s;
  }

  @Test
  void toResponseSingleSemesterMapsAllFields() {
    Semester semester = createSemester(1L);

    SemesterResponse dto = SemesterMapper.toResponse(semester);

    assertEquals(1L, dto.id());
    assertEquals("Fall", dto.name());
    assertEquals(2025, dto.year());
    assertEquals(OrderInYear.FALL, dto.orderInYear());
    assertEquals(LocalDate.of(2025, 9, 1), dto.startDate());
    assertEquals(LocalDate.of(2025, 12, 20), dto.endDate());
    assertTrue(dto.isActive());
    assertEquals(LocalDateTime.of(2025, 9, 1, 0, 0, 0), dto.createdAt());
  }

  @Test
  void toResponseListMapsEachItem() {
    List<Semester> entities = List.of(createSemester(1L), createSemester(2L));

    List<SemesterResponse> dtos = SemesterMapper.toResponse(entities);

    assertEquals(2, dtos.size());
    assertEquals(1L, dtos.get(0).id());
    assertEquals(2L, dtos.get(1).id());
    assertEquals("Fall", dtos.get(0).name());
    assertEquals(OrderInYear.FALL, dtos.get(0).orderInYear());
  }

  @Test
  void toResponsePageMapsContentAndPageMetadata() {
    List<Semester> content = List.of(createSemester(10L), createSemester(11L), createSemester(12L));
    PageRequest pageable = PageRequest.of(1, 3); // page index 1, size 3
    Page<Semester> page = new PageImpl<>(content, pageable, 9); // total elements 9

    SemesterResponseList responseList = SemesterMapper.toResponse(page);

    assertEquals(9, responseList.total());
    assertEquals(1, responseList.page());
    assertEquals(3, responseList.size());
    assertEquals(3, responseList.semesters().size());
    assertEquals(10L, responseList.semesters().get(0).id());
    assertEquals(12L, responseList.semesters().get(2).id());
  }

  @Test
  void toEntityMapsRequestToEntityWithDatesPresent() {
    CreateSemesterRequest req = new CreateSemesterRequest(
        "Spring",
        2026,
        "FALL",
        Optional.of(LocalDate.of(2026, 1, 15)),
        Optional.of(LocalDate.of(2026, 5, 30))
    );

    Semester entity = SemesterMapper.toEntity(req);

    assertNull(entity.getId());
    assertEquals("Spring", entity.getName());
    assertEquals(2026, entity.getYear());
    assertEquals(OrderInYear.FALL, entity.getOrderInYear());
    assertEquals(LocalDate.of(2026, 1, 15), entity.getStartDate());
    assertEquals(LocalDate.of(2026, 5, 30), entity.getEndDate());
  }

  @Test
  void toEntityHandlesEmptyOptionalDates_asNulls() {
    CreateSemesterRequest req = new CreateSemesterRequest(
        "Summer",
        2026,
        "FALL",
        Optional.empty(),
        Optional.empty()
    );

    Semester entity = SemesterMapper.toEntity(req);

    assertNull(entity.getStartDate());
    assertNull(entity.getEndDate());
  }

  @Test
  void toEntityThrowsForInvalidOrderInYear() {
    CreateSemesterRequest req = new CreateSemesterRequest(
        "Invalid",
        2026,
        "NOT_A_VALID_ENUM",
        Optional.empty(),
        Optional.empty()
    );

    assertThrows(IllegalArgumentException.class, () -> SemesterMapper.toEntity(req));
  }
}
