package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */
import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class AddCar extends Fragment {
    EditText year, model, make, notes, vin, mileage, price;
    TextView date;
    Switch rented;
    File mPhotoFile;
    ImageView picView;
    Car car;
    private static final int REQUEST_PHOTO= 2;
    public static int VIDEO_CAPTURED = 1;
    private GoogleApiClient mClient;
    String longitude, latitude;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.car_detail_view,container,false);

        Bundle args = getArguments();




        year = (EditText) v.findViewById(R.id.year);
        make = (EditText) v.findViewById(R.id.make);
        model = (EditText) v.findViewById(R.id.model);
        year = (EditText) v.findViewById(R.id.year);
        vin = (EditText) v.findViewById(R.id.vin);
        notes = (EditText) v.findViewById(R.id.notes);
        price = (EditText) v.findViewById(R.id.price);
        rented = (Switch) v.findViewById(R.id.rented);
        mileage = (EditText) v.findViewById(R.id.mileage);
        date = (TextView) v.findViewById(R.id.date);
        CM_dates dates = new CM_dates();
        date.setText(dates.getCurrentDate() + ", " + dates.getCurrentTime());


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();

        car = new Car();
        UUID id = UUID.randomUUID();
        System.out.println("random id is " + id.toString());
        car.setId(id);

        PictureUtils pictureUtils = new PictureUtils();
        mPhotoFile = pictureUtils.getPhotoFile(getActivity(),car);


        Button takePic = (Button) v.findViewById(R.id.button8);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        takePic.setEnabled(canTakePhoto);





        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            System.out.println("can take photo");
            System.out.println("photoFile:" + mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);



            }
        });

        // Button save = (Button) v.findViewById(R.id.button9);
        picView = (ImageView)v.findViewById(R.id.imageView2);
        picView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        v.setBackgroundColor(Color.BLUE);



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {

            setPhotoView();

        }


    }



    private void setPhotoView()
    {
        try {
            File f = new File(mPhotoFile.getPath());
            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

            Matrix mat = new Matrix();
            mat.postRotate(angle);

            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
            Bitmap finalBm = PictureUtils.getScaledBitmap(correctBmp,400,400);
            Bitmap circleBm = PictureUtils.getclip(finalBm);
            Drawable d = new BitmapDrawable(finalBm);
            //RoundedBitmapDrawable circleWithBorder = PictureUtils.createRoundedBitmapDrawableWithBorder(finalBm,getActivity());

            //picView.setImageDrawable(circleWithBorder);
            picView.setImageBitmap(finalBm);
        }
        catch (IOException e) {
            Log.w("TAG", "-- Error in setting image");
        }
        catch(OutOfMemoryError oom) {
            Log.w("TAG", "-- OOM Error in setting image");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_add,menu);
        MenuItem savePlace =  menu.findItem(R.id.menu_save);
        savePlace.setTitle("save");



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.menu_save:
                Car_crud crud = Car_crud.get(getActivity());
                Car car_update = new Car();
                car.setDate(date.getText().toString());
                car.setYear(year.getText().toString());
                car.setMake(make.getText().toString());
                car.setModel(model.getText().toString());
                car.setPicLoc(mPhotoFile.getPath());
                System.out.println("price in add:"+ price.getText().toString());

                car.setPrice(price.getText().toString());
                System.out.println("price in car in add:"+ car.getPrice());
                if(rented.isChecked()) {
                    car.setRented("1");
                }
                else
                {
                    car.setRented("0");
                }
                car.setVin(vin.getText().toString());
                car.setMileage(mileage.getText().toString());
                car.setNotes(notes.getText().toString());
                crud.addCar(car);
                CM_fragment frag = new CM_fragment();
                frag.popFragByName(getActivity(),"AddCar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}

