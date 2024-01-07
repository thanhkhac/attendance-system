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
	[PhoneNumber] varchar(50),
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
	[TimeIn] time,
	[TimeOut] time,
--	[IsLeave] bit DEFAULT 0,
	[Note] nvarchar(max),
	UNIQUE([Date], [EmployeeID])
);

CREATE TABLE Leaves(
	TimeSheetID int UNIQUE,
	Reason nvarchar(max),
	[Status] bit,
	[ResponedBy] int
)


CREATE TABLE OverTime(
	[Date] date,
	[EmployeeID] int,
	[StartTime] time,
	[EndTime] time,
	[TimeIn] time,
	[TimeOut] time,
	PRIMARY KEY([Date], [EmployeeID])
);


CREATE TABLE News(
	NewsID int PRIMARY KEY,
	Title nvarchar(50),
	[Content] nvarchar(max),
	[DateTime] datetime,
	CreatedBy int,
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


INSERT INTO Employees (FirstName, MiddleName, LastName, Email, CCCD, PhoneNumber, DepartmentID, RoleID, IsActive) VALUES
(N'Thành', N'Khắc', N'Nguyễn', 'thanhcqb2048@gmail.com', '456789012', '0382293846', @PhongTiepThi, @NhanVien, 1)

--INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Cả ngày', '7:30:00', '17:30:00')
--INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Ca sáng', '7:30:00', '11:30:00')
--INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Ca chiều', '13:30:00', '17:30:00')


--DECLARE @CaNgay int = (SELECT ShiftID FROM Shifts WHERE [Name] = N'Cả ngày')
--DECLARE @CaSang int = (SELECT ShiftID FROM Shifts WHERE [Name] = N'Ca sáng')
--DECLARE @CaChieu int = (SELECT ShiftID FROM Shifts WHERE [Name] = N'Ca chiều')





