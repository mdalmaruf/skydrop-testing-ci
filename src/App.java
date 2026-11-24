/** Prints the delivery plan for one sample order. */
public class App {

    public static void main(String[] args) {
        double weight = 1.2;     // kg
        double distance = 4.0;   // km
        int battery = 45;        // percent

        boolean carry = DeliveryRules.canCarry(weight);
        String size = DeliveryRules.packageSize(weight);
        double fee = DeliveryRules.deliveryFee(distance, weight, true);
        int minutes = DeliveryRules.estimateMinutes(distance);
        boolean batteryOk = DeliveryRules.hasEnoughBattery(distance, battery);

        System.out.println("SkyDrop delivery plan");
        System.out.println("Can carry?  " + carry);
        System.out.println("Size:       " + size);
        System.out.println("Fee:        $" + fee);
        System.out.println("Minutes:    " + minutes);
        System.out.println("Battery OK? " + batteryOk);
    }
}
