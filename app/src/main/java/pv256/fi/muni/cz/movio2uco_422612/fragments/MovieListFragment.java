package pv256.fi.muni.cz.movio2uco_422612.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pv256.fi.muni.cz.movio2uco_422612.R;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 19.10.2017.
 */

public class MovieListFragment extends Fragment {
    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private OnMovieSelectListener mListener;
    private Context mContext;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (OnMovieSelectListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement OnMovieSelectListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null; //Avoid leaking the Activity
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list , container, false);

        mMovieList.add(new Movie( getCurrentTime().getTime(),"", "Na první pohled jsou Burnhamovi šťastnou rodinou, jejíž život je zalit sluncem. Pohodlný dům, auto kvalitní značky, půvabná dospívající dcera, studující na správné škole, rodiče úspěšní ve svém povolání. Nikdo se tedy nediví, že paní Burnhamová (Annette Bening) je věčně samý úsměv - alespoň na veřejnosti. Ve skutečnosti je ale všechno jinak. Obraz dokonalosti matce Carolyn a dceři Jane neustále kazí otec Lester (Kevin Spacey). Není tím ramenatým, snědým \"plejbojem\" a co je horší, začíná stále častěji dávat najevo, že se mu nedaří a že je nespokojen se svým životem. Dokud jen odmítá roli, kterou stovky jemu podobných odevzdaně hrají, aby měli klid, je to pro rodinu sice obtížné, ale únosné. Ještě včera nerozhodný a zakřiknutý Lester se ovšem rozhodne \"krizi středního věku\" řešit nečekaně radikálně. Dojde mu totiž, o co v životě jde: o splnění snů a tužeb, které člověk má. Lester už nikdy nebude ubožák!", "Americka krasa", 0.0f));
        mMovieList.add(new Movie( getCurrentTime().getTime(),"", "Sedmnáctiletá Angelika je proti své vůli provdána za bohatého toulouského hraběte Joffreye de Peyrac, který je nejen o dvanáct let starší než ona, ale má také pověst čaroděje. Svým šarmem a inteligencí si brzy získá Angeličino srdce a zdá se, že jejich šťastný život nemůže nic ohrozit. Jednoho dne je ale Peyrac uvržen do Bastily a upálen na hranici za údajné čarodějnictví. Angelika se uchýlí s dětmi na Dvůr zázraků a s pomocí banditů plánuje pomstu všem, kteří změnili její pohádku ve zlý sen.", "Angelika", 0.0f));
        mMovieList.add(new Movie( getCurrentTime().getTime(),"", "Závratná rychlost, adrenalin proudící v žilách, krásné dívky a nezkrotná touha po vítězství. Závody Formule 1 jsou místem, kde jediná chyba může znamenat smrt. Na těchto okruzích spolu soupeří dva odvěcí rivalové. James Hunt je neřízená střela, neodolatelný playboy a brilantní řidič. Niki Lauda je dokonalý profesionál, vždy precizní a disciplinovaný. Movie Rivalové vypráví skutečný příběh dvou pilotů, kteří jsou soky nejen na okruzích, ale také ve skutečném životě. Přestože jsou soupeři, jsou také jeden pro druhého inspirací. Přes jejich rozdílnost je v životě pojí zvláštní přátelství. Ale rivalita je žene ke stále riskantnějším výkonům. Titul mistra světa může získat jen jeden...", "Rivalove", 0.0f));

        mButton1 = (Button) view.findViewById(R.id.movie1);
        mButton2 = (Button) view.findViewById(R.id.movie2);
        mButton3 = (Button) view.findViewById(R.id.movie3);

        View.OnClickListener clickListener =  new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.movie1:
                        mListener.onMovieSelect(mMovieList.get(0));
                        break;
                    case R.id.movie2:
                        mListener.onMovieSelect(mMovieList.get(1));
                        break;
                    case R.id.movie3:
                        mListener.onMovieSelect(mMovieList.get(2));
                        break;
                }
            }
        };
        mButton1.setOnClickListener(clickListener);
        mButton2.setOnClickListener(clickListener);
        mButton3.setOnClickListener(clickListener);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public interface OnMovieSelectListener {
        void onMovieSelect(Movie movie);
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }

    //debug only
    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Log.d(TAG, " onStart method");
    }
    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        Log.d(TAG, " onResume method");
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        Log.d(TAG, " onPause method");
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        Log.d(TAG, " onStop method");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        Log.d(TAG, " onDestroy method");
    }   
}
