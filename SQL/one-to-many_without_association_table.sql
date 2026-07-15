show databases;
use nikhildb1;
show tables;

create table department2(dept_id int primary key, dept_name varchar(40));

insert into department2(dept_id, dept_name) values(101, 'HR'),
(102, 'IT'), (103, 'SALES');

select * from department2;

create table employees2(emp_id int primary key, emp_name varchar(50), emp_salary decimal(10,2), dept_id int,
foreign key(dept_id) references department2(dept_id));

insert into employees2(emp_id, emp_name, emp_salary, dept_id)
values(1, 'Nikhil', 25000, 101),
(2, 'Suneel', 26000, 101), (3, 'Rajkumar', 32000, 102), (4, 'Ram', 30000, 103), (5, 'Jyothi', 25750, 101),
(6, 'Sruthik', 25600, 102), (7, 'Sandeep', 28000, 103), (8, 'Sirisha', 26550, 101), (9, 'Mehatb', 32000, 102);

select * from employees2;

select * from employees2 e2 join department2 d2 on e2.dept_id=d2.dept_id;

select e.emp_name, d.dept_name from employees2 e join department2 d on e.dept_id = d.dept_id;

select e.emp_name, d.dept_name, e.emp_salary from employees2 e join department2 d on e.dept_id = d.dept_id
where e.emp_salary>27000;



delete from employees2;

delete from department2;






