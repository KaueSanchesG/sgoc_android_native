package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.ui.about.AboutActivity;
import br.edu.utfpr.kaue.sgoc.ui.util.BottomNavListener;

public class ListClientActivity extends AppCompatActivity {

    private MenuItem menuClientSorting;
    private MenuItem menuTheme;
    private BottomNavigationView bottomNav;


    public static final String SHARED_PREFERENCES_PATH = "br.edu.utfpr.kaue.sgoc.PREFERENCES";
    public static final String KEY_LIGHT_THEME = "LIGHT_THEME";
    // mudar para client e refatorar cod original
    public static final String KEY_ASC_SORT = "ASCENDING_SORT";



    private boolean lightTheme = true;
    private boolean ascSort = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readPreferences();
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

        setTitle(getString(R.string.client_managment));

        bottomNav = findViewById(R.id.bottomNavigation);

        BottomNavListener.setup(this, bottomNav, R.id.client);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opc_clients, menu);

        menuClientSorting = menu.findItem(R.id.menuClientSort);
        menuTheme = menu.findItem(R.id.menuThemeMode);

        return true;
    }

    // Solução p/ apresentar o icon dentro do menu expandido
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    java.lang.reflect.Method method =
                            menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateSortingIcon();
        updateThemeIcon();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuClientAdd) {
            //openNewItem();
            return true;
        } else if (idMenuItem == R.id.menuAbout) {
            openAbout();
            return true;
        } else if (idMenuItem == R.id.menuClientSort) {
            //writeAscSortPreferences(!ascSort);
            updateSortingIcon();
            //sortList();
            return true;
        } else if (idMenuItem == R.id.menuThemeMode) {
            writeLightThemePreferences(!lightTheme);
            updateThemeIcon();
            changeTheme();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void openAbout() {
        Intent intentOpening = new Intent(this, AboutActivity.class);
        startActivity(intentOpening);
    }

    private void readPreferences() {
        SharedPreferences shared = getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);

        ascSort = shared.getBoolean(KEY_ASC_SORT, ascSort);
        lightTheme = shared.getBoolean(KEY_LIGHT_THEME, lightTheme);
    }

    private void updateSortingIcon() {
        if (ascSort) {
            menuClientSorting.setIcon(R.drawable.ic_action_ascending);
        } else {
            menuClientSorting.setIcon(R.drawable.ic_action_descending);
        }
    }

    private void changeTheme(){
        if (lightTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void writeLightThemePreferences(boolean newValue) {
        SharedPreferences shared = getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(KEY_LIGHT_THEME, newValue);
        editor.commit();

        lightTheme = newValue;
    }

    private void updateThemeIcon() {
        if (lightTheme) {
            menuTheme.setIcon(R.drawable.ic_light_mode);
        } else {
            menuTheme.setIcon(R.drawable.ic_dark_mode);
        }
    }
}