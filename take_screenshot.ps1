param (
    [string]$name = "screenshot"
)

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# Diretório de screenshots do projeto (relativo ao script)
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectScreenshotsDir = Join-Path $scriptDir "screenshots"

if (-not (Test-Path $projectScreenshotsDir)) {
    New-Item -ItemType Directory -Path $projectScreenshotsDir | Out-Null
}

$filePath = Join-Path $projectScreenshotsDir "$name.png"

# Aguarda meio segundo para atualizar a tela
Start-Sleep -Milliseconds 500

$bounds = [System.Windows.Forms.Screen]::PrimaryScreen.Bounds
$bmp = New-Object System.Drawing.Bitmap $bounds.Width, $bounds.Height
$graphics = [System.Drawing.Graphics]::FromImage($bmp)
$graphics.CopyFromScreen($bounds.Location, [System.Drawing.Point]::Empty, $bounds.Size)

$bmp.Save($filePath, [System.Drawing.Imaging.ImageFormat]::Png)
$graphics.Dispose()
$bmp.Dispose()

Write-Host "Captura de tela '$name' salva com sucesso no projeto: $filePath" -ForegroundColor Green

# Salvar também uma cópia no diretório de artefatos atual se necessário
$geminiDir = "C:\Users\dudue\.gemini\antigravity\brain\a173e005-48a6-4b3e-81c2-1c13c0fd4a6f"
if (Test-Path $geminiDir) {
    Copy-Item $filePath (Join-Path $geminiDir "$name.png") -Force
}
