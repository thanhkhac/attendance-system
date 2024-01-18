--SELECT
SELECT * FROM Employees
SELECT * FROM Departments
SELECT * FROM [Application]
SELECT * FROM [Application]
--VIEW Deparment Manager
SELECT DE.DepartmentID, DE.ManagerID, EM.FirstName , EM.MiddleName, EM.LastName
FROM 
	Departments DE
	JOIN 
	Employees EM ON DE.ManagerID = EM.EmployeeID  

	SELECT * FROM Employees


	

--VIEW Employees that Deparment Manager manage
--Search and Filter 
SELECT E.EmployeeID, E.FirstName, E.MiddleName, E.LastName,E.Gender, E.Email, E.BirthDate, E.CCCD, E.StartDate,
E.EndDate, E.PhoneNumber,E.DepartmentID, E.RoleID, E.EmployeeTypeID, D.[Name] as DepartmentName, ET.[Name] as EmployeeTypeName
FROM Employees E
			JOIN Departments D ON E.DepartmentID = D.DepartmentID
			JOIN EmployeeTypes ET ON E.EmployeeTypeID = ET.EmployeeTypeID
WHERE E.FirstName LIKE N'%a%' 
	OR E.MiddleName LIKE '%a%' 
	OR E.LastName LIKE '%a%'
	OR E.CCCD LIKE '%a%'
	OR E.Email LIKE '%a%'
AND D.DepartmentID = ?
AND ET.EmployeeTypeID = ?
ORDER BY E.FirstName ASC