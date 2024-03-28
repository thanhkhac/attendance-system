USE master

GO
IF EXISTS (SELECT name from master.dbo.sysdatabases WHERE name = 'Attendance_DB_Final')
BEGIN
ALTER DATABASE Attendance_DB_Final SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

ALTER DATABASE Attendance_DB_Final SET MULTI_USER;
DROP DATABASE Attendance_DB_Final;

END;
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
	[Avatar] nvarchar(max),
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

	UNIQUE([Date], [EmployeeID], [ShiftID]),

	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([ShiftID]) REFERENCES Shifts(ShiftID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Leaves(
	LeaveID int PRIMARY KEY IDENTITY(1,1),
	[EmployeeID] int,
	[StartDate] date,
	[EndDate] date,
	FilePath nvarchar(max),
	[Reason] nvarchar(max),
	[CreatedDate] date DEFAULT GETDATE(),
	CreatedBy int,

	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID)
)


CREATE TABLE LeaveRequests(
	LeaveRequestID int PRIMARY KEY IDENTITY(1,1),
	[EmployeeID] int,
	[SentDate] datetime,
	[StartDate] date,
	[EndDate] date,
	FilePath nvarchar(max),
	Reason nvarchar(max),
	ManagerApprove bit,
	HrApprove bit,
	ManagerID int,
	HrID int,
	[CreatedBy] int,
	[Status] bit default(0),
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

	PRIMARY KEY([Date], EmployeeID),

	FOREIGN KEY ([EmployeeID]) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
);

CREATE TABLE OvertimeRequests(
	OvertimeRequestID int PRIMARY KEY IDENTITY(1,1),
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
	[Status] bit default(0),
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
	[FilePath] nvarchar(max),
	[DateTime] datetime,
	CreatedBy int,
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
)

CREATE TABLE [RequestsType](
	[TypeID] int PRIMARY KEY IDENTITY(1,1),
	[Name] nvarchar(50)
)

CREATE TABLE [Requests](
	RequestID int IDENTITY(1,1) PRIMARY KEY,
	EmployeeID int,
	Title nvarchar(50),
	[SentDate] datetime,
	[TypeID] int,
	Content nvarchar(max),
	[FilePath] nvarchar(max),
	[Status] bit,
	ProcessNote nvarchar(max),
	[ResponedBy] int,

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
INSERT INTO EmployeeTypes([Name]) VALUES (N'Nhân viên');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Nhân viên parttime');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Thực tập');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Giám đốc');
INSERT INTO EmployeeTypes([Name]) VALUES (N'Trưởng phòng');

--====================Department
INSERT INTO Departments([Name]) VALUES (N'Phòng nhân sự')
INSERT INTO Departments([Name]) VALUES (N'Phòng tiếp thị')




