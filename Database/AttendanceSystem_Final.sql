USE master

GO
IF EXISTS (SELECT name from master.dbo.sysdatabases WHERE name = 'Attendance_DB_Final')
DROP DATABASE Attendance_DB_Final;

GO
CREATE DATABASE Attendance_DB_Final;

GO
USE Attendance_DB_Final

--=================================================================================
CREATE TABLE Departments(
	[DepartmentID] int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50),
	[ManagerID] int
);



CREATE TABLE EmployeeTypes(
	EmployeeTypeID int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50)
);


CREATE TABLE Roles(
	[RoleID] int PRIMARY KEY,
	[Name] nvarchar(50)
)



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

	FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID),
	FOREIGN KEY ([EmployeeTypeID]) REFERENCES EmployeeTypes(EmployeeTypeID),
	FOREIGN KEY ([RoleID]) REFERENCES Roles([RoleID])
);


ALTER TABLE Departments
ADD CONSTRAINT FK_Departments_Employees FOREIGN KEY([ManagerID]) REFERENCES Employees(EmployeeID)




CREATE TABLE Shifts(
	[ShiftID] int IDENTITY(1,1) PRIMARY KEY,
	[Name] nvarchar(50),
	[StartTime] time,
	[EndTime] time,
	[BreakStartTime] time,
	[BreakEndTime] time,
	[OpenAt] time,
	[CloseAt] time,
	
	[IsActive] bit DEFAULT 1
)

CREATE TABLE Timesheet(
	[TimeSheetID] int IDENTITY(1,1) PRIMARY KEY,
	[Date] date,
	[EmployeeID] int,
	[ShiftID] int,
	[CheckIn] time,
	[CheckOut] time,
	CreatedBy int,
	CHECK(CheckIn < CheckOut),

	UNIQUE([Date], [EmployeeID]),

	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([ShiftID]) REFERENCES Shifts(ShiftID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Leaves(
	LeaveID int PRIMARY KEY IDENTITY(1,1),
	[EmployeeID] int,
	[StartDate] date,
	[EndDate] date,
	FilePath nvarchar(100),
	CreatedBy int,

	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)
)


CREATE TABLE LeaveRequest(
	LeaveRequestID int PRIMARY KEY IDENTITY(1,1),
	[EmployeeID] int,
	[SentDate] datetime,
	[StartDate] date,
	[EndDate] date,
	Reason nvarchar(max),
	ManagerApprove bit,
	HrApprove bit,
	ManagerID int,
	HrID int,
	CHECK(StartDate <= EndDate),

	FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (HrID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)
)


CREATE TABLE Overtimes(
	[Date] date,
	[EmployeeID] int,
	[StartTime] time not null,
	[EndTime] time not null,
	[OpenAt] time not null,
	[CloseAt] time not null ,
	[CheckIn] time,
	[CheckOut] time,
	CreatedBy int not null,

	CHECK(OpenAt <= StartTime),
	CHECK(CloseAt >= EndTime),
	CHECK(StartTime < EndTime),
	CHECK(CheckIn < CheckOut),
	CHECK(CheckIn >= OpenAt),
	CHECK(CheckOut <= CloseAt),

	PRIMARY KEY([Date], [EmployeeID]),

	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
);

CREATE TABLE OvertimeRequests(
	[Date] date,
	[EmployeeID] int,
	[SentDate] datetime,
	[StartTime] time not null,
	[EndTime] time not null,
	ManagerApprove bit,
	HrApprove bit,
	ManagerID int,
	HrID int,
	CreatedBy int not null,
	
	PRIMARY KEY([Date], [EmployeeID]),
	CHECK(StartTime < EndTime),

	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (HrID) REFERENCES Employees(EmployeeID),
)

CREATE TABLE News(
	NewsID int PRIMARY KEY IDENTITY(1,1),
	Title nvarchar(50),
	Content nvarchar(max),
	[FilePath] nvarchar(100),
	[DateTime] datetime,
	CreatedBy int,
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
)

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
	[FilePath] nvarchar(max),
	[Status] bit,
	[ResponedBy] int

	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (ResponedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([TypeID]) REFERENCES RequestsType([TypeID])
)



GO
--====================INSERT
INSERT INTO Roles(RoleID, [Name]) VALUES (1, N'Nhân viên');
INSERT INTO Roles(RoleID, [Name]) VALUES (2, N'Quản lý nhân sự');
--INSERT INTO Roles(RoleID, [Name]) VALUES (3, N'Admin');
INSERT INTO Roles(RoleID, [Name]) VALUES (4, N'Quản lý');
INSERT INTO Roles(RoleID, [Name]) VALUES (5, N'Quản lý kiêm quản lý nhân sự');

--====================EmployeeType
INSERT INTO EmployeeTypes([Name]) VALUES (N'Part Time');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Full Time');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Intern');

