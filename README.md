## How to run

### From project base directory

```
docker build -f docker/Dockerfile -t macaw:latest .
```

### Run container

```
docker run --rm -p 8080:8080 \
 -e SPRING_PROFILES_ACTIVE=default \
 macaw:latest
```

## Solution

- Didn't accomplish a complete solution for the problem.

- What we have:
    - An initial setup.
    - Entities and relationships defined.
    - Basic services created.
    - Repositories for data access.
    - Specifications for querying for:
        - Classroom
        - CourseSection
        - Semester
        - Student
        - Teacher
    - Basic controller for:
        - Student
        - Semester
        - Schedule
    - Basic ControllerAdvice dealing with 404.
    - Auxiliary:
        - DTOs
        - Mappers
        - Validators

The main idea here, were to provide a solid based on enterprise standards,
so that the rest of the implementation could be easily achieved by following the same patterns.

So the Idea were to create ORM entities, which with the specifications could be easily queried.
Then the services would provide the business logic, and the controllers would expose the endpoints.

The auxiliary classes would help with data transfer, mapping and validation.

The initial purpose were to provide a Schedule Master, but also a extensible CRUD and listing
methods,
to be presented in the future with a frontend application.

- Missing Unit Tests and Integration Tests.
- Missing complete CRUD operations for all entities.
- Missing complete validation logic.
- Missing complete error handling.
- Missing documentation.