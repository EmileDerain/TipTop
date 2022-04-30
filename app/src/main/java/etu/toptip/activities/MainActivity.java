package etu.toptip.activities;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.os.Bundle;
        import android.view.MenuItem;
        import android.widget.Toast;

        import com.google.android.material.bottomnavigation.BottomNavigationView;

        import java.util.Objects;

        import etu.toptip.R;
        import etu.toptip.databinding.ActivityMainBinding;
        import etu.toptip.fragments.AccueilFragment;
        import etu.toptip.fragments.GPSFragment;
        import etu.toptip.fragments.IGPS;
        import etu.toptip.fragments.ListFavorisFragment;
        import etu.toptip.fragments.MapsFragment;
        import etu.toptip.fragments.NavigationFragment;
        import etu.toptip.fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        bottomNavigationView= findViewById(R.id.nav_view);
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new MapsFragment()).commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        bottomNavigationView.setSelectedItemId(R.id.map);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){

                    case R.id.maps:
                        try {
                            fragment = new MapsFragment();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        break;

                    case R.id.accueil:
                        fragment = new AccueilFragment();
                        break;

                    case R.id.profil:
                        fragment = new ProfilFragment();
                        break;

                    case R.id.favoris:
                        fragment = new ListFavorisFragment();
                        break;


                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                return true;
            }
        });

    }


}