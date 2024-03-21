

DROP DATABASE IF EXISTS Attendance_DB_Final;

CREATE DATABASE IF NOT EXISTS Attendance_DB_Final;

USE Attendance_DB_Final;


CREATE TABLE Departments(
	DepartmentID int AUTO_INCREMENT PRIMARY KEY,
	Name NVARCHAR(255)
	ManagerID int	
);

CREATE TABLE EmployeeTypes(
	EmployeeTypeID int AUTO_INCREMENT PRIMARY KEY,
	Name NVARCHAR(255)
);

CREATE TABLE Roles(
	RoleID int AUTO_INCREMENT PRIMARY KEY,
	Name NVARCHAR(255)
);

CREATE TABLE Employees(
	EmployeeID int AUTO_INCREMENT PRIMARY KEY,
	FirstName NVARCHAR(255) not null,
	MiddleName VARCHAR(255) not null,
	LastName VARCHAR(50) not null,
	Gender bit,
	BirthDate date,
	Email NVARCHAR(255) UNIQUE not null,
	Password NVARCHAR(50),
	CCCD varchar(50) UNIQUE not null,
	PhoneNumber varchar(50) UNIQUE,
	EmployeeTypeID int,
	DepartmentID int,
	RoleID int DEFAULT 1,
	StartDate date,
	EndDate date,
	Avatar TEXT,
	IsActive bit DEFAULT 1,
	FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID),
	FOREIGN KEY (EmployeeTypeID) REFERENCES EmployeeTypes(EmployeeTypeID),
	FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

ALTER TABLE Departments
ADD CONSTRAINT FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID);

ALTER TABLE Employees
ADD CONSTRAINT FK_Departments_Employees FOREIGN KEY(DepartmentID) REFERENCES Departments(DepartmentID);


CREATE TABLE Shifts(
	ShiftID int AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(50),
	StartTime time,
	EndTime time,
	OpenAt time,
	CloseAt time,
	IsActive bit DEFAULT 1
);

CREATE TABLE Timesheet(
	TimeSheetID int AUTO_INCREMENT PRIMARY KEY,
	Date date,
	EmployeeID int,
	ShiftID int,
	CheckIn time,
	CheckOut time,
	CreatedBy int,
	CHECK(CheckIn < CheckOut),
	UNIQUE(Date, EmployeeID, ShiftID),
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (ShiftID) REFERENCES Shifts(ShiftID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Leaves(
	LeaveID int AUTO_INCREMENT PRIMARY KEY,
	EmployeeID int,
	StartDate date,
	EndDate date,
	FilePath NVARCHAR(255),
	Reason TEXT,
	CreatedDate date ,
	CreatedBy int,
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE LeaveRequests(
	LeaveRequestID int AUTO_INCREMENT PRIMARY KEY,
	EmployeeID int,
	SentDate datetime,
	StartDate date,
	EndDate date,
	FilePath VARCHAR(255),
	Reason TEXT,
	ManagerApprove bit,
	HrApprove bit,
	ManagerID int,
	HrID int,
	CreatedBy int,
	Status bit DEFAULT 0,
	CHECK(StartDate <= EndDate),
	FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (HrID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Overtimes(
	Date date,
	EmployeeID int,
	StartTime time not null,
	EndTime time not null,
	OpenAt time not null,
	CloseAt time not null,
	CheckIn time,
	CheckOut time,
	CreatedBy int not null,
	CHECK(OpenAt <= StartTime),
	CHECK(CloseAt >= EndTime),
	CHECK(StartTime < EndTime),
	CHECK(CheckIn < CheckOut),
	CHECK(CheckIn >= OpenAt),
	CHECK(CheckOut <= CloseAt),
	PRIMARY KEY(Date, EmployeeID),
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
);

CREATE TABLE OvertimeRequests(
	OvertimeRequestID int AUTO_INCREMENT PRIMARY KEY,
	Date date,
	EmployeeID int,
	SentDate datetime,
	StartTime time not null,
	EndTime time not null,
	ManagerApprove bit,
	HrApprove bit,
	ManagerID int,
	HrID int,
	CreatedBy int not null,
	Status bit DEFAULT 0,
	CHECK(StartTime < EndTime),
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (HrID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE News(
	NewsID int AUTO_INCREMENT PRIMARY KEY,
	Title VARCHAR(50),
	Content TEXT,
	FilePath VARCHAR(255),
	DateTime datetime,
	CreatedBy int,
	FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID)
);

CREATE TABLE RequestsType(
	TypeID int AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(50)
);

CREATE TABLE Requests(
	RequestID int AUTO_INCREMENT PRIMARY KEY,
	EmployeeID int,
	Title VARCHAR(50),
	SentDate datetime,
	TypeID int,
	Content TEXT,
	FilePath VARCHAR(255),
	Status bit,
	ProcessNote TEXT,
	ApproveDate datetime,
	ResponedBy int,
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (ResponedBy) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (TypeID) REFERENCES RequestsType(TypeID)
);

ALTER TABLE Departments
MODIFY COLUMN Name NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE EmployeeTypes
MODIFY COLUMN Name NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Roles
MODIFY COLUMN Name NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN FirstName NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN MiddleName NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN LastName NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN Email NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN Password NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN CCCD NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE Employees
MODIFY COLUMN PhoneNumber NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE Shifts
MODIFY COLUMN Name NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE Timesheet
MODIFY COLUMN FilePath NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Leaves
MODIFY COLUMN FilePath NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE Leaves
MODIFY COLUMN Reason NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE LeaveRequests
MODIFY COLUMN FilePath NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE LeaveRequests
MODIFY COLUMN Reason NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE OvertimeRequests
MODIFY COLUMN FilePath NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE OvertimeRequests
MODIFY COLUMN ProcessNote NVARCHAR(255) CHARACTER SET utf8mb4;

ALTER TABLE News
MODIFY COLUMN Title NVARCHAR(50) CHARACTER SET utf8mb4;

ALTER TABLE News
MODIFY COLUMN Content NVARCHAR(255) CHARACTER SET utf8mb4;
