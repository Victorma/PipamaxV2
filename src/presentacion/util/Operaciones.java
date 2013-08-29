package presentacion.util;

import java.math.BigDecimal;

public class Operaciones {
	
	public static double round(double value, int decimalDigits) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(decimalDigits, BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	}
	
	public static double applyDiscount(double value, double percent) {
	    return round(value - value*percent, 2);
	}
	
	public static double getDiscounted(double value, double percent) {
	    return round(value*percent, 2);
	}

}
