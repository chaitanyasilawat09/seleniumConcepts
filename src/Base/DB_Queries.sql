--Write a query to fetch the number of employees working in the department ‘HR’.
Select count(*) from Employee where dept name = 'HR'
SELECT COUNT(*) FROM EmployeeInfo WHERE Department = 'HR';

--Write a query to retrieve the first four characters of EmpLname from the EmployeeInfo table.
select substring(empname, 1,4) from emp
SELECT SUBSTRING(EmpLname, 1, 4) FROM EmployeeInfo;

--Write a query to create a new table which consists of data and structure copied from the other table.
CREATE TABLE NewEmp AS select * from emp
CREATE TABLE NewTable AS SELECT * FROM EmployeeInfo;

--Write q query to find all the employees whose salary is between 50000 to 100000.
select * from emp where salary BETWEEN '1000' and '15000'
SELECT * FROM EmployeePosition WHERE Salary BETWEEN '50000' AND '100000';

--Write a query to fetch top N records.
select top 1 * from (select top 3 from emp order by salary DESC) order by salary Ase

SELECT TOP N * FROM EmployeePosition ORDER BY Salary DESC;
SELECT * FROM EmpPosition ORDER BY Salary DESC LIMIT N

Write a query to retrieve the EmpFname and EmpLname in a single column as “FullName”. The first name and the last name must be separated with space.
select concat(EmpName,' ', EmaLName) as full name from emp
SELECT CONCAT(EmpFname, ' ', EmpLname) AS 'FullName' FROM EmployeeInfo;


Write a query to fetch all the records from the EmployeeInfo table ordered by EmpLname in descending order and Department in the ascending order.
select * from emp order by empLname, dept asc
SELECT * FROM EmployeeInfo ORDER BY EmpFname desc, Department asc;

Write a query to fetch details of all employees excluding the employees with first names, “Sanjay” and “Sonia” from the EmployeeInfo table.
SELECT * FROM EmployeeInfo WHERE EmpFname NOT IN ('Sanjay','Sonia');

Write a query to fetch the department-wise count of employees sorted by department’s count in ascending order.
SELECT Department, count(EmpID) AS EmpDeptCount FROM EmployeeInfo GROUP BY Department ORDER BY EmpDeptCount ASC;

Write a query to retrieve duplicate records from a table.
SELECT EmpID, EmpFname, Department COUNT(*) FROM EmployeeInfo GROUP BY EmpID, EmpFname, Department HAVING COUNT(*) > 1;

Write a query to retrieve the list of employees working in the same department.
Select DISTINCT E.EmpID, E.EmpFname, E.Department
FROM EmployeeInfo E, Employee E1
WHERE E.Department = E1.Department AND E.EmpID != E1.EmpID;


Q25. Write a query to find the third-highest salary from the EmpPosition table.
SELECT TOP 1 salary
FROM(
SELECT TOP 3 salary
FROM employee_table
ORDER BY salary DESC) AS emp
ORDER BY salary ASC;


--Q26. Write a query to display the first and the last record from the EmployeeInfo table.
        To display the first record from the EmployeeInfo table, you can write a query as follows:
SELECT * FROM EmployeeInfo WHERE EmpID = (SELECT MIN(EmpID) FROM EmployeeInfo);


--To display the last record from the EmployeeInfo table, you can write a query as follows:
        To display the last record from the EmployeeInfo table, you can write a query as follows:
SELECT * FROM EmployeeInfo WHERE EmpID = (SELECT MAX(EmpID) FROM EmployeeInfo);

--Write a query to add email validation to your database
SELECT Email FROM EmployeeInfo WHERE NOT REGEXP_LIKE(Email, ‘[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}’, ‘i’);

--Write a query to retrieve Departments who have less than 2 employees working in it.
SELECT DEPARTMENT, COUNT(EmpID) as 'EmpNo' FROM EmployeeInfo GROUP BY DEPARTMENT HAVING COUNT(EmpD) < 2;

Q29. Write a query to retrieve EmpPostion along with total salaries paid for each of them.
SELECT EmpPosition, SUM(Salary) from EmployeePosition GROUP BY EmpPosition;

Q30. Write a query to fetch 50% records from the EmployeeInfo table.
SELECT *
FROM EmployeeInfo WHERE
EmpID <= (SELECT COUNT(EmpID)/2 from EmployeeInfo);











