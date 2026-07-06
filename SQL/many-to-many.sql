show databases;
use nikhildb1;
show tables;

create table deptmanytomany(dept_id int primary key, dept_name varchar(40));

insert into deptmanytomany(dept_id, dept_name) values(101, 'HR'),
(102, 'IT'), (103, 'SALES');

select * from deptmanytomany;

create table empmanytomany(emp_id int primary key, emp_name varchar(50), emp_salary decimal(10,2));

insert into empmanytomany(emp_id, emp_name, emp_salary)
values(1, 'Nikhil', 25000),
(2, 'Suneel', 26000), (3, 'Rajkumar', 32000), (4, 'Ram', 30000), (5, 'Jyothi', 25750), 
(6, 'Sruthik', 25600), (7, 'Sandeep', 28000), (8, 'Sirisha', 26550), (9, 'Mehatb', 32000);

select * from empmanytomany;

create table deptemp_manytomany(dept_id int, emp_id int,
primary key(dept_id, emp_id),
foreign key(dept_id) references deptmanytomany(dept_id),
foreign key(emp_id) references empmanytomany(emp_id));

insert into deptemp_manytomany(dept_id, emp_id) values(101, 1), (101,2),
(101,4), (102,4), (102,3), (102,5), (103,6), (103,2), (103,7),(103,8),(102,8),(102,9);

select * from deptemp_manytomany;

select * from deptemp_manytomany dem join deptmanytomany dm on dem.dept_id = dm.dept_id
join empmanytomany em on dem.emp_id=em.emp_id;


delete from deptemp_manytomany where emp_id in(1,2,3,4,5,6,7,8,9);

select * from deptemp_manytomany;

delete from deptmanytomany;
delete from empmanytomany;