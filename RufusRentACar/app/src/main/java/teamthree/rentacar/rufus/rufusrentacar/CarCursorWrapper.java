package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */

import teamthree.rentacar.rufus.rufusrentacar.CarDbSchema.CarTable;
import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.UUID;

public class CarCursorWrapper extends CursorWrapper

{
    public CarCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Car getCar() {
        String id = getString(getColumnIndex(CarTable.Cols.ID));
        String year = getString(getColumnIndex(CarTable.Cols.YEAR));
        String make = getString(getColumnIndex(CarTable.Cols.MAKE));
        String model = getString(getColumnIndex(CarTable.Cols.MODEL));
        String pic = getString(getColumnIndex(CarTable.Cols.PIC_LOC));
        String price = getString(getColumnIndex(CarTable.Cols.PRICE));
        String rented = getString(getColumnIndex(CarTable.Cols.RENTED));
        String vin = getString(getColumnIndex(CarTable.Cols.VIN));
        String date = getString(getColumnIndex(CarTable.Cols.DATE));
        String mileage = getString(getColumnIndex(CarTable.Cols.MILEAGE));
        String notes = getString(getColumnIndex(CarTable.Cols.NOTES));

        Car car = new Car();
        car.setYear(year);
        car.setMake(make);
        car.setModel(model);
        car.setPicLoc(pic);
        car.setPrice(price);
        car.setRented(rented);
        car.setVin(vin);
        car.setDate(date);
        car.setMileage(mileage);
        car.setNotes(notes);
        car.setId(UUID.fromString(id));


        return car;
    }
}