DECLARE @TYPE_FULLTIME int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Nhân viên');
DECLARE @TYPE_PARTTIME int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Thực tập');
DECLARE @TYPE_CONTRACTOR int = (SELECT EmployeeTypeID FROM EmployeeTypes WHERE [Name] = N'Trưởng phòng');



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
(N'Đức', N'Tân', N'Nguyễn', 1, '2003-01-01', N'ducnthe173064@fpt.edu.vn', '12345678', '031203001933', '017452672343', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Dương', N'Mạnh', N'Nguyễn', 1, '2003-01-01', N'duongnmhe172724@fpt.edu.vn', '12345678', '014203001933', '0174526723433', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) VALUES
(N'Dương', N'Khánh', N'Mai Ngọc', 1, '2003-01-01', N'duongmnkhe172188@fpt.edu.vn', '12345678', '034204000406', '091903763', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_NHANVIEN, '2022-02-15', '2024-12-31')

INSERT INTO Employees(FirstName, MiddleName, LastName, Gender, BirthDate, Email, [Password], CCCD, PhoneNumber, EmployeeTypeID, DepartmentID, RoleID, StartDate, EndDate) 
VALUES
(N'Hồng', N'Thị', N'Nguyễn', 0, '1995-03-15', N'hongnt@gmail.com', 'password123', '012345678', '0987654321', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_NHANVIEN, '2021-01-15', '2023-12-31'),
(N'Quang', N'Thế', N'Trần', 1, '1990-08-20', N'quangtt@gmail.com', 'pass456', '012345679', '0987654322', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2021-03-10', '2023-12-31'),
(N'Minh', N'Văn', N'Phạm', 1, '1988-05-10', N'minhvp@gmail.com', 'pass789', '012345680', '0987654323', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_QUANLY, '2021-02-22', '2023-12-31'),
(N'Thảo', N'Mai', N'Đặng', 0, '1993-12-03', N'thaodm@gmail.com', 'pass101', '012345681', '0987654324', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2021-04-05', '2023-12-31'),
(N'Anh', N'Huy', N'Lê', 1, '1997-09-18', N'anhlh@gmail.com', 'pass121', '012345682', '0987654325', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_NHANVIEN, '2021-06-15', '2023-12-31'),
(N'Trang', N'Thị', N'Vũ', 0, '1994-07-25', N'trangtv@gmail.com', 'pass141', '012345683', '0987654326', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2021-08-10', '2023-12-31'),
(N'Nam', N'Quốc', N'Nguyễn', 1, '1991-11-05', N'namnq@gmail.com', 'pass161', '012345684', '0987654327', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_QUANLY, '2021-09-22', '2023-12-31'),
(N'Tâm', N'Thị', N'Lê', 0, '1996-02-12', N'tamtl@gmail.com', 'pass181', '012345685', '0987654328', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2021-11-05', '2023-12-31'),
(N'Tuấn', N'Trung', N'Đỗ', 1, '1998-06-30', N'tuandt@gmail.com', 'pass201', '012345686', '0987654329', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_NHANVIEN, '2022-01-15', '2024-12-31'),
(N'Quỳnh', N'Thị', N'Phan', 0, '1992-04-08', N'quynhpt@gmail.com', 'pass221', '012345687', '0987654330', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2022-03-10', '2024-12-31'),
(N'Thành', N'Long', N'Trần', 1, '1989-10-20', N'thanhtl@gmail.com', 'pass241', '012345688', '0987654331', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_QUANLY, '2022-02-22', '2024-12-31'),
(N'Hoài', N'Yến', N'Nguyễn', 0, '1994-01-25', N'hoaing@gmail.com', 'pass261', '012345689', '0987654332', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2022-04-05', '2024-12-31'),
(N'Tuấn', N'Đức', N'Lê', 1, '1990-07-18', N'tuandl@gmail.com', 'pass281', '012345690', '0987654333', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_NHANVIEN, '2022-06-15', '2024-12-31'),
(N'My', N'Thị', N'Võ', 0, '1993-05-10', N'mytv@gmail.com', 'pass301', '012345691', '0987654334', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYNHANSU, '2022-08-10', '2024-12-31'),
(N'Trường', N'Đức', N'Phan', 1, '1988-12-03', N'truongdp@gmail.com', 'pass321', '012345692', '0987654335', @TYPE_FULLTIME, @DEPART_PHONGNHANSU, @ROLE_QUANLY, '2022-09-22', '2024-12-31'),
(N'Thùy', N'Liên', N'Đặng', 0, '1997-02-18', N'thuyld@gmail.com', 'pass341', '012345693', '0987654336', @TYPE_PARTTIME, @DEPART_PHONGTIEPTHI, @ROLE_QUANLYKIEMQUANLYNHANSU, '2022-11-05', '2024-12-31')



INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
VALUES
('2024-02-16', 1, '17:00:00', '19:30:00', '16:30:00', '20:00:00', '16:45:00', '19:45:00', 1),
('2024-02-17', 1, '15:00:00', '17:30:00', '14:30:00', '18:00:00', '14:45:00', '17:45:00', 1)

INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
VALUES
('2024-02-23', 1, '17:00:00', '19:30:00', '16:30:00', '20:00:00', NULL, NULL, 1),
('2024-02-24', 1, '15:00:00', '17:30:00', '14:30:00', '18:00:00', NULL, NULL, 1)

INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
VALUES
('2024-02-23', 2, '17:00:00', '19:30:00', '16:30:00', '20:00:00', NULL, NULL, 1),
('2024-02-24', 2, '15:00:00', '17:30:00', '14:30:00', '18:00:00', NULL, NULL, 1)


INSERT INTO Shifts ([Name], [StartTime], [EndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
(N'Ca sáng', '7:30', '11:30', '7:15', '11:45', 1)
INSERT INTO Shifts ([Name], [StartTime], [EndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
(N'Ca chiều', '13:30', '17:30','13:15', '17:45', 1)

INSERT INTO RequestsType([Name])
VALUES (N'Tăng ca')
INSERT INTO RequestsType([Name])
VALUES (N'Nghỉ phép')
INSERT INTO RequestsType (Name) VALUES
(N'Thay Đổi Thông Tin Cá Nhân'),
(N'Xin Nâng Lương'),
(N'Xin Chế Độ Làm Việc Linh Hoạt'),
(N'Xin Thăng Tiến/Chuyển Bộ Phận'),
(N'Xin Nghỉ Việc')

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


DECLARE @RequestTypeID INT = 1; 
  INSERT INTO Requests (EmployeeID, Title, SentDate, TypeID, Content, FilePath, Status, ProcessNote, ResponedBy)
VALUES
  (1, N'Yêu cầu tăng ca ngày 01/03/2024', GETDATE(), @RequestTypeID, N'Tôi muốn đăng ký tăng ca vào ngày 01/03/2024 từ 17:00 đến 19:30.', NULL, NULL, NULL, NULL),
  (2, N'Yêu cầu tăng ca ngày 03/03/2024', GETDATE(), @RequestTypeID, N'Tôi muốn đăng ký tăng ca vào ngày 03/03/2024 từ 15:00 đến 17:30.', NULL, NULL, NULL, NULL);

 
INSERT INTO [LeaveRequests] ( [EmployeeID], [SentDate], [StartDate], [EndDate], [FilePath], [Reason], [ManagerApprove], [HrApprove], [ManagerID], [HrID], [CreatedBy], [Status]) VALUES ( 1, CAST(N'2024-03-28T00:00:00.000' AS DateTime), CAST(N'2024-03-29' AS Date), CAST(N'2024-03-30' AS Date), N'Leave-Request-AttachedFiles/fb87a0b5-eccb-11ee-b9dd-6dbcbbb18060.pdf', N'Nghỉ phép', 1, NULL, 3, NULL, 3, 0)
INSERT INTO [LeaveRequests] ( [EmployeeID], [SentDate], [StartDate], [EndDate], [FilePath], [Reason], [ManagerApprove], [HrApprove], [ManagerID], [HrID], [CreatedBy], [Status]) VALUES ( 3, CAST(N'2024-03-28T00:00:00.000' AS DateTime), CAST(N'2024-03-29' AS Date), CAST(N'2024-03-30' AS Date), N'Leave-Request-AttachedFiles/1674862d-eccd-11ee-be20-ad5297dcc3d7.pdf', N'Không có chi', NULL, NULL, NULL, NULL, 3, 0)


  SELECT * FROM LeaveRequests