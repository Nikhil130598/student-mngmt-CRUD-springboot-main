
show databases;
use nikhildb1;
show tables;

create table department1(dept_id int primary key, dept_name varchar(40));

insert into department1(dept_id, dept_name) values(101, 'HR'),
(102, 'IT'), (103, 'SALES');

select * from department1;


create table employees(emp_id int primary key, emp_name varchar(50), emp_salary decimal(10,2));

insert into employees(emp_id, emp_name, emp_salary) values(1, 'Nikhil', 25000),
(2, 'Suneel', 26000), (3, 'Rajkumar', 32000), (4, 'Ram', 30000), (5, 'Jyothi', 25750), 
(6, 'Sruthik', 25600), (7, 'Sandeep', 28000), (8, 'Sirisha', 26550), (9, 'Mehatb', 32000);

select * from employees;

create table dept_emp1(dept_id int, emp_id int,
primary key(dept_id, emp_id), 
foreign key(dept_id) references department1(dept_id),
foreign key(emp_id) references employees(emp_id));



insert into dept_emp1(dept_id, emp_id) values(101, 1),
(101, 2), (101, 5), (101, 8), (102, 3), (102, 6), (102, 9),
(103, 4), (103, 7);

select * from dept_emp1;

select * from dept_emp1 de1 join department1 d1 on de1.dept_id = d1.dept_id
join employees e on de1.emp_id = e.emp_id;

select d1.dept_name, e.emp_name from dept_emp1 de1 join department1 d1 on 
de1.dept_id = d1.dept_id join employees e on de1.emp_id = e.emp_id;








  
  
  
         
         