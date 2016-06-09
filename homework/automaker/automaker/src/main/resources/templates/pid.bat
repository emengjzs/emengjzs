@echo off
netstat -ano | findstr %1
:end