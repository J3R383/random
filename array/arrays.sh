 #!/bin/bash
#Configuring variables
CODE="arrays.c"
DIRECTORY="array"

#1.Checking for existence of directory
if [ -e "$DIRECTORY" ];then
		echo "$DIRECTORY exists"
	else
		mkdir -p $DIRECTORY
	fi
#2. Compilation of the C file
gcc "$CODE"

#. Checking for comilation errors
if [ ! -e err ];then
		echo "Compilation successful"
	else
		echo "Error in compilation"
	fi

./a.out
#Done
