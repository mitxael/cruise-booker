---------------------Creacion de Base de datos----------------------------
USE Master
GO

DECLARE @Database nvarchar(max) = 'db_GestioneCrociere';
DECLARE @DataPath nvarchar(max) = cast(serverproperty('InstanceDefaultDataPath') as nvarchar(200));
--C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\
DECLARE @Statement nvarchar(max);

IF EXISTS (SELECT Name FROM sysdatabases WHERE name IN (@Database))
BEGIN
   EXEC ('DROP DATABASE ' + @Database)
END

SET @Statement = N'CREATE DATABASE [' + @Database + '] 
	ON PRIMARY (
	NAME = ' + @Database + ', 
	FILENAME = ''' + @DataPath + @Database+ '.MDF'' , 
	SIZE = 100MB , 
	MAXSIZE = UNLIMITED, 
	FILEGROWTH = 10%
) 
	LOG ON (
	NAME = ' + @Database + '_Log, 
	FILENAME = ''' + @DataPath + @Database + + '_Log.LDF'', 
	SIZE = 10MB , 
	MAXSIZE = UNLIMITED, 
	FILEGROWTH = 10%
) collate Latin1_General_CI_AI'

--PRINT @Statement
EXEC (@Statement)

EXEC ('USE ' + @Database)
GO