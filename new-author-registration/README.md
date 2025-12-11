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

# Expected Result

A new author is created and HTTP status 200 is returned

# Requirement 2

- The email must be unique
