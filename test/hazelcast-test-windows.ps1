
Write-Host "=== MONGODB SIEGE TEST ===" -ForegroundColor Green

$stopwatch = [System.Diagnostics.Stopwatch]::StartNew()
$successCount = 0
$failedCount = 0
$jobs = @()


for ($i = 1; $i -le 10; $i++) {
    $job = Start-Job -ScriptBlock {
        param($clientId)
        $clientSuccess = 0
        $clientFailed = 0
        

        for ($j = 1; $j -le 100; $j++) {
            try {
                $response = Invoke-RestMethod -Uri "http://localhost:8080/nosql-lab-mon?student_no=2025000001" -Headers @{"Accept"="application/json"} -TimeoutSec 10
                if ($response) {
                    $clientSuccess++
                }
            } catch {
                $clientFailed++
            }
        }
        
        return @{
            Success = $clientSuccess
            Failed = $clientFailed
        }
    } -ArgumentList $i
    
    $jobs += $job
}

# Tum joblari bekle
$results = $jobs | Wait-Job | Receive-Job
$jobs | Remove-Job

# Sonuclari topla
foreach ($result in $results) {
    $successCount += $result.Success
    $failedCount += $result.Failed
}

$stopwatch.Stop()
$totalTime = $stopwatch.Elapsed.TotalSeconds
$availability = if ($successCount -gt 0) { ($successCount / 1000) * 100 } else { 0 }
$transactionRate = if ($totalTime -gt 0) { $successCount / $totalTime } else { 0 }
$responseTime = if ($successCount -gt 0) { $totalTime / $successCount } else { 999 }
$dataTransferred = $successCount * 0.0008
$throughput = if ($totalTime -gt 0) { $dataTransferred / $totalTime } else { 0 }

$result = @"
Transactions:                   $successCount hits
Availability:                 $([math]::Round($availability, 2)) %
Elapsed time:                  $([math]::Round($totalTime, 2)) secs
Data transferred:               $([math]::Round($dataTransferred, 2)) MB
Response time:                  $([math]::Round($responseTime, 4)) secs
Transaction rate:              $([math]::Round($transactionRate, 2)) trans/sec
Throughput:                     $([math]::Round($throughput, 2)) MB/sec
Concurrency:                    10.00
Successful transactions:        $successCount
Failed transactions:               $failedCount
"@

$result | Out-File -FilePath "mongodb-siege.results" -Encoding UTF8
Write-Host $result
Write-Host "Results saved to mongodb-siege.results" -ForegroundColor Yellow
