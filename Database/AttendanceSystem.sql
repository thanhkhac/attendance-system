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
	[IsActive] bit DEFAULT 1
);

CREATE TABLE Roles(
	[RoleID] int PRIMARY KEY,
	[Name] nvarchar(50)
)


CREATE TABLE Departments(
	[DepartmentID] int IDENTITY(1,1) PRIMARY KEY,
	[ManagerID] int,
	[Name] nvarchar(50),
);

--Quản lý nhân sự sẽ tạo ca làm
--Ca làm sẽ có thời gian vào và ra
CREATE TABLE Shifts(
	[ShiftID] int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50),
	[StartTime] time,
	[EndTime] time,
)

CREATE TABLE Timesheet(
	[Date] date,
	[EmployeeID] int,
	[ShiftID] int,
	[TimeIn] time,
	[TimeOut] time,

	PRIMARY KEY ([Date], [EmployeeID], [ShiftID])
);



ALTER TABLE Employees
ADD CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
ALTER TABLE Employees
ADD	CONSTRAINT FK_Employees_Roles FOREIGN KEY ([RoleID]) REFERENCES Roles([RoleID])

ALTER TABLE Departments
ADD CONSTRAINT FK_Departments_Employees FOREIGN KEY([ManagerID]) REFERENCES Employees(EmployeeID)

ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Employees FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)
ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Employees FOREIGN KEY ([ShiftID]) REFERENCES Shifts(ShiftID)

GO

INSERT INTO Roles(RoleID, [Name]) VALUES (1, N'Nhân viên');
INSERT INTO Roles(RoleID, [Name]) VALUES (2, N'Quản lý nhân sự');
INSERT INTO Roles(RoleID, [Name]) VALUES (3, N'Admin');

INSERT INTO Departments([Name]) VALUES (N'Phòng kế toán')
INSERT INTO Departments([Name]) VALUES (N'Phòng nhân sự')
INSERT INTO Departments([Name]) VALUES (N'Phòng tiếp thị')

DECLARE @NhanVien int = 1
DECLARE @QuanLyNhanSu int = 2
DECLARE @Admin int = 3

DECLARE @PhongKeToan int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng kế toán')
DECLARE @PhongNhanSu int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng nhân sự')
DECLARE @PhongTiepThi int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng tiếp thị')


INSERT INTO Employees (FirstName, MiddleName, LastName, Email, CCCD, PhoneNumber, DepartmentID, RoleID, IsActive) VALUES
(N'Thành', N'Khắc', N'Nguyễn', 'thanhcqb2048@gmail.com', '456789012', '0382293846', @PhongTiepThi, @NhanVien, 1)

INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Ca hành chính', '7:30:00', '17:30:00')
INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Ca sáng', '7:30:00', '11:30:00')
INSERT INTO Shifts([Name],StartTime, EndTime) VALUES (N'Ca chiều', '13:30:00', '17:30:00')

DECLARE 




