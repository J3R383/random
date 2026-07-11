import java.util.*;

class FarenheitToCelcius {
    public static void main(String[] args) {
        float temperature;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter temperature in Farenheit: ");
        temperature = in.nextInt();
        temperature = ((temperature - 32) * 5) / 9;
        System.out.println("Temperature in Celcius = " + temperature);
    }
}