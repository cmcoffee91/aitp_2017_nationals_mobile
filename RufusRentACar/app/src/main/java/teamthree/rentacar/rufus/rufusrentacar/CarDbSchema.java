package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */

public class CarDbSchema {
    public static final class CarTable {
        public static final String NAME = "cars";

        public static final class Cols {
            public static final String ID = "id";
            public static final String YEAR = "year";
            public static final String MAKE = "make";
            public static final String MODEL = "model";
            public static final String PRICE = "price";
            public static final String RENTED = "rented";
            public static final String VIN = "vin";
            public static final String PIC_LOC = "pic_loc";
            public static final String DATE = "date";
            public static final String MILEAGE = "mileage";
            public static final String NOTES = "notes";

        }
    }
}