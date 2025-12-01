## How to run

### From project base directory

API(api/Master_Schedule):
```
docker build -f docker/Dockerfile -t macaw-api:latest .
```

UI (ui/macaw):
```
docker build -f docker/Dockerfile -t macaw-ui:latest .
```

### Run container

```
docker run --rm -p 8080:8080 \
 -e SPRING_PROFILES_ACTIVE=default \
 macaw-[ui|api]:latest
```

#### Docker compose:

At the base path, run:
```
docker compose up
```

to open in a browser:
- Chrome: click the address bar and explicitly type http://localhost:8080
- Firefox: Preferences → Privacy & Security → HTTPS-Only Mode → disable (or add an exception for localhost). You can also clear HSTS for localhost: about:config → search “hsts” and clear site data for localhost.
- Safari: explicitly type http://localhost:8080 and ensure no HTTPS-only extensions are forcing HTTPS.

## Solution

- What we have:
    - An initial setup.
    - Entities and relationships defined.
    - Basic services created.
    - Repositories for data access.
    - Specifications for querying
    - Basic controller
    - Basic ControllerAdvice
    - Auxiliary:
        - DTOs
        - Mappers
        - Validators

The main idea here, were to provide a solid based on enterprise standards,
so that the rest of the implementation could be easily achieved by following the same patterns.

So the Idea were to create ORM entities, which with the specifications could be easily queried.
Then the services would provide the business logic, and the controllers would expose the endpoints.

The auxiliary classes would help with data transfer, mapping and validation.

- Missing Some Unit Tests and Integration Tests.
- Missing complete CRUD operations for all entities.
- Missing complete validation logic.
- Missing complete error handling.
- Missing documentation.