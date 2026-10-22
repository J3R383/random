import java.util.Scanner;

public class LargestNumber {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter three numbers: ");
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            int largest = a;
            if (b > largest)
                largest = b;
            if (c > largest)
                largest = c;
            System.out.println("Largest: " + largest);
        }
    }
}