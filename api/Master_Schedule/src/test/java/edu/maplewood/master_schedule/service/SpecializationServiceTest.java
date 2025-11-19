package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.repository.SpecializationRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpecializationServiceTest {

  private SpecializationRepository repository;
  private SpecializationService service;

  @BeforeEach
  public void setUp() {
    repository = mock(SpecializationRepository.class);
    service = new SpecializationService(repository);
  }

  @Test
  public void testFindAll() {
    Specialization specialization = createSpecialization();
    when(repository.findAll()).thenReturn(List.of(specialization));

    List<Specialization> specializations = service.findAll();

    verify(repository).findAll();

    assertThat(specializations).containsExactly(specialization);
  }

  public static Specialization createSpecialization() {
    Specialization specialization = new Specialization();
    specialization.setId(1L);
    specialization.setName("Computer Science");
    return specialization;
  }
}
