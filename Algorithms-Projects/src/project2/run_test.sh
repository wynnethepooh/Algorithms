#!/bin/bash

# This variables limits the size of the problem
maxN=4096

# Use this variable to specify the CSV file you want to write to
fileName='run_time.csv'

# Stores the name of the executable
execFile='java GraphAlgorithms'

# Check to see if the file exists. If so, then delete the file.
if [ -e $fileName ] 
then
	rm $fileName
fi

# Outermost for-loop controsl the algorithm that will be run
for i in `seq 1 5`;
do
	n=256
	# This while-loop handles increasing problem size from initial to maxN
	while [ "$n" -le "$maxN" ]
	do
		echo -n "$n," >> $fileName

		# This for-loop runs the same command multiple times in order tocalculate
		# the average time.
		for j in `seq 0 3`;
		do
			$execFile $i $n >> $fileName

			# Write to the file the elapsed times
			echo -n "," >> $fileName
			echo "Elapsed Time: $elapsedTime"
			echo "n: $n, i: $i"
		done
		echo "" >> $fileName
		n=$((2*$n))
	done
	echo "" >> $fileName
done
