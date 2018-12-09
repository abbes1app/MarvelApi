package com.example.abbes.marvelapp.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abbes.marvelapp.Autre.AppContext;
import com.example.abbes.marvelapp.Adapter.MarvelAdapter;
import com.example.abbes.marvelapp.Parsing.fetchdata;
import com.example.abbes.marvelapp.R;


public class ListMarvel extends Fragment {
public static boolean Chargement = false ;
public static boolean bsearch = false ;
public  ImageView NoWifi ;
public ProgressBar load;
public  ListView data ;
public  Bundle bundle ;

    public ListMarvel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.list_marvel, container, false);
        getActivity().setTitle("Accueil");
        data = v.findViewById(R.id.listMarvel);
        NoWifi = v.findViewById(R.id.Nowifi);
        load = v.findViewById(R.id.load);
        fetchdata.offset = 0 ;

        final MarvelAdapter adapter = new MarvelAdapter(AppContext.getContext());



        // Liste vide au lancement
        data.setAdapter(adapter);

        // Verification de La Connexion Reseau
        if(TestInternet(AppContext.getContext())) {

            fetchdata process = new fetchdata(adapter);
            process.execute();

        }
        else{
            Toast.makeText(AppContext.getContext(),
                    "Verifier VOtre Connexion",Toast.LENGTH_LONG).show();
            NoWifi.setVisibility(View.VISIBLE);
        }


        // Recherche par Nom
        final EditText  ChampRecherche = v.findViewById(R.id.search);

        ChampRecherche.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)
                        && TestInternet(AppContext.getContext())) {

                    fermerclavier();
                    bsearch = true ;
                    MarvelAdapter.clear();
                    fetchdata process = new fetchdata(adapter,ChampRecherche.getText().toString());
                    process.execute();
                    ChampRecherche.setText("");

                return true;
                }
                return false;
            }
        });


        // Quand on clique sur un Item pour voir les details

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // On lui envoie son Nom pour récupérer l'objet de la classe MarvelModel
                bundle = new Bundle();
                bundle.putString("nom", String.valueOf(i));
                // On lance le fragment description
                replacefragment();

            }
        });

        //On relance l'API quand on arrive a la fin de la liste

        data.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if(i+i1 == i2 && i2!=0
                        && !Chargement
                        && !bsearch
                        && fetchdata.count == fetchdata.limit
                        && TestInternet(AppContext.getContext())) {

                    fetchdata.offset = fetchdata.offset + 20 ;
                    fetchdata process = new fetchdata(adapter);
                    process.execute();
                    Chargement = true ;

                }

            }

        });

    return v ;

    }

    private void fermerclavier() {

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void replacefragment() {
        Fragment fragment;
        fragment = new DescriptionModel();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static boolean TestInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }



}
