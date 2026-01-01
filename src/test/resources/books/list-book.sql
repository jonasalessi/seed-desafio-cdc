insert into categories (id, name)
values (1, 'Software Development');
insert into authors (id, name, email, description, created_at)
values (1, 'Author A', 'authora@book.com', 'Author bio', CURRENT_TIMESTAMP);
insert into books (id, title, summary, table_contents, price, number_pages, isbn, release_date, category_id, author_id)
values (1, 'Book A', 'Summary A', 'Table of Contents A', 10.00, 100, 'ISBN A', '2022-01-01', 1, 1);
insert into books (id, title, summary, table_contents, price, number_pages, isbn, release_date, category_id, author_id)
values (2, 'Book B', 'Summary B', 'Table of Contents B', 20.00, 200, 'ISBN B', '2022-01-02', 1, 1);
