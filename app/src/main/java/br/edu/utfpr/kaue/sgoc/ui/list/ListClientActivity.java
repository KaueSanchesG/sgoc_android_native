    package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.data.ClientDatabase;
import br.edu.utfpr.kaue.sgoc.data.ItemDatabase;
import br.edu.utfpr.kaue.sgoc.model.Client;
import br.edu.utfpr.kaue.sgoc.model.Item;
import br.edu.utfpr.kaue.sgoc.ui.about.AboutActivity;
import br.edu.utfpr.kaue.sgoc.ui.form.ClientActivity;
import br.edu.utfpr.kaue.sgoc.ui.form.ItemActivity;
import br.edu.utfpr.kaue.sgoc.ui.util.BottomNavListener;

public class ListClientActivity extends AppCompatActivity {

    private MenuItem menuClientSorting;
    private MenuItem menuTheme;
    private BottomNavigationView bottomNav;

    public static final String SHARED_PREFERENCES_PATH = "br.edu.utfpr.kaue.sgoc.PREFERENCES";
    public static final String KEY_LIGHT_THEME = "LIGHT_THEME";
    public static final String KEY_ASC_SORT = "CLIENT_ASCENDING_SORT";

    private boolean lightTheme = true;
    private boolean ascSort = true;

    //private Client

    private List<Client> clientList;

    // TODO finaizar imp aqui
    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.opc_item_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEdit) {
                //editItem();
                return true;
            } else if (idMenuItem == R.id.menuItemRemove) {
                //removeItem();
                mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
//            if (selectedView != null) {
//                selectedView.setBackground(backgroundDrawable);
//            }
//            actionMode = null;
//            selectedView = null;
//            backgroundDrawable = null;
//
//            listViewItems.setEnabled(true);
        }

    };

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

    public void openNewClient(){
        Intent intentOpening = new Intent(this, ClientActivity.class);

        intentOpening.putExtra(ClientActivity.KEY_MODO, ClientActivity.KEY_NEW);

        launcherNewClient.launch(intentOpening);
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

    ActivityResultLauncher<Intent> launcherNewClient = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult ar) {
                    if (ar.getResultCode() == ItemActivity.RESULT_OK) {
                        Intent intent = ar.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            long id = bundle.getLong(ItemActivity.KEY_ID);

                            ClientDatabase database = ClientDatabase.getInstance(ListClientActivity.this);

                            Client client = database.getClientDao().queryForId(id);

                            clientList.add(client);

                            sortList();
                        }
                    }
                }
            });

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

    private void changeTheme() {
        if (lightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void sortList() {
        ClientDatabase database = ClientDatabase.getInstance(this);

        List<Client> novaLista;

        if (ascSort) {
            novaLista = database.getClientDao().queryAllAscending();
        } else {
            novaLista = database.getClientDao().queryAllDescending();
        }

        clientList.clear();
        clientList.addAll(novaLista);

        //TODO imp aq
        //clientAdapter.notifyDataSetChanged();
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