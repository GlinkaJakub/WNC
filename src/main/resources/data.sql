insert into categories(category_id, name) values ( 1, 'archeologia' );
insert into categories(category_id, name) values ( 2, 'filozofia' );
insert into categories(category_id, name) values ( 3, 'historia' );
insert into categories(category_id, name) values ( 4, 'językoznawstwo' );
insert into categories(category_id, name) values ( 5, 'literaturoznawstwo' );
insert into categories(category_id, name) values ( 6, 'nauki o religii i kulturze' );
insert into categories(category_id, name) values ( 7, 'nauki o sztuce' );
insert into categories(category_id, name) values ( 8, 'architektura i urbanistyka' );
insert into categories(category_id, name) values ( 9, 'automatyka, elektronika i elektrotechnika' );
insert into categories(category_id, name) values ( 10, 'informatyka techniczna i telekomunikacja' );
insert into categories(category_id, name) values ( 11, 'inżynieria biomedyczna' );
insert into categories(category_id, name) values ( 12, 'inżynieria chemiczna' );
insert into categories(category_id, name) values ( 13, 'inżynieria lądowa i transport' );
insert into categories(category_id, name) values ( 14, 'inżynieria materiałowa' );
insert into categories(category_id, name) values ( 15, 'inżynieria mechaniczna' );
insert into categories(category_id, name) values ( 16, 'inżynieria środowiska, górnictwo i energetyka' );
insert into categories(category_id, name) values ( 17, 'nauki farmaceutyczne' );
insert into categories(category_id, name) values ( 18, 'nauki medyczne' );
insert into categories(category_id, name) values ( 19, 'nauki o kulturze fizycznej' );
insert into categories(category_id, name) values ( 20, 'nauki o zdrowiu' );
insert into categories(category_id, name) values ( 21, 'nauki leśne' );
insert into categories(category_id, name) values ( 22, 'rolnictwo i ogrodnictwo' );
insert into categories(category_id, name) values ( 23, 'technologia żywności iżywienia' );
insert into categories(category_id, name) values ( 24, 'weterynaria' );
insert into categories(category_id, name) values ( 25, 'zootechnologia i rybactwo' );
insert into categories(category_id, name) values ( 26, 'ekonomia i finance' );
insert into categories(category_id, name) values ( 27, 'geografia społeczno-ekonomiczna i gospodarka przestrzenna' );
insert into categories(category_id, name) values ( 28, 'nauki o bezpieczeństwie' );
insert into categories(category_id, name) values ( 29, 'nauki o komunikacji społecznej i mediach' );
insert into categories(category_id, name) values ( 30, 'nauki o polityce i administracji' );
insert into categories(category_id, name) values ( 31, 'nauki o zarządzaniu i jakości' );
insert into categories(category_id, name) values ( 32, 'nauki prawne' );
insert into categories(category_id, name) values ( 33, 'nauki socjologiczne' );
insert into categories(category_id, name) values ( 34, 'pedagogika' );
insert into categories(category_id, name) values ( 35, 'prawo kanoniczne' );
insert into categories(category_id, name) values ( 36, 'psychologia' );
insert into categories(category_id, name) values ( 37, 'astronomia' );
insert into categories(category_id, name) values ( 38, 'informatyka' );
insert into categories(category_id, name) values ( 39, 'matematyka' );
insert into categories(category_id, name) values ( 40, 'nauki biologiczna' );
insert into categories(category_id, name) values ( 41, 'nauki chemiczne' );
insert into categories(category_id, name) values ( 42, 'nauki fizyczne' );
insert into categories(category_id, name) values ( 43, 'nauki o Ziemi i środowisku' );
insert into categories(category_id, name) values ( 44, 'nauki teologiczne' );

insert into users(user_id, email, password, name, surname, enabled)
    values ( 1, 'jakub@wp.pl', '$2a$10$ZuhZq6.AKqcDQjWLjm4X.OhukDrXiZXcLv0uiehOqKuVNDDCzs4cS', 'Jakub', 'Nowak', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 2, 'zuzia@wp.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Zuzanna', 'Nowak', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 3, 'test@test.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Pan', 'Tester', 1);
insert into users(user_id, email, password, name, surname, enabled)
    values ( 4, 'marian@wp.pl', '$2y$12$bkH8QXSciba0YWniVV8rI.vzVKpkGm/QzkfJB37UHStUOs9dM8TK6', 'Marian', 'Nowak', 1);

insert into groups(group_id, name, owner_user_id) values ( 1, 'jakuba gr', 1);
insert into groups(group_id, name, owner_user_id) values ( 2, 'zuzi gr', 2);
insert into groups(group_id, name, owner_user_id) values ( 3, 'Grupa użytkownika Pan Tester', 3);
insert into groups(group_id, name, owner_user_id) values ( 4, 'mariana gr', 4);
--
insert into authorities(auth_id, email, authority) values (1, 'jakub@wp.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (2, 'zuzia@wp.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (3, 'test@test.pl', 'ROLE_USER' );
insert into authorities(auth_id, email, authority) values (4, 'marian@wp.pl', 'ROLE_USER' );

insert into groups_users(groups_group_id, users_user_id) values ( 1, 1 );
insert into groups_users(groups_group_id, users_user_id) values ( 1, 2 );
insert into groups_users(groups_group_id, users_user_id) values ( 2, 2 );
insert into groups_users(groups_group_id, users_user_id) values ( 2, 1 );
insert into groups_users(groups_group_id, users_user_id) values ( 3, 3 );
insert into groups_users(groups_group_id, users_user_id) values ( 3, 4 );
insert into groups_users(groups_group_id, users_user_id) values ( 4, 4 );
insert into groups_users(groups_group_id, users_user_id) values ( 4, 2 );