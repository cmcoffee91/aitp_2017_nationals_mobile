package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import teamthree.rentacar.rufus.rufusrentacar.CarDbSchema.CarTable;

public class CarBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CarBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "rufusrentacar.db";

    public CarBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + CarTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CarTable.Cols.ID + ", " +
                CarTable.Cols.YEAR + ", " +
                CarTable.Cols.MAKE + ", " +
                CarTable.Cols.MODEL + ", " +
                CarTable.Cols.PRICE + ", " +
                CarTable.Cols.RENTED + ", " +
                CarTable.Cols.VIN + ", " +
                CarTable.Cols.DATE + ", " +
                CarTable.Cols.MILEAGE + ", " +
                CarTable.Cols.NOTES + ", " +
                CarTable.Cols.PIC_LOC +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}