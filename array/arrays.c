#include <stdio.h>
int main(){
	int numbers[10] = {2, 3, 5, 7, 9, 10, 20, 30, 40, 50};
	int size = sizeof(numbers) / sizeof(numbers[0]);
int i =0;
int sum = 0;

do{
	sum += numbers[i];
	i++;
} while (i < size);

printf("Sum of the values is: %d\n", sum);
return 0;
}
