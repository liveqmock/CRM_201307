call mvn clean compile
if %errorlevel% ==1 (
	echo ***Error:����ʧ��***
	goto end
)
call mvn help:effective-pom  -Doutput=effective-pom.txt
echo ******************************************
echo mvn scm:checkin
echo ******************************************
call mvn scm:checkin -Dmessage=patch%date:~0,4%%date:~5,2%%date:~8,2%(BIZ)
if %errorlevel% ==1 (
	echo ***SVN�ύʧ��***
	goto end
)
echo ******************************************
echo mvn release:prepare -DdryRun=true
echo ******************************************
call mvn release:prepare -DdryRun=true 
if %errorlevel% ==1 (
	echo ***����������ʧ��***
	call mrb
	goto end
)
echo ******************************************
echo mvn release:prepare -Dresume=false
echo ******************************************
call mrp
if %errorlevel% ==1 (
	echo ***����Subversion Tagʧ��,��ʼ����***
	call mrb
	goto end
)
echo ******************************************
echo mvn release:perform
echo ******************************************
call mr
:end
