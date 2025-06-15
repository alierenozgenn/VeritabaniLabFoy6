# Time Test - Windows 10 Compatible
Write-Host "=== TIME TESTS ===" -ForegroundColor Green

# Redis Time Test
Write-Host "Redis Time Test..." -ForegroundColor Cyan
$redisTime = Measure-Command {
    $jobs = @()
    
    # 10 paralel job, her biri 10 istek toplam 100
    for ($i = 1; $i -le 10; $i++) {
        $job = Start-Job -ScriptBlock {
            for ($j = 1; $j -le 10; $j++) {
                try {
                    Invoke-RestMethod -Uri "http://localhost:8080/nosql-lab-rd?student_no=2025000001" -TimeoutSec 5 | Out-Null
                } catch {
                    # Hatalari sessizce gec
                }
            }
        }
        $jobs += $job
    }
    
    $jobs | Wait-Job | Out-Null
    $jobs | Remove-Job
}

"Redis Execution time: $($redisTime.TotalSeconds)" | Out-File -FilePath "redis-time.results" -Encoding UTF8

# Hazelcast Time Test  
Write-Host "Hazelcast Time Test..." -ForegroundColor Cyan
$hzTime = Measure-Command {
    $jobs = @()
    
    # 10 paralel job, her biri 10 istek toplam 100
    for ($i = 1; $i -le 10; $i++) {
        $job = Start-Job -ScriptBlock {
            for ($j = 1; $j -le 10; $j++) {
                try {
                    Invoke-RestMethod -Uri "http://localhost:8080/nosql-lab-hz?student_no=2025000001" -TimeoutSec 5 | Out-Null
                } catch {
                    # Hatalari sessizce gec
                }
            }
        }
        $jobs += $job
    }
    
    $jobs | Wait-Job | Out-Null
    $jobs | Remove-Job
}

"Hazelcast Execution time: $($hzTime.TotalSeconds)" | Out-File -FilePath "hz-time.results" -Encoding UTF8

# MongoDB Time Test
Write-Host "MongoDB Time Test..." -ForegroundColor Cyan
$mongoTime = Measure-Command {
    $jobs = @()
    
    # 10 paralel job, her biri 10 istek toplam 100
    for ($i = 1; $i -le 10; $i++) {
        $job = Start-Job -ScriptBlock {
            for ($j = 1; $j -le 10; $j++) {
                try {
                    Invoke-RestMethod -Uri "http://localhost:8080/nosql-lab-mon?student_no=2025000001" -TimeoutSec 5 | Out-Null
                } catch {
                    # Hatalari sessizce gec
                }
            }
        }
        $jobs += $job
    }
    
    $jobs | Wait-Job | Out-Null
    $jobs | Remove-Job
}

"MongoDB Execution time: $($mongoTime.TotalSeconds)" | Out-File -FilePath "mongodb-time.results" -Encoding UTF8

Write-Host ""
Write-Host "=== FINAL RESULTS ===" -ForegroundColor Green
Write-Host "Redis: $($redisTime.TotalSeconds) seconds" -ForegroundColor White
Write-Host "Hazelcast: $($hzTime.TotalSeconds) seconds" -ForegroundColor White
Write-Host "MongoDB: $($mongoTime.TotalSeconds) seconds" -ForegroundColor White
