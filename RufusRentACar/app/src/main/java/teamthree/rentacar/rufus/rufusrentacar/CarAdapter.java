package teamthree.rentacar.rufus.rufusrentacar;

/**
 * Created by user on 4/7/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private List<Car> mMemberList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button deleteBut;
        public ImageView imageView;
        private File mPhotoFile;

        public MyViewHolder(View view) {
            super(view);


            imageView = (ImageView)view.findViewById(R.id.imageView) ;


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Car car = mMemberList.get(getAdapterPosition());
                    Bundle args = new Bundle();
                    args.putSerializable("car", car);
                    CM_fragment updateDetailFrag = new CM_fragment();
                    FragmentActivity mycontext = (FragmentActivity)mContext;
                    updateDetailFrag.launchFragWithName(mycontext,"Car_update_detail",args);


                }
            });


            name = (TextView) view.findViewById(R.id.name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            deleteBut = (Button)view.findViewById(R.id.button7);
            deleteBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Car car = mMemberList.get(getAdapterPosition());
                    Car_crud car_crud = Car_crud.get(mContext);
                    car_crud.deleteCar(car);
                    mMemberList.clear();
                    mMemberList = car_crud.getCars(null,null,null);
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void setmCarList(List memberList)
    {
        mMemberList = memberList;
    }



    public CarAdapter(List<Car> memberList, Context context) {
        this.mMemberList = memberList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_list_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Car car = mMemberList.get(position);
        holder.name.setText(car.getYear() + " " + car.getMake() + " " + car.getModel());


        PictureUtils pictureUtils = new PictureUtils();
        File mPhotoFile = pictureUtils.getPhotoFile(mContext,car);
        System.out.println(mPhotoFile.getPath());

        File f = new File(mPhotoFile.getPath());
        Glide.with(mContext)
                .load(new File(f.getPath())) // Uri of the picture
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }
}