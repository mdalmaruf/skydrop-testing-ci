import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DeliveryRulesTest {

    // ---------- canCarry ----------
    @Test
    void parcelAtTheLimitCanBeCarried() {
        assertTrue(DeliveryRules.canCarry(2.5));
    }

    @Test
    void parcelOverTheLimitIsRejected() {
        assertFalse(DeliveryRules.canCarry(2.6));
    }

    @Test
    void emptyParcelIsRejected() {
        assertFalse(DeliveryRules.canCarry(0));
    }

    // ---------- packageSize ----------
    @Test
    void lightParcelIsSmall() {
        assertEquals("SMALL", DeliveryRules.packageSize(0.3));
    }

    @Test
    void parcelOfOneKiloIsMedium() {
        assertEquals("MEDIUM", DeliveryRules.packageSize(1.0));
    }

    @Test
    void heavyParcelIsLarge() {
        assertEquals("LARGE", DeliveryRules.packageSize(2.0));
    }

    @Test
    void sizeOfTooHeavyParcelThrowsError() {
        assertThrows(IllegalArgumentException.class,
                () -> DeliveryRules.packageSize(3.0));
    }

    // ---------- deliveryFee ----------
    @Test
    void standardFeeForShortLightDelivery() {
        // 3.00 + 1.50 x 2 km = 6.00
        assertEquals(6.00, DeliveryRules.deliveryFee(2, 0.5, false), 0.001);
    }

    @Test
    void expressHeavyDeliveryCostsMore() {
        // (3.00 + 1.50 x 4 + 2.00) x 1.5 = 16.50
        assertEquals(16.50, DeliveryRules.deliveryFee(4, 1.2, true), 0.001);
    }

    @Test
    void zeroDistanceIsNotAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> DeliveryRules.deliveryFee(0, 1.0, false));
    }

    // ---------- estimateMinutes ----------
    @Test
    void fourKilometresTakeEightMinutes() {
        // 4 / 40 x 60 = 6 min flying + 2 min take-off and landing
        assertEquals(8, DeliveryRules.estimateMinutes(4));
    }

    @Test
    void partMinutesAreRoundedUp() {
        // 5 / 40 x 60 = 7.5 + 2 = 9.5, rounded up to 10
        assertEquals(10, DeliveryRules.estimateMinutes(5));
    }

    // ---------- hasEnoughBattery ----------
    @Test
    void shortTripWithFullBatteryIsFine() {
        assertTrue(DeliveryRules.hasEnoughBattery(2, 100));
    }

    @Test
    void batteryMustCoverTheReturnTrip() {
        // 8 km there + 8 km back = 16 km x 5% = 80%, plus 10% reserve = 90%
        assertFalse(DeliveryRules.hasEnoughBattery(8, 60));
    }

    // ---------- isSafeToFly ----------
    @Test
    void calmDryWeatherIsSafe() {
        assertTrue(DeliveryRules.isSafeToFly(15, false));
    }

    @Test
    void rainIsNotSafe() {
        assertFalse(DeliveryRules.isSafeToFly(10, true));
    }

    @Test
    void windOfFortyIsTooStrong() {
        assertFalse(DeliveryRules.isSafeToFly(40, false));
    }
}
