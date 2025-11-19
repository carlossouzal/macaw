package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.RoomTypeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomTypeServiceTest {

  private RoomTypeRepository repository;
  private RoomTypeService service;

  @BeforeEach
  public void setUp() {
    repository = mock(RoomTypeRepository.class);
    service = new RoomTypeService(repository);
  }

  @Test
  public void testFindAll() {
    RoomType roomType = createRoomType();
    when(repository.findAll()).thenReturn(List.of(roomType));

    List<RoomType> roomTypes = service.findAll();

    verify(repository).findAll();

    assertThat(roomTypes).containsExactly(roomType);
  }

  public static RoomType createRoomType() {
    RoomType roomType = new RoomType();
    roomType.setId(1L);
    roomType.setName("Art Studio");
    return roomType;
  }
}
