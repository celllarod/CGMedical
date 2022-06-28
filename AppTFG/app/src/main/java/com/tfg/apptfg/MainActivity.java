package com.tfg.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.tfg.apptfg.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button btLogout;
    private TextView txUserNav;
    private TextView txRolNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager session = SessionManager.get(getApplicationContext());
        // Se comprueba sesión
        if(Objects.isNull(session.getUserToken())) {
            Log.d("[CPMEDICAL][SESSION]", "No hay sesión en SharedPreferences");
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
        } else {
            Log.d("[CPMEDICAL][SESSION]", "Sesión activa en SharedPreferences:" + SessionManager.get(getApplicationContext()).getUserEmail());

            ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setSupportActionBar(binding.appBarMain.toolbar);
            drawer = binding.drawerLayout;
            navigationView = binding.navView;
            btLogout = binding.btLogout;
            View headerView = binding.navView.getHeaderView(0);
            txUserNav = (TextView) headerView.findViewById(R.id.tx_user_nav);
            txRolNav = (TextView) headerView.findViewById(R.id.tx_rol_nav);

            this.inicializarNavigationView(session);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void inicializarNavigationView(SessionManager session){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mezclas, R.id.nav_catalogo, R.id.nav_dosimetro)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Inicializar elementos
        btLogout.setOnClickListener(v -> {
            SessionManager.destroy(this.getApplicationContext());
            Intent intent = new Intent(v.getContext(), InicioActivity.class);
            startActivity(intent);
        });

        if(session !=  null) {
            Log.d("SESSION", session.getUserEmail() + "--" + session.getUserRol());
            txUserNav.setText(session.getUserEmail());
            txRolNav.setText(session.getUserRol());
        }
    }
}