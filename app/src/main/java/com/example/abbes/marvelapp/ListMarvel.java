package com.example.abbes.marvelapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListMarvel extends Fragment {
public static boolean etat = true ;
public static boolean bsearch = false ;
public  ListView data ;
public  Bundle bundle ;

    public ListMarvel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.list_marvel, container, false);

        MarvelModel.Series st = new  MarvelModel.Series();

        final MarvelAdapter  adapter = new MarvelAdapter(ApplicationContextProvider.getContext());
        fetchdata.number = 0 ;
        data = v.findViewById(R.id.listMarvel);

        data.setAdapter(adapter);
        final EditText edittext = v.findViewById(R.id.search);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    fermerclavier();
                    bsearch = true ;
                    adapter.clear();

                    fetchdata process = new fetchdata(adapter,edittext.getText().toString());
                    process.execute();


                    Toast.makeText(getContext(),edittext.getText().toString(),Toast.LENGTH_LONG).show();


                return true;
                }
                return false;
            }
        });
        final String nom = "";
        fetchdata process = new fetchdata(adapter,nom);
        process.execute();

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  TextView mytv = (TextView) view.findViewById(R.id.nameTxt);

           //   String mytv = fetchdata.ModelList.get(i).getName();
                //Toast.makeText(getContext(),mytv.getText() ,Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("nom", String.valueOf(i));
                Toast.makeText(getContext(),String.valueOf(i),Toast.LENGTH_LONG).show();
              //  replacefragment();



            }
        });

        data.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if(i+i1 == i2 && i2!=0 && etat && edittext.getText().toString().equals(""))

                {

                        fetchdata.number = fetchdata.number + 20 ;

                        fetchdata process = new fetchdata(adapter,nom);
                        process.execute();
                        Toast.makeText(getContext(),String.valueOf(fetchdata.number),Toast.LENGTH_SHORT).show();
                        etat = false ;


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





}
