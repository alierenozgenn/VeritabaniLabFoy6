@echo off
echo === NoSQL Performance Test Suite ===
echo.

REM PowerShell execution policy ayarla
powershell -Command "Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser -Force"

REM Test scriptlerini çalıştır
powershell -File "run-all-tests.ps1"

echo.
echo Press any key to exit...
pause > nul
