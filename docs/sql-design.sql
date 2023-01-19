-- user
desc user;
select * from user;
-- join
insert into user 
values(null, 'name', 'email', password('password'), 'gender', now());