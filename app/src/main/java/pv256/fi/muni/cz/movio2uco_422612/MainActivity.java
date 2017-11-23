package pv256.fi.muni.cz.movio2uco_422612;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;
import pv256.fi.muni.cz.movio2uco_422612.entities.MovieList;
import pv256.fi.muni.cz.movio2uco_422612.fragments.DetailFragment;
import pv256.fi.muni.cz.movio2uco_422612.fragments.MovieListFragment;


public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectListener, MovieListFragment.OnMovieLongClickListener {

    private static final String THEME = "mainTheme";
    private static final String APP = "movio";
    private boolean mTwoPane;
    private List<Object> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DetailFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
////        mData = new ArrayList<Object>(10);
////        for (int i = 0; i < 4; i++) {
////            if (i == 0) {
////                mData.add("Upcoming");
////            }
////            if (i == 2) {
////                mData.add("Favourite");
////            }
////            mData.add(new Movie(getCurrentTime().getTime(), "first cover", "Na první pohled jsou Burnhamovi šťastnou rodinou, jejíž život je zalit sluncem. Pohodlný dům, auto kvalitní značky, půvabná dospívající dcera, studující na správné škole, rodiče úspěšní ve svém povolání. Nikdo se tedy nediví, že paní Burnhamová (Annette Bening) je věčně samý úsměv - alespoň na veřejnosti. Ve skutečnosti je ale všechno jinak. Obraz dokonalosti matce Carolyn a dceři Jane neustále kazí otec Lester (Kevin Spacey). Není tím ramenatým, snědým \"plejbojem\" a co je horší, začíná stále častěji dávat najevo, že se mu nedaří a že je nespokojen se svým životem. Dokud jen odmítá roli, kterou stovky jemu podobných odevzdaně hrají, aby měli klid, je to pro rodinu sice obtížné, ale únosné. Ještě včera nerozhodný a zakřiknutý Lester se ovšem rozhodne \"krizi středního věku\" řešit nečekaně radikálně. Dojde mu totiž, o co v životě jde: o splnění snů a tužeb, které člověk má. Lester už nikdy nebude ubožák!", "Americka krasa", 0.0f, R.drawable.blade));
////            mData.add(new Movie(getCurrentTime().getTime(), "", "Sedmnáctiletá Angelika je proti své vůli provdána za bohatého toulouského hraběte Joffreye de Peyrac, který je nejen o dvanáct let starší než ona, ale má také pověst čaroděje. Svým šarmem a inteligencí si brzy získá Angeličino srdce a zdá se, že jejich šťastný život nemůže nic ohrozit. Jednoho dne je ale Peyrac uvržen do Bastily a upálen na hranici za údajné čarodějnictví. Angelika se uchýlí s dětmi na Dvůr zázraků a s pomocí banditů plánuje pomstu všem, kteří změnili její pohádku ve zlý sen.", "Angelika", 0.0f, R.drawable.thor));
////        }
//
//
//        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(this,mData);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public void onMovieSelect(int position) {
        Movie movie = (Movie)mData.get(position);
        if (mTwoPane) {
            FragmentManager fm = getSupportFragmentManager();

            DetailFragment fragment = DetailFragment.newInstance(movie);
            fm.beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailFragment.TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }

    @Override
    public void onMovieLongClick(int position) {
        Toast.makeText(this, ((Movie) mData.get(position)).getTitle(), Toast.LENGTH_SHORT).show();
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }


}
