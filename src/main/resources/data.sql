insert into categories(category_id, name) values ( 1, 'Music' );
insert into categories(category_id, name) values ( 2, 'Computer Science' );
insert into categories(category_id, name) values ( 3, 'Biology' );
insert into categories(category_id, name) values ( 4, 'Chemistry' );

insert into journals(journal_id, title1, issn1, eissn1, title2, issn2, eissn2, points)
    values ( 1, '19th Century music', '0148-2076', 'e0148-2076', 'Muzyka 19 wieku', '1534-3219', 'e1534-3219', 20 );
insert into journals(journal_id, title1, issn1, eissn1, title2, issn2, eissn2, points)
    values ( 2, 'ACM TRANSACTIONS ON MATHEMATICAL SOFTWARE', '0098-3500', 'e0098-3500', 'ACM Tranzakcje w systemach matematycznych', '0360-0300', 'e0360-0300', 50 );
insert into journals(journal_id, title1, issn1, eissn1, title2, issn2, eissn2, points)
    values ( 3, 'Acta Biologica', '1230-3976', 'e1230-3976', 'Acka biolocostam', '2353-3013', 'e2353-3013', 9 );
insert into journals(journal_id, title1, issn1, eissn1, title2, issn2, eissn2, points)
    values ( 4, 'Advances in Polymer Science', '0065-3195', 'e0065-3195', 'Polimer dla zaawansownych', '0065-3454', 'e0065-3454', 45 );

insert into users(user_id, email, password, name, surname, enabled)
    values ( 1, 'jakub@wp.pl', '$2a$10$ZuhZq6.AKqcDQjWLjm4X.OhukDrXiZXcLv0uiehOqKuVNDDCzs4cS', 'Jakub', 'Nowak', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 2, 'zuzia@wp.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Zuzanna', 'Nowak', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 3, 'jarek@wp.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Jarosław', 'Nowak', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 4, 'marian@wp.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Marian', 'Nowak', 1);

insert into groups(group_id, name) values ( 1, 'jakuba gr');
insert into groups(group_id, name) values ( 2, 'zuzi gr');
insert into groups(group_id, name) values ( 3, 'jarka gr');
insert into groups(group_id, name) values ( 4, 'mariana gr');

insert into authorities(auth_id, email, authority) values (1, 'jakub@wp.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (2, 'zuzia@wp.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (3, 'jarek@wp.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (4, 'marian@wp.pl', 'ROLE_USER' );

insert into journals_categories(journals_journal_id, categories_category_id) values ( 1, 1 );
insert into journals_categories(journals_journal_id, categories_category_id) values ( 2, 2 );
insert into journals_categories(journals_journal_id, categories_category_id) values ( 3, 3 );
insert into journals_categories(journals_journal_id, categories_category_id) values ( 4, 4 );

insert into groups_journals(groups_group_id, journals_journal_id) values ( 1, 1 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 1, 2 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 2, 2 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 2, 3 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 2, 4 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 3, 3 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 4, 4 );
insert into groups_journals(groups_group_id, journals_journal_id) values ( 4, 1 );

insert into groups_users(groups_group_id, users_user_id) values ( 1, 1 );
insert into groups_users(groups_group_id, users_user_id) values ( 1, 2 );
insert into groups_users(groups_group_id, users_user_id) values ( 2, 2 );
insert into groups_users(groups_group_id, users_user_id) values ( 2, 1 );
insert into groups_users(groups_group_id, users_user_id) values ( 3, 3 );
insert into groups_users(groups_group_id, users_user_id) values ( 3, 4 );
insert into groups_users(groups_group_id, users_user_id) values ( 4, 4 );
insert into groups_users(groups_group_id, users_user_id) values ( 4, 2 );