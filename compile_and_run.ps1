# PowerShell script to compile and run the Java strategic board game simulation.

# Ensure the output directory exists
if (-not (Test-Path bin)) {
    New-Item -ItemType Directory -Path bin | Out-Null
}

Write-Host "Compilando as estruturas de dados e regras do jogo..." -ForegroundColor Cyan

# Compile all Java source files
javac -d bin -encoding UTF-8 src/datastructures/*.java src/game/*.java src/Main.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilação concluída com sucesso! Iniciando o jogo..." -ForegroundColor Green
    # Run the application with UTF-8 encoding support
    java -cp bin Main
} else {
    Write-Host "Falha na compilação!" -ForegroundColor Red
}
