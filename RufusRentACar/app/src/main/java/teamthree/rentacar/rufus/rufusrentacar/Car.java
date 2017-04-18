package teamthree.rentacar.rufus.rufusrentacar;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by user on 4/7/2017.
 */

public class Car implements Serializable {

    String year, make, model, vin, price, rented, date, mileage, notes, pic_loc ;
    UUID id;

   public void setYear(String year)
    {
        this.year = year;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setMileage(String mileage)
    {
        this.mileage = mileage;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public void setPicLoc(String pic_loc)
    {
        this.pic_loc = pic_loc;
    }

    public void setRented(String rented)
    {
        this.rented = rented;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }


    public UUID getId()
    {
        return id;
    }


    public String getYear()
    {
        return year;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public String getVin()
    {
        return vin;
    }

    public String getPrice()
    {
        return price;
    }

    public String getRented()
    {
        return rented;
    }

    public String getDate()
    {
        return date;
    }

    public String getMileage()
    {
        return mileage;
    }

    public String getNotes()
    {
        return notes;
    }

    public String getPic_loc()
    {
        return pic_loc;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }



}
