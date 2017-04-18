package teamthree.rentacar.rufus.rufusrentacar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = (View) findViewById(R.id.fragment_container);
        v.setBackgroundColor(Color.GREEN);


        FragmentManager fragmentManager = getFragmentManager();
        Fragment start_frag = fragmentManager.findFragmentById(R.id.fragment_container);
        if (start_frag == null) {

            start_frag = new StartFrag();


            fragmentManager.beginTransaction().add(R.id.fragment_container, start_frag).commit();
        }
    }
}
