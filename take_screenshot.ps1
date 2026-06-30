param (
    [string]$name = "screenshot"
)

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

$artifactDir = "C:\Users\dudue\.gemini\antigravity\brain\f0d04e4d-db7d-4fd3-8320-41db79fd60f9"
if (-not (Test-Path $artifactDir)) {
    New-Item -ItemType Directory -Path $artifactDir | Out-Null
}

$filePath = Join-Path $artifactDir "$name.png"

# Aguarda meio segundo para atualizar a tela
Start-Sleep -Milliseconds 500

$bounds = [System.Windows.Forms.Screen]::PrimaryScreen.Bounds
$bmp = New-Object System.Drawing.Bitmap $bounds.Width, $bounds.Height
$graphics = [System.Drawing.Graphics]::FromImage($bmp)
$graphics.CopyFromScreen($bounds.Location, [System.Drawing.Point]::Empty, $bounds.Size)

$bmp.Save($filePath, [System.Drawing.Imaging.ImageFormat]::Png)
$graphics.Dispose()
$bmp.Dispose()

Write-Host "Captura de tela '$name' salva com sucesso em: $filePath" -ForegroundColor Green
