package com.example.abbes.marvelapp;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemList> MenuGauche;
    private ListView listMenuGauche;
    private DrawerLayout MenuGaucheLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //Init component
        listMenuGauche = findViewById(R.id.list_menu);
        MenuGaucheLayout = findViewById(R.id.meun_gauche_layout);
        MenuGauche = new ArrayList<>();
        //Add item for sliding list
        MenuGauche.add(new ItemList(R.drawable.marvel,"Accueil" ));
        MenuGauche.add(new ItemList(R.drawable.marvel,"Favoris"));


        MenuGaucheAdapter  adapter = new MenuGaucheAdapter(this, MenuGauche);
        listMenuGauche.setAdapter(adapter);

        //Display icon to open/close sliding pastille_liste
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //item selected
        listMenuGauche.setItemChecked(0, true);
        //Close menu
        MenuGaucheLayout.closeDrawer(listMenuGauche);

        //Display fragment 1 when start

          replaceFragment(0);

        //Hanlde on item click

        listMenuGauche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                setTitle(MenuGauche.get(position).getTitle());
                //item selected
                listMenuGauche.setItemChecked(position, true);



                //Replace fragment
                replaceFragment(position);


                //Close menu
                MenuGaucheLayout.closeDrawer(listMenuGauche);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, MenuGaucheLayout, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

fetchdata.offset = 0 ;


        if (ListMarvel.bsearch) {
            //additional code
            replaceFragment(0);
            Toast.makeText(getApplicationContext(),"je clique",Toast.LENGTH_LONG).show();
        ListMarvel.bsearch = false ;
        }
        else {
            super.onBackPressed();
        }


    }

//Create method replace fragment

    private void replaceFragment(int pos) {

        android.app.Fragment fragment = null;
        switch (pos) {

            case 0:
                fragment = new ListMarvel();
                break;
            case 1:
                fragment = new favoris();
                break;
        }

        if(null!=fragment) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



}

