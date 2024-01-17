
 DROP DATABASE IF EXISTS Attendance_DB;
 CREATE DATABASE IF NOT EXISTS Attendance_DB;
 USE Attendance_DB;


#=================================================================================
CREATE TABLE Employees(
	`EmployeeID` int auto_increment PRIMARY KEY,
		
	`FirstName` nvarchar(50) not null,
	`MiddleName` nvarchar(50) not null,
	`LastName` nvarchar(50) not null,
	`Gender` bit,
	`BirthDate` date,
	`Email` nvarchar(50) UNIQUE not null,
	`Password` nvarchar(50),
	`CCCD` varchar(50) UNIQUE not null,
	`PhoneNumber` varchar(50) UNIQUE,
	`EmployeeTypeID` int,
	`DepartmentID` int,
	`RoleID` int DEFAULT 1,
	`StartDate` date,
	`EndDate` date ,
	`IsActive` bit DEFAULT 1
);




CREATE TABLE EmployeeTypes(
	EmployeeTypeID int auto_increment PRIMARY KEY,
	`Name` nvarchar(50)
);


CREATE TABLE Roles(
	`RoleID` int PRIMARY KEY,
	`Name` nvarchar(50)
);


CREATE TABLE Departments(
	`DepartmentID` int auto_increment PRIMARY KEY,
	`Name` nvarchar(50),
	`ManagerID` int
);

CREATE TABLE Shifts(
	`ShiftID` int auto_increment PRIMARY KEY,
	`Name` nvarchar(50),
	`StartTime` time,
	`EndTime` time
);

CREATE TABLE Timesheet(
	`TimeSheetID` int auto_increment PRIMARY KEY,
	`Date` date,
	`EmployeeID` int,
	`ShiftID` int,
	`CheckIn` time,
	`CheckOut` time,
#	`IsLeave` bit DEFAULT 0,
	`Note` nvarchar(20000),
	UNIQUE(`Date`, `EmployeeID`, `ShiftID`)
);

CREATE TABLE Leaves(
	TimeSheetID int PRIMARY KEY,
	Reason nvarchar(20000),
	`Status` bit,
	`ResponedBy` int
);



CREATE TABLE OverTime(
	`Date` date,
	`EmployeeID` int,
	`StartTime` time,
	`EndTime` time,
	`CheckIn` time,
	`CheckOut` time,
	`OpenBefore` int,
	`CloseAfter` int,
	PRIMARY KEY(`Date`, `EmployeeID`)
);


CREATE TABLE News(
	NewsID int PRIMARY KEY auto_increment,
	Title nvarchar(50),
	Content nvarchar(20000),
	`DateTime` datetime,
	`NewsTypeID` int,
	CreatedBy int
);

CREATE TABLE NewsTypes(
	NewsTypeID int PRIMARY KEY,
	`Name` nvarchar(50)
);

CREATE TABLE `RequestsType`(
	`TypeID` int PRIMARY KEY,
	`Name` nvarchar(50)
);

CREATE TABLE `Requests`(
	RequestID int auto_increment PRIMARY KEY,
	EmployeeID int,
	Title nvarchar(50),
	`TypeID` int,
	Content nvarchar(20000),
	`Status` bit,
	`ResponedBy` int
);

#CREATE TABLE`Shifts`(
#	`ShiftID` int auto_increment PRIMARY KEY,
#	`StartDate` date,
#	`StartTime` time,
#	`EndTime` time,
#	`OpenBefore` int,
#	`CloseAfter` int,
#	`CreatedBy` int 
#)

#=====================FOREIGN KEY
#Employees
ALTER TABLE Employees
ADD CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID);

ALTER TABLE Employees
ADD	CONSTRAINT FK_Employees_Roles FOREIGN KEY (`RoleID`) REFERENCES Roles(`RoleID`);


#Departments
ALTER TABLE Departments
ADD CONSTRAINT FK_Departments_Employees FOREIGN KEY(`ManagerID`) REFERENCES Employees(EmployeeID);

#Shift
#ALTER TABLE Shifts
#ADD CONSTRAINT FK_Shifts_Employees FOREIGN KEY(CreatedBY) REFERENCES Employees(EmployeeID)

#TimeSheets
ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Employees FOREIGN KEY (`EmployeeID`) REFERENCES Employees(EmployeeID);

ALTER TABLE Timesheet
ADD CONSTRAINT FK_Timesheet_Shifts FOREIGN KEY (`ShiftID`) REFERENCES Shifts(ShiftID);


#Leaves
ALTER TABLE Leaves
ADD CONSTRAINT FK_Timesheet_Leaves FOREIGN KEY (TimeSheetID) REFERENCES Timesheet(TimesheetID) ;

ALTER TABLE Leaves
ADD CONSTRAINT FK_Timesheet_Employees_ResponedBy FOREIGN KEY (`ResponedBy`) REFERENCES Employees(EmployeeID) ;


#OverTimes
ALTER TABLE OverTime
ADD CONSTRAINT FK_OverTime_Employees FOREIGN KEY (`EmployeeID`) REFERENCES Employees(EmployeeID);


#News
ALTER TABLE News
ADD CONSTRAINT FK_News_Employees_CreatedBy FOREIGN KEY (CreatedBy) REFERENCES Employees(EmployeeID);

ALTER TABLE News
ADD CONSTRAINT FK_News_NewsType FOREIGN KEY (NewsTypeID) REFERENCES NewsTypes(NewsTypeID);


#Requests:
ALTER TABLE `Requests`
ADD CONSTRAINT FK_Requests_Employees_ResponedBy FOREIGN KEY (ResponedBy) REFERENCES Employees(EmployeeID);

ALTER TABLE `Requests`
ADD CONSTRAINT FK_Requests_RequestsType FOREIGN KEY (`TypeID`) REFERENCES RequestsType(`TypeID`);

#EmployeeTypes
ALTER TABLE Employees
ADD CONSTRAINT FK_Employees_EmployeeTypes FOREIGN KEY (`EmployeeTypeID`) REFERENCES EmployeeTypes(EmployeeTypeID);








