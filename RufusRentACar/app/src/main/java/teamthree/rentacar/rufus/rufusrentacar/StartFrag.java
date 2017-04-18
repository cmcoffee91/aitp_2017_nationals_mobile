package teamthree.rentacar.rufus.rufusrentacar;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by user on 4/7/2017.
 */

public class StartFrag extends Fragment {

    private RecyclerView mCarRecyclerView;
    private CarAdapter mCarAdapter;
    private List<Car> mCarList ;
    private List<Car> mCarListSave ;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        System.out.println("in startFrag");


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.start_frag,container,false);


        mCarList = new ArrayList<>();


        Car_crud car_crud = Car_crud.get(getActivity());



        mCarList = car_crud.getCars(null,null,"id desc");





        mCarRecyclerView = (RecyclerView) v.findViewById(R.id.car_recycler_view);
        mCarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCarAdapter = new CarAdapter(mCarList,getActivity());
        mCarRecyclerView.setAdapter(mCarAdapter);

        mCarAdapter.notifyDataSetChanged();


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.search_item,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView1 = (SearchView) searchItem.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchQuerySubmit","query is : " + query);
                lookForMember(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchQueryOnTextChange","query textchange is : " + newText);
                return false;
            }
        });

        searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("search","search closed");
                lookForMember("");
                return false;
            }
        });

        MenuItem addCar = menu.findItem(R.id.menu_add_car);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_add_car:
                CM_fragment frag = new CM_fragment();
                frag.launchFragWithName(getActivity(),"AddCar",null);
                return true;
            case R.id.menu_sort:
                Car_crud car_crud = Car_crud.get(getActivity());
                mCarList = car_crud.getCars(null,null,"price asc");
                mCarAdapter.setmCarList(mCarList);
                mCarAdapter.notifyDataSetChanged();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        Car_crud car_crud = Car_crud.get(getActivity());
        mCarList = car_crud.getCars(null,null,null);
        if(mCarAdapter == null)
        {
            mCarAdapter = new CarAdapter(mCarList,getActivity());
            mCarRecyclerView.setAdapter(mCarAdapter);
        }
        else
        {
            mCarAdapter.setmCarList(mCarList);
            mCarAdapter.notifyDataSetChanged();
        }
    }

    public void lookForMember(String query)
    {
        Car_crud car_crud = Car_crud.get(getActivity());

        if(!query.trim().isEmpty())
        {


            mCarList.clear();
            mCarList = car_crud.getCars(CarDbSchema.CarTable.Cols.PRICE + " LIKE ?",new String[] {"%" + query +"%"},null);
            mCarAdapter.setmCarList(mCarList);
            mCarAdapter.notifyDataSetChanged();
        }
        else
        {
            mCarList.clear();
            mCarList = car_crud.getCars(null,null,null);
            mCarAdapter.setmCarList(mCarList);
            mCarAdapter.notifyDataSetChanged();
        }

    }

}
