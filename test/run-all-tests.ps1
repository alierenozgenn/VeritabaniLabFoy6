
Write-Host "=== NoSQL Performance Test Suite ===" -ForegroundColor Magenta
Write-Host "Starting all performance tests..." -ForegroundColor Yellow
Write-Host ""


try {
    $testResponse = Invoke-RestMethod -Uri "http://localhost:8080/test" -TimeoutSec 5
    Write-Host "Server is running!" -ForegroundColor Green
} catch {
    Write-Host "Server is not running! Please start your Java application first." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Running Siege Tests - 1000 requests each..." -ForegroundColor Yellow


Write-Host ""
Write-Host "=== TESTING REDIS ===" -ForegroundColor Red
& ".\redis-test-windows.ps1"

Start-Sleep -Seconds 3


Write-Host ""
Write-Host "=== TESTING HAZELCAST ===" -ForegroundColor Blue
& ".\hazelcast-test-windows.ps1"

Start-Sleep -Seconds 3


Write-Host ""
Write-Host "=== TESTING MONGODB ===" -ForegroundColor Green
& ".\mongodb-test-windows.ps1"

Start-Sleep -Seconds 3

# Time Tests
Write-Host ""
Write-Host "=== RUNNING TIME TESTS ===" -ForegroundColor Cyan
& ".\time-test-windows.ps1"

Write-Host ""
Write-Host "=== ALL TESTS COMPLETED ===" -ForegroundColor Green
Write-Host "Check the following files for results:" -ForegroundColor Yellow
Write-Host "- redis-siege.results" -ForegroundColor White
Write-Host "- hz-siege.results" -ForegroundColor White
Write-Host "- mongodb-siege.results" -ForegroundColor White
Write-Host "- redis-time.results" -ForegroundColor White
Write-Host "- hz-time.results" -ForegroundColor White
Write-Host "- mongodb-time.results" -ForegroundColor White
