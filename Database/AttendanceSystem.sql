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
	[Gender] bit,
	[BirthDate] date,
	[Email] nvarchar(50) UNIQUE not null,
	[Password] nvarchar(50),
	[CCCD] varchar(50) UNIQUE not null,
	[PhoneNumber] varchar(50) UNIQUE,
	[EmployeeTypeID] int,
	[DepartmentID] int,
	[RoleID] int DEFAULT 1,
	[StartDate] date,
	[EndDate] date ,
	[IsActive] bit DEFAULT 1
);




CREATE TABLE EmployeeTypes(
	EmployeeTypeID int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50)
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

CREATE TABLE Shifts(
	[ShiftID] int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50),
	[StartTime] time,
	[EndTime] time
)

CREATE TABLE Timesheet(
	[TimeSheetID] int IDENTITY(1,1) PRIMARY KEY,
	[Date] date,
	[EmployeeID] int,
	[ShiftID] int,
	[CheckIn] time,
	[CheckOut] time,
--	[IsLeave] bit DEFAULT 0,
	[Note] nvarchar(max),
	UNIQUE([Date], [EmployeeID])
);

CREATE TABLE Leaves(
	TimeSheetID int PRIMARY KEY,
	Reason nvarchar(max),
	[Status] bit,
	[ResponedBy] int
)



CREATE TABLE OverTime(
	[Date] date,
	[EmployeeID] int,
	[StartTime] time,
	[EndTime] time,
	[CheckIn] time,
	[CheckOut] time,
	[OpenBefore] int,
	[CloseAfter] int,
	PRIMARY KEY([Date], [EmployeeID])
);


CREATE TABLE News(
	NewsID int PRIMARY KEY IDENTITY(1,1),
	Title nvarchar(50),
	Content nvarchar(max),
	[DateTime] datetime,
	[NewsTypeID] int,
	CreatedBy int,
)

CREATE TABLE NewsTypes(
	NewsTypeID int PRIMARY KEY IDENTITY(1,1),
	[Name] nvarchar(50)
);

CREATE TABLE [RequestsType](
	[TypeID] int PRIMARY KEY,
	[Name] nvarchar(50)
)

CREATE TABLE [Requests](
	RequestID int IDENTITY(1,1) PRIMARY KEY,
	EmployeeID int,
	Title nvarchar(50),
	[TypeID] int,
	Content nvarchar(max),
	[Status] bit,
	[ResponedBy] int
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
--ALTER TABLE Shifts
--ADD CONSTRAINT FK_Shifts_Employees FOREIGN KEY(CreatedBY) REFERENCES Employees(EmployeeID)

--TimeSheets
ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Employees FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)

ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Shifts FOREIGN KEY ([ShiftID]) REFERENCES Shifts(ShiftID)




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

ALTER TABLE News
ADD CONSTRAINT FK_News_NewsType FOREIGN KEY (NewsTypeID) REFERENCES NewsTypes(NewsTypeID)


--Requests:
ALTER TABLE [Requests]
ADD CONSTRAINT FK_Requests_Employees_ResponedBy FOREIGN KEY (ResponedBy) REFERENCES Employees(EmployeeID)

ALTER TABLE [Requests]
ADD CONSTRAINT FK_Requests_RequestsType FOREIGN KEY ([TypeID]) REFERENCES RequestsType([TypeID])

--EmployeeTypes
ALTER TABLE Employees
ADD CONSTRAINT FK_Employees_EmployeeTypes FOREIGN KEY ([EmployeeTypeID]) REFERENCES EmployeeTypes(EmployeeTypeID)

GO
--====================INSERT
INSERT INTO Roles(RoleID, [Name]) VALUES (1, N'Nhân viên');
INSERT INTO Roles(RoleID, [Name]) VALUES (2, N'Quản lý nhân sự');
INSERT INTO Roles(RoleID, [Name]) VALUES (3, N'Admin');

DECLARE @NhanVien int = 1
DECLARE @QuanLyNhanSu int = 2
DECLARE @Admin int = 3

--INSERT INTO Departments([Name]) VALUES (N'Phòng kế toán')
INSERT INTO Departments([Name]) VALUES (N'Phòng nhân sự')
INSERT INTO Departments([Name]) VALUES (N'Phòng tiếp thị')


--DECLARE @PhongKeToan int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng kế toán')
DECLARE @PhongNhanSu int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng nhân sự')
DECLARE @PhongTiepThi int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng tiếp thị')

--INSERT INTO EmployeeType
INSERT INTO EmployeeTypes([Name]) VALUES (N'Part Time')
INSERT INTO EmployeeTypes([Name]) VALUES (N'Full Time')
INSERT INTO EmployeeTypes([Name]) VALUES (N'Intern')

--DECLARE @EmployeeType
DECLARE @Part_Time int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Part Time')
DECLARE @Full_Time int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Full Time')
DECLARE @Intern int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Intern')


