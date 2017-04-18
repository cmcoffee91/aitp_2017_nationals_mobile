package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import teamthree.rentacar.rufus.rufusrentacar.CarDbSchema.CarTable;

public class Car_crud {
    private static Car_crud sCar_crud;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Car_crud get(Context context) {
        if (sCar_crud == null) {
            sCar_crud = new Car_crud(context);
        }
        return sCar_crud;
    }

    private Car_crud(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CarBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Car car) {
        ContentValues values = new ContentValues();
        values.put(CarTable.Cols.ID, car.getId().toString());
        values.put(CarTable.Cols.YEAR, car.getYear());
        values.put(CarTable.Cols.MAKE, car.getMake());
        values.put(CarTable.Cols.MODEL, car.getModel());
        values.put(CarTable.Cols.PRICE, car.getPrice());
        values.put(CarTable.Cols.RENTED, car.getRented());
        values.put(CarTable.Cols.VIN, car.getVin());
        values.put(CarTable.Cols.DATE, car.getDate());
        values.put(CarTable.Cols.MILEAGE, car.getMileage());
        values.put(CarTable.Cols.NOTES, car.getNotes());
        values.put(CarTable.Cols.PIC_LOC, car.getPic_loc());

        return values;
    }

    public void addCar(Car car) {
        ContentValues values = getContentValues(car);

        System.out.println("in addCar():" + car.getPrice());

        mDatabase.insert(CarTable.NAME, null, values);
    }

    public void updateCar(Car car) {
        String IDString = car.getId().toString();
        ContentValues values = getContentValues(car);

        mDatabase.update(CarTable.NAME, values,
                CarTable.Cols.ID + " = ?",
                new String[] { IDString });
    }

    public void deleteCar(Car car) {
        String IDString = car.getId().toString();
        mDatabase.delete(CarTable.NAME, CarTable.Cols.ID + " = ? ",
                new String[] { IDString });

    }

    private CarCursorWrapper queryCar(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                CarTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy  // orderBy
        );

        return new CarCursorWrapper(cursor);
    }


    public List<Car> getCars(String whereClause, String[] whereArgs, String orderBy) {
        List<Car> members = new ArrayList<>();

        CarCursorWrapper cursor = queryCar(whereClause, whereArgs, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            members.add(cursor.getCar());
            cursor.moveToNext();
        }
        cursor.close();

        return members;
    }

    public Car getCar(Integer id) {
        CarCursorWrapper cursor = queryCar(
                CarTable.Cols.ID + " = ?",
                new String[] { id.toString() }, null
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCar();
        } finally {
            cursor.close();
        }
    }
}
