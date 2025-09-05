@echo off
echo Setting up Online Quiz System database...

echo Please enter MySQL root password:
set /p mysql_password=

echo Creating database and user...
mysql -u root -p%mysql_password% -e "CREATE DATABASE IF NOT EXISTS quiz_system;"
mysql -u root -p%mysql_password% -e "CREATE USER IF NOT EXISTS 'quizuser'@'localhost' IDENTIFIED BY 'quizpass';"
mysql -u root -p%mysql_password% -e "GRANT ALL PRIVILEGES ON quiz_system.* TO 'quizuser'@'localhost';"
mysql -u root -p%mysql_password% -e "FLUSH PRIVILEGES;"

echo Importing database schema and sample data...
mysql -u quizuser -pquizpass quiz_system < src\main\resources\database.sql

echo.
echo Database setup completed successfully!
echo.
echo You can now run the application using: build_and_run.bat

pause
