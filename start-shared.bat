@echo off
cd /d "%~dp0"
python judge.py --share %*
pause
