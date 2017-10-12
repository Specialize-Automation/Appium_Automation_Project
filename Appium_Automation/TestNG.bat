Echo off
Title TestNG Suite XML
::-----------------------Setting Project Directory Either CD/Pushd---------------------------
Echo Setting Project Location E:\Eclipse_selenium\Selenium_Automation
set projectLocation=E:\Eclipse_selenium\Selenium_Automation
pushd %projectLocation%
Timeout 1
::---------------------Setting Classpath-----------------------------
Echo Setting classpath JAR path;ClassFiles path
set classpath=C:\selenium-2.48.2\libs\*;E:\Eclipse_selenium\Selenium_Automation\bin
Timeout 1
::--------------------Executing TestNG XML------------------------------
Echo Executing TestNG.XML 
java org.testng.TestNG dummy.xml
::--------------------------------------------------
pause