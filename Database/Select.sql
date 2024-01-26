--SELECT
SELECT * FROM Employees
SELECT * FROM Departments
SELECT * FROM [Application]
SELECT * FROM [Application]
SELECT * FROM EmployeeTypes
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
E.EndDate, E.PhoneNumber,E.DepartmentID, E.RoleID, E.EmployeeTypeID, D.[Name] as DepartmentName, ET.[Name] as EmployeeTypeName, 
R.[Name] as RoleName
FROM Employees E
			JOIN Departments D ON E.DepartmentID = D.DepartmentID
			JOIN EmployeeTypes ET ON E.EmployeeTypeID = ET.EmployeeTypeID
			JOIN Roles R ON E.RoleID = R.RoleID
WHERE (E.FirstName LIKE N'%a%' 
	OR E.MiddleName LIKE '%a%' 
	OR E.LastName LIKE '%a%'
	OR E.CCCD LIKE '%a%'
	OR E.Email LIKE '%a%')
AND D.DepartmentID = 2
AND ET.EmployeeTypeID = 1
ORDER BY E.FirstName ASC

SELECT  EmployeeID, 
        FirstName,
        MiddleName,
        LastName, 
        [Password], 
        Gender,
        Email, 
        BirthDate, 
        CCCD, 
        StartDate, 
        EndDate, 
        isActive, 
        PhoneNumber,
		Roles.
        Employees.DepartmentID AS EmployeeDepartmentID, 
        Employees.RoleID, 
        Employees.EmployeeTypeID AS TypeID, 
        Departments.Name AS DepartmentName, 
        EmployeeTypes.Name AS EmployeeTypeName 
    FROM Employees
    JOIN Departments ON Employees.DepartmentID = Departments.DepartmentID 
    JOIN EmployeeTypes ON Employees.EmployeeTypeID = EmployeeTypes.EmployeeTypeID 
	JOIN [Roles] ON Employees.RoleID = Roles.RoleID
    WHERE 
        (FirstName LIKE '%a%' 
		OR MiddleName LIKE '%a%' 
		OR LastName LIKE '%a%' 
		OR CCCD LIKE '%a%' 
		OR Email LIKE '%a%') 
        AND Departments.DepartmentID = 1
        AND EmployeeTypes.EmployeeTypeID = 1



