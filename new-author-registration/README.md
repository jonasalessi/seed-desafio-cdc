# About

Small challenge from the "Dev Eficiente" course.

# Requirement 1

It is required to register a new author in the system. Each author has a name, an email and a description. We also want to
record the exact timestamp when they were registered.

Constraints:

- The timestamp must not be null
- The email is required
- The email must have a valid format
- The name is required
- The description is required and must not exceed 400 characters

## Expected Result

A new author is created and HTTP status 200 is returned

# Requirement 2

- The email must be unique

# Requirement 3

## Registering a category

Every category needs a name.

Constraints:

- The name is required.
- The name cannot be duplicated.

### Expected result

- A new category has been registered in the system and status 200 has been returned.
- If any restriction is not met, return a 400 error and a JSON object indicating the validation problems.
