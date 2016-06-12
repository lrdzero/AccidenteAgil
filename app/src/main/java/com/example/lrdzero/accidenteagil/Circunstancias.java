package com.example.lrdzero.accidenteagil;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Circunstancias extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static Integer [] respuestas;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        respuestas = new Integer[17];
        for(int i=0;i<17;i++){
            respuestas[i]=0;
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seleccion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 final Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_seleccion, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView preguntas = (TextView) rootView.findViewById(R.id.preguntas);
            Button finaliz =(Button)rootView.findViewById(R.id.finaliza);

            if(getArguments().get(ARG_SECTION_NUMBER)!=17) {
                finaliz.setEnabled(false);
                finaliz.setVisibility(View.INVISIBLE);
            }

            final ImageView vehiculoA =(ImageView) rootView.findViewById(R.id.vehiculoA);
            final ImageView vehiculoB =(ImageView) rootView.findViewById(R.id.vehiculoB);

            if(respuestas[getArguments().getInt(ARG_SECTION_NUMBER)-1]==1){
                vehiculoA.setImageResource(R.drawable.autos);
            }
            else if(respuestas[getArguments().getInt(ARG_SECTION_NUMBER)-1]==2){
                vehiculoB.setImageResource(R.drawable.autos);
            }
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            String [] circunstancias =getResources().getStringArray(R.array.preguntas_format);
            preguntas.setText(circunstancias[getArguments().getInt(ARG_SECTION_NUMBER)-1]);

            vehiculoA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vehiculoA.setImageResource(R.drawable.autos);
                    vehiculoB.setImageResource(R.drawable.vehiculo);
                    respuestas[getArguments().getInt(ARG_SECTION_NUMBER) - 1] = 1;

                }
            });

            vehiculoB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vehiculoB.setImageResource(R.drawable.autos);
                    vehiculoA.setImageResource(R.drawable.vehiculo);
                    respuestas[getArguments().getInt(ARG_SECTION_NUMBER)-1]=2;
                }
            });

            finaliz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent=new Intent(getContext(),Posicionamiento.class);
                    for(int i=0;i<respuestas.length;i++)
                    myIntent.putExtra("vector"+i,respuestas[i]);
                    startActivity(myIntent);
                }
            });


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 17;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
