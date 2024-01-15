--SELECT
SELECT * FROM Employees

--VIEW Deparment Manager
SELECT DE.DepartmentID, DE.ManagerID, EM.FirstName , EM.MiddleName, EM.LastName
FROM 
	Departments DE
	JOIN Employees EM ON DE.ManagerID = EM.EmployeeID  

--VIEW Employees that Deparment Manager manage