--====================Department
INSERT INTO Departments([Name]) VALUES (N'Phòng nhân sự')
INSERT INTO Departments([Name]) VALUES (N'Phòng tiếp thị')




DECLARE @TYPE_FULLTIME int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Full time');
DECLARE @TYPE_PARTTIME int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Part time');
DECLARE @TYPE_CONTRACTOR int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Contractor');



DECLARE @DEPART_PHONGNHANSU int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng nhân sự');
DECLARE @DEPART_PHONGTIEPTHI int = (SELECT DepartmentID FROM Departments WHERE [Name] = N'Phòng tiếp thị');

DECLARE @ROLE_NHANVIEN int = 1
DECLARE @ROLE_QUANLYNHANSU int = 2
DECLARE @ROLE_QUANLY int = 4
DECLARE @ROLE_QUANLYKIEMQUANLYNHANSU int = 5


INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Thành', N'Khắc', N'Nguyễn', 1, '2003-01-01', N'thanhcqb2048@gmail.com', '12345678', '001300013471', '0382293846', @TYPE_FULLTIME, @DEPART_PHONGTIEPTHI, @ROLE_NHANVIEN, '2022-02-15', '2024-12-31');

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Trường', N'Xuân', N'Phạm', 1, '2003-01-01', N'truongpxhe173501@fpt.edu.vn', '12345678', '036204003385', '0382293842', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Đức', N'Tân', N'Nguyễn', 1, '2003-01-01', N'ducnthe173064@fpt.edu.vn', '12345678', '031203001933', '017452672343', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLY, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Dương', N'Mạnh', N'Nguyễn', 1, '2003-01-01', N'duongnmhe172724@fpt.edu.vn', '12345678', '014203001933', '0174526723433', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Dương', N'Khánh', N'Mai Ngọc', 1, '2003-01-01', N'duongmnkhe172188@fpt.edu.vn', '12345678', '034204000406', '091903763', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_NHANVIEN, '2022-02-15', '2024-12-31')



INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
VALUES
('2024-02-16', 1, '17:00:00', '19:30:00', '16:30:00', '20:00:00', '16:45:00', '19:45:00', 1),
('2024-02-17', 1, '15:00:00', '17:30:00', '14:30:00', '18:00:00', '14:45:00', '17:45:00', 1)

INSERT INTO Shifts ([Name], [StartTime], [EndTime], [BreakStartTime], [BreakEndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
('Ca hành chính', '7:30', '17:30', '11:00', '13:30', '7:15', '17:45', 1)
INSERT INTO Shifts ([Name], [StartTime], [EndTime], [BreakStartTime], [BreakEndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
('Ca sáng', '7:30', '11:30', NULL, NULL, '7:15', '11:45', 1)
INSERT INTO Shifts ([Name], [StartTime], [EndTime], [BreakStartTime], [BreakEndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
('Ca chiều', '13:30', '17:30', NULL, NULL, '13:15', '17:45', 1)


DECLARE @EmployeeID INT = 1;
DECLARE @ShiftID INT = 1;
DECLARE @DateList TABLE (SelectedDate DATE);

DECLARE @CurrentDate DATE = '2024-02-01';
WHILE @CurrentDate <= '2024-02-28'
BEGIN  
    IF DATEPART(WEEKDAY, @CurrentDate) BETWEEN 2 AND 6
        INSERT INTO @DateList (SelectedDate) VALUES (@CurrentDate);
	SET @CurrentDate = DATEADD(DAY, 1, @CurrentDate);
END
INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy)
SELECT SelectedDate, @EmployeeID, @ShiftID, 2
FROM @DateList;


INSERT INTO Leaves ([EmployeeID], [StartDate], [EndDate], FilePath, CreatedBy)
VALUES
  (1, '2024-02-01', '2024-02-03', 'path1', 1),
  (1, '2024-02-05', '2024-02-07', 'path2', 1),
  (1, '2024-02-10', '2024-02-12', 'path3', 1),
  (1, '2024-02-15', '2024-02-18', 'path4', 1),
  (1, '2024-02-20', '2024-02-22', 'path5', 1);












