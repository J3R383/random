import java.util.Scanner;
public class SimpleInterest{
public static void main(String[]args){
    Scanner in = new Scanner(System.in);
    System.out.print("Enter Principal, Rate and Time: ");
    double p = in.nextDouble();
    double r = in.nextDouble();
    double t = in.nextDouble();
    double si = (p*r*t)/100;
    System.out.println("Simple Interest:" + si);
    }
}