USE master

GO
IF EXISTS (SELECT name from master.dbo.sysdatabases WHERE name = 'Attendance_DB')
DROP DATABASE Attendance_DB;

GO
CREATE DATABASE Attendance_DB;

GO
USE Attendance_DB

--=================================================================================
CREATE TABLE Employees(
	[EmployeeID] int IDENTITY(1,1) PRIMARY KEY,
	[FirstName] nvarchar(50) not null,
	[MiddleName] nvarchar(50) not null,
	[LastName] nvarchar(50) not null,
	[Email] nvarchar(50) UNIQUE not null,
	[CCCD] varchar(50) UNIQUE not null,
	[PhoneNumber] varchar(50) UNIQUE,
	[DepartmentID] int,
	[RoleID] int DEFAULT 1,
	[StartDate] date,
	[EndDate] date ,
	[IsActive] bit DEFAULT 1
);

CREATE TABLE Roles(
	[RoleID] int PRIMARY KEY,
	[Name] nvarchar(50)
)


CREATE TABLE Departments(
	[DepartmentID] int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50),
	[ManagerID] int,
);

--CREATE TABLE Shifts(
--	[ShiftID] int IDENTITY(1,1) PRIMARY KEY,
--	[Name] nvarchar(50),
--	[StartTime] time,
--	[EndTime] time
--)

CREATE TABLE Timesheet(
	[TimeSheetID] int IDENTITY(1,1) PRIMARY KEY,
	[Date] date,
	[EmployeeID] int,
--	[ShiftID] int,
	[CheckIn] time,
	[CheckOut] time,
--	[IsLeave] bit DEFAULT 0,
	[Note] nvarchar(max),
	UNIQUE([Date], [EmployeeID])
);

CREATE TABLE Leaves(
	TimeSheetID int PRIMARY KEY,
	Reason nvarchar(max),
	--[Status] bit,
	[ResponedBy] int
)



CREATE TABLE OverTime(
	[Date] date,
	[EmployeeID] int,
	[StartTime] time,
	[EndTime] time,
	[CheckIn] time,
	[CheckOut] time,
	PRIMARY KEY([Date], [EmployeeID])
);


CREATE TABLE News(
	NewsID int PRIMARY KEY,
	Title nvarchar(50),
	Content nvarchar(max),
	[DateTime] datetime,
	CreatedBy int,
)

CREATE TABLE [Application](
	ApplicationID int IDENTITY(1,1) PRIMARY KEY,
	EmployeeID int,
	Title nvarchar(50), 
	Content nvarchar(max),
	[Status] bit,
	[ResponedBy] int
)

CREATE TABLE[Shifts](
	[ShiftID] int IDENTITY(1,1) PRIMARY KEY,
	[StartDate] date,
	[StartTime] time,
	[EndTime] time,
	[OpenBefore] int,
	[CloseAfter] int,
	[CreatedBy] int 
)

GO
--=====================FOREIGN KEY
--Employees
ALTER TABLE Employees
ADD CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)

ALTER TABLE Employees
ADD	CONSTRAINT FK_Employees_Roles FOREIGN KEY ([RoleID]) REFERENCES Roles([RoleID])


--Departments
ALTER TABLE Departments
ADD CONSTRAINT FK_Departments_Employees FOREIGN KEY([ManagerID]) REFERENCES Employees(EmployeeID)

--Shift
ALTER TABLE Shifts
ADD CONSTRAINT FK_Shifts_Employees FOREIGN KEY(CreatedBY) REFERENCES Employees(EmployeeID)

--TimeSheets
ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Employees FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)

--ALTER TABLE Timesheet
--ADD CONSTRAINT FK_Timesheet_Shifts FOREIGN KEY ([ShiftID]) REFERENCES Shifts(ShiftID)


--Leaves
ALTER TABLE Leaves
ADD CONSTRAINT FK_Timesheet_Leaves FOREIGN KEY (TimeSheetID) REFERENCES Timesheet(TimesheetID) 

ALTER TABLE Leaves
ADD CONSTRAINT FK_Timesheet_Employees_ResponedBy FOREIGN KEY ([ResponedBy]) REFERENCES Employees(EmployeeID) 


--OverTimes
ALTER TABLE OverTime
ADD CONSTRAINT FK_OverTime_Employees FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)


--News
ALTER TABLE News
ADD CONSTRAINT FK_News_Employees_CreatedBy FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)


--Application:
ALTER TABLE [Application]
ADD CONSTRAINT FK_Application_Employees_ResponedBy FOREIGN KEY (ResponedBy) REFERENCES Employees(EmployeeID)





GO
--====================INSERT
INSERT INTO Roles(RoleID, [Name]) VALUES (1, N'Nhân viên');
INSERT INTO Roles(RoleID, [Name]) VALUES (2, N'Quản lý nhân sự');
INSERT INTO Roles(RoleID, [Name]) VALUES (3, N'Admin');

DECLARE @NhanVien int = 1
DECLARE @QuanLyNhanSu int = 2
DECLARE @Admin int = 3

INSERT INTO Departments([Name]) VALUES (N'Phòng kế toán')
INSERT INTO Departments([Name]) VALUES (N'Phòng nhân sự')
INSERT INTO Departments([Name]) VALUES (N'Phòng tiếp thị')


DECLARE @PhongKeToan int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng kế toán')
DECLARE @PhongNhanSu int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng nhân sự')
DECLARE @PhongTiepThi int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng tiếp thị')









