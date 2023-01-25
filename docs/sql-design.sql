-- user
desc user;
select * from user;
-- join
insert into user 
values(null, 'name', 'email', password('password'), 'gender', now());

-- login
select *
from user
where email = 'zzang9@naver.com' 
and password = password('1234');

select *
from user
where no = 1;


update user
set name = "신짱구", gender ="male"
where email = 'zzang9@naver.com';
update user
set name = '신짱구',gender ='male', password =password('1111')
where email = 'zzang9@naver.com';

desc board;
