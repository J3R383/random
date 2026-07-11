import java.util.Scanner;
public class SumNatural {
    @SuppressWarnings("empty-statement")
    public static void main(String[]args){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter N: ");
        int n = in.nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++);
            sum += i;
        System.out.println("Sum = "+ sum);
    }
}