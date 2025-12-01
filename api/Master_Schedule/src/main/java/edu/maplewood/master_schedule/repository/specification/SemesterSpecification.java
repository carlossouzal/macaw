package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import jakarta.persistence.criteria.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class SemesterSpecification {

  private SemesterSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Semester> build(
      String name,
      Integer yearMin,
      Integer yearMax,
      OrderInYear orderInYear,
      LocalDate startDateBegin,
      LocalDate startDateEnd,
      LocalDate endDateBegin,
      LocalDate endDateEnd,
      Boolean isActive,
      LocalDateTime createdAtBegin,
      LocalDateTime createdAtEnd) {

    List<Specification<Semester>> specs = new ArrayList<>();
    specs.add(nameContains(name));
    specs.add(yearBetween(yearMin, yearMax));
    specs.add(orderInYearEquals(orderInYear));
    specs.add(startDateBetween(startDateBegin, startDateEnd));
    specs.add(endDateBetween(endDateBegin, endDateEnd));
    specs.add(isActiveEquals(isActive));
    specs.add(createdAtBetween(createdAtBegin, createdAtEnd));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<Semester> nameContains(String name) {
    if (!StringUtils.hasText(name)) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  private static Specification<Semester> yearBetween(Integer min, Integer max) {
    if (min == null && max == null) {
      return null;
    }
    return (root, query, cb) -> {
      Path<Integer> year = root.get("year");
      if (min != null && max != null) {
        return cb.between(year, min, max);
      } else if (min != null) {
        return cb.greaterThanOrEqualTo(year, min);
      } else {
        return cb.lessThanOrEqualTo(year, max);
      }
    };
  }

  private static Specification<Semester> orderInYearEquals(OrderInYear order) {
    if (order == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("orderInYear"), order.getOrder());
  }

  private static Specification<Semester> startDateBetween(LocalDate begin, LocalDate end) {
    if (begin == null && end == null) {
      return null;
    }
    return (root, query, cb) -> {
      Path<LocalDate> p = root.get("startDate");
      if (begin != null && end != null) {
        return cb.between(p, begin, end);
      }
      if (begin != null) {
        return cb.greaterThanOrEqualTo(p, begin);
      }
      return cb.lessThanOrEqualTo(p, end);
    };
  }

  private static Specification<Semester> endDateBetween(LocalDate begin, LocalDate end) {
    if (begin == null && end == null) {
      return null;
    }
    return (root, query, cb) -> {
      Path<LocalDate> p = root.get("endDate");
      if (begin != null && end != null) {
        return cb.between(p, begin, end);
      }
      if (begin != null) {
        return cb.greaterThanOrEqualTo(p, begin);
      }
      return cb.lessThanOrEqualTo(p, end);
    };
  }

  private static Specification<Semester> isActiveEquals(Boolean active) {
    if (active == null) {
      return null;
    }
    return (root, query, cb) -> cb.equal(root.get("isActive"), active);
  }

  private static Specification<Semester> createdAtBetween(LocalDateTime begin, LocalDateTime end) {
    if (begin == null && end == null) {
      return null;
    }
    return (root, query, cb) -> {
      Path<LocalDateTime> p = root.get("createdAt");
      if (begin != null && end != null) {
        return cb.between(p, begin, end);
      }
      if (begin != null) {
        return cb.greaterThanOrEqualTo(p, begin);
      }
      return cb.lessThanOrEqualTo(p, end);
    };
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String name;
    private Integer yearMin;
    private Integer yearMax;
    private OrderInYear orderInYear;
    private LocalDate startDateBegin;
    private LocalDate startDateEnd;
    private LocalDate endDateBegin;
    private LocalDate endDateEnd;
    private Boolean isActive;
    private LocalDateTime createdAtBegin;
    private LocalDateTime createdAtEnd;

    private Builder() {
      
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder yearMin(Integer yearMin) {
      this.yearMin = yearMin;
      return this;
    }

    public Builder yearMax(Integer yearMax) {
      this.yearMax = yearMax;
      return this;
    }

    public Builder orderInYear(OrderInYear orderInYear) {
      this.orderInYear = orderInYear;
      return this;
    }

    public Builder startDateBegin(LocalDate startDateBegin) {
      this.startDateBegin = startDateBegin;
      return this;
    }

    public Builder startDateEnd(LocalDate startDateEnd) {
      this.startDateEnd = startDateEnd;
      return this;
    }

    public Builder endDateBegin(LocalDate endDateBegin) {
      this.endDateBegin = endDateBegin;
      return this;
    }

    public Builder endDateEnd(LocalDate endDateEnd) {
      this.endDateEnd = endDateEnd;
      return this;
    }

    public Builder isActive(Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public Builder createdAtBegin(LocalDateTime createdAtBegin) {
      this.createdAtBegin = createdAtBegin;
      return this;
    }

    public Builder createdAtEnd(LocalDateTime createdAtEnd) {
      this.createdAtEnd = createdAtEnd;
      return this;
    }
  }
}
