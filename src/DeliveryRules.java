/**
 * Business rules for SkyDrop drone deliveries.
 * Five small functions, each easy to test on its own.
 */
public class DeliveryRules {

    public static final double MAX_WEIGHT_KG = 2.5;
    public static final double BASE_FEE = 3.00;
    public static final double FEE_PER_KM = 1.50;
    public static final double HEAVY_SURCHARGE = 2.00;  // parcels over 1 kg
    public static final double EXPRESS_MULTIPLIER = 1.5;
    public static final double SPEED_KMH = 40.0;
    public static final int TAKEOFF_LANDING_MIN = 2;
    public static final double BATTERY_PER_KM = 5.0;    // percent per km flown
    public static final double RESERVE_PERCENT = 10.0;  // land with 10% left

    /** 1. Can the drone carry this parcel? More than 0 kg and at most 2.5 kg. */
    public static boolean canCarry(double weightKg) {
        return weightKg > 0 && weightKg <= MAX_WEIGHT_KG;
    }

    /** 2. Size label printed on the parcel: SMALL, MEDIUM or LARGE. */
    public static String packageSize(double weightKg) {
        if (!canCarry(weightKg)) {
            throw new IllegalArgumentException("Cannot carry " + weightKg + " kg");
        }
        if (weightKg < 0.5) {
            return "SMALL";
        }
        if (weightKg < 1.5) {
            return "MEDIUM";
        }
        return "LARGE";
    }

    /** 3. Delivery fee in dollars: base + per km, heavy surcharge, express x1.5. */
    public static double deliveryFee(double distanceKm, double weightKg,
                                     boolean express) {
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        double fee = BASE_FEE + FEE_PER_KM * distanceKm;
        if (weightKg > 1.0) {
            fee += HEAVY_SURCHARGE;
        }
        if (express) {
            fee *= EXPRESS_MULTIPLIER;
        }
        return Math.round(fee * 100) / 100.0;
    }

    /** 4. Flight time to the customer in whole minutes, rounded up. */
    public static int estimateMinutes(double distanceKm) {
        double minutes = distanceKm / SPEED_KMH * 60 + TAKEOFF_LANDING_MIN;
        return (int) Math.ceil(minutes);
    }

    /** 5. Enough battery to fly there AND back, keeping a 10% reserve? */
    public static boolean hasEnoughBattery(double distanceKm, int batteryPercent) {
        double needed = distanceKm * 2 * BATTERY_PER_KM + RESERVE_PERCENT;
        return batteryPercent >= needed;
    }

    /** 6. Weather check: no flying in rain, or when the wind is 40 km/h or more. */
    public static boolean isSafeToFly(double windKmh, boolean raining) {
        return windKmh <= 40 && !raining;
    }
}
