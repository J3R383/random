import java.util.Scanner;
public class Main {
public static void main(String[]args)
{
    Scanner input = new Scanner(System.in);
    int num1;
    String word1;
    System.out.print("Enter a number and I will echo it later: ");
    num1=input.nextInt();
    System.out.println("Enter a word and I will echo it out later: ");
    word1 = input.next();
    System.out.println("Here is the echho of what you have entered: ");
    System.out.println("The number yoou have entered is: "+ num1);
    System.out.println("The word you have entered is: "+ word1);
}
}