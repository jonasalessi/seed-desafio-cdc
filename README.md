# About

Challenge from the "Dev Eficiente" course. Implementing an online bookstore.

## CI/CD Status

[![Build and Test](https://github.com/jonasalessi/seed-desafio-cdc/actions/workflows/build-and-test.yml/badge.svg?branch=master)](https://github.com/jonasalessi/seed-desafio-cdc/actions/workflows/build-and-test.yml)
[![codecov](https://codecov.io/gh/jonasalessi/seed-desafio-cdc/branch/master/graph/badge.svg)](https://codecov.io/gh/jonasalessi/seed-desafio-cdc)

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

# Requirement 4

Needs:

- A title
- A summary of what will be found in the book
- A free-size table of contents. The text should be in Markdown format, which is a string. That way it can be formatted later
  appropriately.
- Book price
- Number of pages
- ISBN (book identifier)
- The date it should go live (publication date)
- A book belongs to a category
- A book has an author

Constraints:

- Title is required
- Title is unique
- Summary is required and has a maximum of 500 characters
- Table of contents is free-size
- Price is required and the minimum is 20
- Number of pages is required and the minimum is 100
- ISBN is required, format is free
- ISBN is unique
- The release date must be in the future
- Category cannot be null
- Author cannot be null

Expected result:

- A new book must be created and status 200 returned
- If any constraint is not met, return 400 and a JSON informing the validation problems

# Requirement 5
Needs: 
- List all books registered in the system
- The JSON must contain the fields: id and name