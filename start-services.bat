@echo off
echo Starting ScheduleBuilder services...
docker-compose up --build -d
echo Services started successfully!
echo.
echo Application is running at: http://localhost:8080
echo Database is running at: localhost:5432
echo.
echo Press any key to exit...
pause > nul 