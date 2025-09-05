@echo off
echo Building Online Quiz System...
call mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo Build failed. Please check the error messages above.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Starting Online Quiz System...
java -jar target/OnlineQuizSystem-1.0-SNAPSHOT.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Application failed to start. Please check the error messages above.
    pause
)
