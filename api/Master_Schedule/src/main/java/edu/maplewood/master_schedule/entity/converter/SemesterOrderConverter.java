package edu.maplewood.master_schedule.entity.converter;

import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SemesterOrderConverter implements AttributeConverter<OrderInYear, Integer> {

  @Override
  public Integer convertToDatabaseColumn(OrderInYear orderInYear) {
    if (orderInYear == null) {
      return null;
    }

    return orderInYear.getOrder();
  }

  @Override
  public OrderInYear convertToEntityAttribute(Integer orderInYear) {
    if (orderInYear == null) {
      return null;
    }

    for (OrderInYear order : OrderInYear.values()) {
      if (order.getOrder() == orderInYear) {
        return order;
      }
    }

    throw new IllegalArgumentException("Unknown order in year: " + orderInYear);
  }
}
