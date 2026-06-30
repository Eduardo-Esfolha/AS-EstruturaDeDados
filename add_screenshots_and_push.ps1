Param(
    [string]$Message = "Add project screenshots",
    [switch]$Push,
    [string]$Remote = "origin",
    [string]$Branch = ""
)

$ErrorActionPreference = 'Stop'

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $ScriptDir

if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error "Git não encontrado. Instale o Git e execute novamente."
    exit 1
}

$inside = & git rev-parse --is-inside-work-tree 2>$null
if ($LASTEXITCODE -ne 0) {
    Write-Output "Repositório Git não encontrado. Inicializando repositório..."
    git init
}

if (-not $Branch) {
    $br = & git rev-parse --abbrev-ref HEAD 2>$null
    if ($LASTEXITCODE -ne 0) { $Branch = "main" } else { $Branch = $br.Trim() }
}

Write-Output "Adicionando arquivos (screenshots/*.png e README.md)..."
# Tenta adicionar os arquivos; suprime erros caso não existam
git add screenshots/*.png 2>$null
git add README.md 2>$null

# Verifica se há algo para commitar
$porcelain = & git status --porcelain
if (-not $porcelain) {
    Write-Output "Nada a commitar."
    exit 0
}

Write-Output "Comitando com a mensagem: $Message"
git commit -m $Message

if ($Push) {
    Write-Output "Enviando (push) para $Remote/$Branch..."
    git push $Remote $Branch
}

Write-Output "Concluído."