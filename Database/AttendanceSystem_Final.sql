﻿USE master

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
INSERT INTO Shifts ([Name], [StartTime], [EndTime], [OpenAt], [CloseAt], [IsActive]) VALUES
(N'Ca tối', '19:30', '22:30','19:15', '22:45', 1)

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

DECLARE @CurrentDate DATE = '2024-02-01';
WHILE @CurrentDate <= '2024-03-31'
BEGIN  
 --   IF DATEPART(WEEKDAY, @CurrentDate) BETWEEN 2 AND 6
	--BEGIN
		INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy, CheckIn, CheckOut)
			VALUES( @CurrentDate, @EmployeeID, 1, 2, '7:15', '11:45')
		IF @CurrentDate < '2024-03-15' OR @CurrentDate = CONVERT(DATE, GETDATE())
		INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy, CheckIn)
			VALUES( @CurrentDate, @EmployeeID, 2, 2, '13:30')
		IF @CurrentDate > '2024-03-15'
		INSERT INTO Timesheet ([Date], EmployeeID, ShiftID, CreatedBy, CheckIn)
			VALUES( @CurrentDate, @EmployeeID, 3, 2, NULL)
	--END;
	SET @CurrentDate = DATEADD(DAY, 1, @CurrentDate);
END

UPDATE Timesheet 
SET CheckIn = null, CheckOut = null
WHERE Date >= CONVERT(date, GETDATE()) 






INSERT INTO Leaves ([EmployeeID], [StartDate], [EndDate], FilePath, CreatedBy)
VALUES
  (1, '2024-02-01', '2024-02-03', 'path1', 1),
  (1, '2024-02-05', '2024-02-07', 'path2', 1),
  (1, '2024-02-10', '2024-02-12', 'path3', 1),
  (1, '2024-02-15', '2024-02-18', 'path4', 1),
  (1, '2024-02-20', '2024-02-22', 'path5', 1);

INSERT INTO Leaves ([EmployeeID], [StartDate], [EndDate], FilePath, CreatedBy)
VALUES
  (1, '2024-02-01', '2024-02-03', 'path1', 1),
  (1, '2024-02-05', '2024-02-07', 'path2', 1),
  (1, '2024-02-10', '2024-02-12', 'path3', 1),
  (1, '2024-02-15', '2024-02-18', 'path4', 1),
  (1, '2024-03-01', '2024-03-5', 'path5', 1);


DECLARE @RequestTypeID INT = 4; 
  INSERT INTO Requests (EmployeeID, Title, SentDate, TypeID, Content, FilePath, Status, ProcessNote, ResponedBy)
VALUES
  (1, N'Yêu cầu ban quản lý xem xét', GETDATE(), @RequestTypeID, N'', NULL, NULL, NULL, NULL),
  (2, N'Yêu cầu ban quản lý xem xét', GETDATE(), @RequestTypeID, N'Tôi muốn đăng ký tăng ca vào ngày 03/03/2024 từ 15:00 đến 17:30.', NULL, NULL, NULL, NULL);

 
INSERT INTO [LeaveRequests] ( [EmployeeID], [SentDate], [StartDate], [EndDate], [FilePath], [Reason], [ManagerApprove], [HrApprove], [ManagerID], [HrID], [CreatedBy], [Status]) VALUES ( 1, CAST(N'2024-03-28T00:00:00.000' AS DateTime), CAST(N'2024-03-29' AS Date), CAST(N'2024-03-30' AS Date), N'Leave-Request-AttachedFiles/fb87a0b5-eccb-11ee-b9dd-6dbcbbb18060.pdf', N'Nghỉ phép', 1, NULL, 3, NULL, 3, 0)
INSERT INTO [LeaveRequests] ( [EmployeeID], [SentDate], [StartDate], [EndDate], [FilePath], [Reason], [ManagerApprove], [HrApprove], [ManagerID], [HrID], [CreatedBy], [Status]) VALUES ( 3, CAST(N'2024-03-28T00:00:00.000' AS DateTime), CAST(N'2024-03-29' AS Date), CAST(N'2024-03-30' AS Date), N'Leave-Request-AttachedFiles/1674862d-eccd-11ee-be20-ad5297dcc3d7.pdf', N'Yêu cầu', NULL, NULL, NULL, NULL, 3, 0)





insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)

insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1)




INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định chấm công', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong4.html', CAST(N'2021-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định chấm công', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong5.html', CAST(N'2022-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định chấm công', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong6.html', CAST(N'2023-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định chấm công', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong7.html', CAST(N'2024-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định chấm công 2024', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong.html', CAST(N'2020-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Thông báo ngày 28/03/2024', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong1.html', CAST(N'2019-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Quy định mới', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong2.html', CAST(N'2024-03-28T15:20:17.130' AS DateTime), 2)

INSERT [dbo].[News] ( [Title], [Content], [FilePath], [DateTime], [CreatedBy]) VALUES ( N'Thông báo ngày 20/02/2024', N'Quy định chấm công năm 2024', N'/htmlfile/QuyDinhChamCong3.html', CAST(N'2024-03-28T15:20:17.130' AS DateTime), 2)



INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
values ('2024-03-20', 1, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 5, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 6, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 7, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 8, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 9, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 10, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 11, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 12, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 13, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 14, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-20', 15, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-20', 16, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-20', 17, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-20', 18, '13:00', '16:30', '12:30', '17:00', '13:30', '16:00',2),
('2024-03-20', 19, '13:00', '16:30', '12:30', '17:00', '13:30', '16:00',2)
INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
values ('2024-03-10', 1, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 5, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 6, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 7, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 8, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 9, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 10, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 11, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-10', 12, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2)


INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
values ('2024-03-23', 1, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 5, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 6, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 7, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 8, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 9, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 10, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 11, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 12, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 13, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 14, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-23', 18, '13:00', '16:30', '12:30', '17:00', '13:30', '16:00',2),
('2024-03-23', 19, '13:00', '16:30', '12:30', '17:00', '13:30', '16:00',2),
('2024-03-23', 15, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-23', 16, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-23', 17, '7:00', '9:30', '6:30', '9:30', '6:30', '8:00',2),
('2024-03-23', 4, '9:00', '11:30', '8:30', '12:00', '8:30', '12:00',2),
('2024-03-23', 3, '9:00', '11:30', '8:30', '12:00', '8:30', '12:00',2),
('2024-03-23', 20, '19:00', '22:30', '18:30', '23:00', '18:30', '23:00',2),
('2024-03-23', 2, '19:00', '22:30', '18:30', '23:00', '18:30', '23:00',2)
INSERT INTO Overtimes ([Date], [EmployeeID], [StartTime], [EndTime], [OpenAt], [CloseAt], [CheckIn], [CheckOut], CreatedBy)
values ('2024-03-25', 10, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-25', 11, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-25', 12, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-25', 13, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2),
('2024-03-25', 14, '17:00', '19:30', '16:30', '20:00', '17:15', '20:00',2)



insert into LeaveRequests
values  (6 , '2024-03-01' , '2024-03-02' , '2024-03-02' , '' , N'việc gia đình' , 1 , 1 , 4 , 2 , 6 , 1),
		(16, '2024-03-11' , '2024-03-15' , '2024-03-17' , '' , N'Du lịch' , 0 , null , 4 , null , 16, 0),
		(16, '2024-03-12' , '2024-03-16' , '2024-03-17' , '' , N'Nghỉ phép' , 1 , 1 , 4 , 2 , 16, 1),
		(18, '2024-03-12' , '2024-03-13' , '2024-03-13' , '' , N'Nghỉ ốm' , 1 , 1 , 4 , 2 , 18, 1),
		(20, '2024-03-12' , '2024-03-15' , '2024-03-16' , '' , N'Đám cưới' , 1 , 1 , 4 , 2 , 20, 1),
		(10, '2024-03-28' , '2024-03-29' , '2024-03-30' , '' , N'Ốm' , 1 , 1 , 4 , 2 , 10, 1),
		(10, '2024-03-28' , '2024-03-29' , '2024-03-29' , '' , N'Về quê' , 1 , 1 , 4 , 2 , 10, 1),
		(12, '2024-03-27' , '2024-03-28' , '2024-03-28' , '' , N'Đi viện' , 1 , 1 , 4 , 2 , 12, 1),
		(12, '2024-03-25' , '2024-03-28' , '2024-03-29' , '' , N'Du lịch' , 1 , 1 , 4 , 2 , 12, 1),
		(14, '2024-03-26' , '2024-03-27' , '2024-03-27' , '' , N'Việc cá nhân' , 1 , 0 , 4 , 2 , 14, 0),
		(14, '2024-03-26' , '2024-03-26' , '2024-03-26' , '' , N'Việc đột xuất' , 0 , null , 4 , null , 14, 0),
		(16, '2024-03-27' , '2024-03-29' , '2024-03-30' , '' , N'Du lịch' , null , null , null , null , 16, 0),
		(16, '2024-03-28' , '2024-03-29' , '2024-03-31' , '' , N'Nghỉ phép' , 1 , 1 , 4 , 2 , 16, 1),
		(18, '2024-03-29' , '2024-03-29' , '2024-03-29' , '' , N'Nghỉ ốm' , 1 , 1 , 4 , 2 , 18, 1),
		(20, '2024-03-25' , '2024-03-26' , '2024-03-28' , '' , N'Đám cưới' , 1 , 1 , 4 , 2 , 20, 0),

		(1 , '2024-03-26' , '2024-03-28' , '2024-03-28' , '' , N'Đi lễ' , null , null , null , null , 1 , 0),
		(1 , '2024-03-26' , '2024-04-01' , '2024-04-03' , '' , N'Đi lễ' , null , null , null , null , 1 , 0),
		(1 , '2024-03-24' , '2024-03-24' , '2024-03-24' , '' , N'Việc đột xuất' , 1 , 1 , 4 , 2 , 1 , 1),
		(2 , '2024-03-27' , '2024-03-28' , '2024-03-28' , '' , N'Việc cá nhân' , null , null , null , null , 2 , 0),
		(3 , '2024-03-19' , '2024-03-20' , '2024-03-23' , '' , N'Du lịch' , null , null , null , null , 3 , 0),
		(3 , '2024-03-27' , '2024-03-28' , '2024-03-29' , '' , N'Nghỉ ốm' , 1 , 1 , 4 , 2 , 3 , 1),
		(5 , '2024-03-24' , '2024-03-25' , '2024-03-25' , '' , N'Việc cá nhân' , null , null , null , null , 5 , 0),
		(9 , '2024-03-20' , '2024-03-22' , '2024-03-23' , '' , N'Đám cưới' , null , null , null , null , 9 , 0),
		(15, '2024-03-21' , '2024-03-23' , '2024-03-25' , '' , N'Du lịch' , null , null , null , null , 15, 0),
		(15, '2024-03-28' , '2024-03-29' , '2024-03-29' , '' , N'Việc cá nhân' , null , null , null , null , 15, 0),
		(17, '2024-03-28' , '2024-03-29' , '2024-03-29' , '' , N'Đi viện' , null , null , null , null , 17 , 0)



INSERT INTO OvertimeRequests ([Date], [EmployeeID], [SentDate], [StartTime], [EndTime], ManagerApprove, HrApprove, ManagerID, HrID, CreatedBy, [Status])
VALUES	('2024-03-29' , 1 , '2024-03-24 09:00:00'  , '18:00:00' , '20:30:00' , 1, NULL, 4, NULL, 3, 0),
		('2024-03-28' , 1 , '2024-03-24 09:00:00'  , '18:00:00' , '20:30:00' , 1, NULL, 4, NULL, 3, 0),
		('2024-03-29' , 3 , '2024-03-24 10:30:00'  , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 4, 1),
		('2024-03-29' , 4 , '2024-03-24 08:15:00'  , '17:30:00' , '20:00:00' , NULL, NULL, NULL, NULL, 2, 0),
		('2024-03-29' , 4 , '2024-03-24 12:45:00'  , '15:00:00' , '17:30:00' , 1, 0, 3, 4, 3, 1),
		('2024-03-28' , 5 , '2024-03-24 09:00:00'  , '18:00:00' , '20:30:00' , NULL, NULL, NULL, NULL, 4, 0),
		('2024-03-28' , 5 , '2024-03-24 10:30:00'  , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 5, 1),
		('2024-03-28' , 6 , '2024-03-24 08:15:00'  , '17:30:00' , '20:00:00' , 1, 0, 3, 4, 6, 1),
		('2024-04-01' , 6 , '2024-03-24 12:45:00'  , '15:00:00' , '17:30:00' , NULL, NULL, NULL, NULL, 2, 0),
		('2024-04-01' , 7 , '2024-03-25 09:00:00'  , '18:00:00' , '20:30:00' , NULL, NULL, NULL, NULL, 4, 0),
		('2024-04-01' , 7 , '2024-03-25 10:30:00'  , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 7, 1),
		('2024-04-02' , 8 , '2024-03-25 08:15:00'  , '17:30:00' , '20:00:00' , 1, 0, 3, 4, 8, 1),
		('2024-04-02' , 8 , '2024-03-25 12:45:00'  , '15:00:00' , '17:30:00' , 1, 0, 3, 4, 9, 1),
		('2024-04-02' , 9 , '2024-03-25 09:00:00'  , '18:00:00' , '20:30:00' , NULL, NULL, NULL, NULL, 10, 0),
		('2024-04-02' , 9 , '2024-03-25 10:30:00'  , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 11, 1),
		('2024-04-03' , 10 , '2024-03-25 08:15:00' , '17:30:00' , '20:00:00' , 1, 0, 3, 4, 12, 1),
		('2024-04-03' , 10 , '2024-03-26 12:45:00' , '15:00:00' , '17:30:00' , NULL, NULL, NULL, NULL, 13, 0),
		('2024-04-03' , 11 , '2024-03-26 09:00:00' , '18:00:00' , '20:30:00' , 1, 0, 3, 4, 14, 1),
		('2024-04-03' , 11 , '2024-03-26 10:30:00' , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 15, 1),
		('2024-04-04' , 12 , '2024-03-26 08:15:00' , '17:30:00' , '20:00:00' , NULL, NULL, NULL, NULL, 16, 0),
		('2024-04-04' , 12 , '2024-03-26 12:45:00' , '15:00:00' , '17:30:00' , 1, 0, 3, 4, 17, 1),
		('2024-04-04' , 13 , '2024-03-26 09:00:00' , '18:00:00' , '20:30:00' , NULL, NULL, NULL, NULL, 18, 0),
		('2024-04-04' , 13 , '2024-03-26 10:30:00' , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 19, 1),
		('2024-04-05' , 14 , '2024-03-26 08:15:00' , '17:30:00' , '20:00:00' , 1, 0, 3, 4, 20, 1),
		('2024-04-05' , 14 , '2024-03-26 12:45:00' , '15:00:00' , '17:30:00' , NULL, NULL, NULL, NULL, 21, 0),
		('2024-04-05' , 15 , '2024-03-26 09:00:00' , '18:00:00' , '20:30:00' , 1, 0, 3, 4, 21, 1),
		('2024-04-05' , 15 , '2024-03-27 10:30:00' , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 21, 1),
		('2024-04-05' , 16 , '2024-03-27 08:15:00' , '17:30:00' , '20:00:00' , NULL, NULL, NULL, NULL, 21, 0),
		('2024-04-05' , 16 , '2024-03-27 12:45:00' , '15:00:00' , '17:30:00' , 1, 0, 3, 4, 21, 1),
		('2024-04-05' , 17 , '2024-03-27 09:00:00' , '18:00:00' , '20:30:00' , NULL, NULL, NULL, NULL, 12, 0),
		('2024-04-05' , 17 , '2024-03-27 10:30:00' , '15:30:00' , '18:30:00' , 1, 1, 4, 5, 21, 1);