--Employee
INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Thành', N'Khắc', N'Nguyễn', 1, '2003-01-01', N'thanhcqb2048@gmail.com', '12345678', '035203004448', '0382293846', @Full_Time, @PhongTiepThi, @NhanVien, '2022-02-15', '2024-12-31'),
(N'Trường', N'Xuân', N'Phạm', 1, '2003-01-01', N'truongnt@gmail.com', '12345678', '001304013023', '453434545454', @Full_Time, @PhongTiepThi, @NhanVien, '2022-02-15', '2024-12-31'),
(N'Dương', N'Ngọc Khánh', N'Khánh', 1,'2003-01-01', N'Duongmnk@gmail.com', '123456789', '014205002145', '0987654312', @Part_Time, @PhongNhanSu, @NhanVien, '2023-01-01', '2025-01-01'),
--HR
(N'Đức', N'Tân', N'Nguyễn', 1, '2003-01-01', N'ducnt@gmail.com', '12345678', '001203020541', '454545454', @Intern, @PhongNhanSu, @QuanLyNhanSu, '2022-02-15', '2024-12-31'),
--Deparment Manager
(N'Bách', N'Việt', N'Lê', 1, '2003-01-01', N'bachlv3@fpt.edu.vn', '1234564578', '026303003033', '234432445', @Part_Time, @PhongTiepThi, @NhanVien, '2022-02-15', '2024-12-31'),
--HR Manager
(N'Dương', N'Mạnh', N'Nguyễn', 1, '2003-01-01', N'duong@gmail.com','1234564578', '001204002773', '25425345', @Intern, @PhongNhanSu, @QuanLyNhanSu, '2022-02-15', '2024-12-31')

--New Data
INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber,EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Tùng', N'Sơn', N'Nguyễn', 1, '2003-01-01', N'sontungMtp@gmai.com', '123123', '001204002473', '1231234', @Part_Time, @QuanLyNhanSu, @NhanVien, '2023-01-01', '2025-01-01'),
(N'Cường', N'Đức', N'Nguyễn', 1, '2003-01-01', N'denVau@gmai.com', '123456', '001203002473', '1231235', @Full_Time,@PhongNhanSu, @NhanVien, '2023-01-01', '2025-01-01'),
(N'Linh', N'Thùy', N'Hoàng', 1, '2003-01-01', N'hgThuyLing@gmai.com', '123123', '002204002473', '1231236', @Intern, @PhongTiepThi, @NhanVien, '2023-01-01', '2025-01-01'),
(N'Messi', N'Văn', N'Nguyễn', 1, '2003-01-01', N'messi@gmai.com', '123123', '001204102473', '1231237',@Part_Time, @QuanLyNhanSu, @NhanVien, '2023-01-01', '2025-01-01'),
(N'6h50', N'Văn', N'Nguyễn', 1, '2003-01-01', N'ronaldo@gmai.com', '123123', '001214002473', '1231238',@Full_Time, @QuanLyNhanSu, @NhanVien, '2023-01-01', '2025-01-01')


--EmployeeTypes
INSERT INTO EmployeeTypes([Name]) VALUES (N'Toàn thời gian');
INSERT INTO EmployeeTypes([Name]) VALUES (N'PartTime');

--RequestType
INSERT INTO RequestsType(TypeID, [Name]) VALUES
(1,N'Leave Application')
INSERT INTO RequestsType(TypeID, [Name]) VALUES
(2,N'Resignation Application')
INSERT INTO RequestsType(TypeID, [Name]) VALUES
(3,N'Request Grant Accessability')
INSERT INTO RequestsType(TypeID, [Name]) VALUES
(4,N'Request OverTime Shift')


--Shift
INSERT INTO Shifts ([Name], [StartTime], [EndTime])
VALUES
    ('Ca làm sáng', '07:30:00', '11:30:00'),
    ('Ca làm chiều', '13:30:00', '17:30:00'),
    ('Ca cả ngày', '07:30:00', '17:30:00');


--Timesheet 
DECLARE @KhacThanh int = (SELECT EmployeeID FROM Employees WHERE Email = N'thanhcqb2048@gmail.com')
DECLARE @EmployeeID INT = @KhacThanh;
DECLARE @ShiftID INT = 1;

DECLARE @DateList TABLE (SelectedDate DATE);

DECLARE @CurrentDate DATE = '2024-01-01';
WHILE @CurrentDate <= '2024-01-31'
BEGIN
    SET @CurrentDate = DATEADD(DAY, 1, @CurrentDate);
    
    IF DATEPART(WEEKDAY, @CurrentDate) BETWEEN 2 AND 6
        INSERT INTO @DateList (SelectedDate) VALUES (@CurrentDate);
END

INSERT INTO Timesheet ([Date], EmployeeID, ShiftID)
SELECT SelectedDate, @EmployeeID, @ShiftID
FROM @DateList;



--====================Modify Area

UPDATE Departments
SET ManagerID = (SELECT EmployeeID FROM Employees WHERE CCCD = '026303003033')
WHERE DepartmentID = @PhongTiepThi

UPDATE Departments
SET ManagerID = (SELECT EmployeeID FROM Employees WHERE CCCD = '001204002773')
WHERE DepartmentID = @PhongNhanSu

--====================================================================================








