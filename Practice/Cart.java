public class Cart {
    // main method
    public static void main(String[] args) {

        // total purchases > 5500 > 10%(0.1)

        // shopping cart of 3 items
        int milk_price = 65;
        int rice_price = 240;
        int office_chair = 5200;

        // calculate the discount
        // calculate the total
        double total_purchases = (milk_price + rice_price + office_chair);

        // decision making
        if (total_purchases > 5500) {
            double discount = (total_purchases * 0.1);
            System.out.println("Your Discount is : " + discount);
        } else {
            System.out.println("Do more purchases to get a discount.");
        }
    }
}