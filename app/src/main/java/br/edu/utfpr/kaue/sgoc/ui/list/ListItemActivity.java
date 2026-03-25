package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.kaue.sgoc.ui.about.AboutActivity;
import br.edu.utfpr.kaue.sgoc.ui.form.ItemActivity;
import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.model.Item;
import br.edu.utfpr.kaue.sgoc.model.QuantityType;
import br.edu.utfpr.kaue.sgoc.ui.util.BottomNavListener;

public class ListItemActivity extends AppCompatActivity {

    private ListView listViewItems;

    private List<Item> itemList;

    private int selectedPosition = -1;

    private ActionMode actionMode;
    private ItemAdapter itemAdapter;

    private View selectedView;
    private Drawable backgroundDrawable;

    public static final String SHARED_PREFERENCES_PATH = "br.edu.utfpr.kaue.sgoc.PREFERENCES";

    public static final String KEY_ASC_SORT = "ASCENDING_SORT";
    private boolean ascSort = true;

    public static final String KEY_LIGHT_THEME = "LIGHT_THEME";
    private boolean lightTheme = true;

    private MenuItem menuItemSorting;
    private MenuItem menuTheme;
    private BottomNavigationView bottomNav;

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
                editItem();
                return true;
            } else if (idMenuItem == R.id.menuItemRemove) {
                removeItem();
                mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (selectedView != null) {
                selectedView.setBackground(backgroundDrawable);
            }
            actionMode = null;
            selectedView = null;
            backgroundDrawable = null;

            listViewItems.setEnabled(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readPreferences();
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        setTitle(getString(R.string.controle_de_itens));

        listViewItems = findViewById(R.id.listViewItems);
        bottomNav = findViewById(R.id.bottomNavigation);

        populateData();

        registerForContextMenu(listViewItems);

        BottomNavListener.setup(this, bottomNav, R.id.item);
    }

    private void populateData() {

        itemList = new ArrayList<>();

        Item item;
        QuantityType quantityType;

        QuantityType[] quantityTypes = QuantityType.values();

        itemAdapter = new ItemAdapter(this, itemList);

        listViewItems.setAdapter(itemAdapter);

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }

                selectedPosition = position;

                selectedView = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                listViewItems.setEnabled(false);

                actionMode = startSupportActionMode(actionCallback);

                return true;
            }
        });
    }


    public void openAbout() {
        Intent intentOpening = new Intent(this, AboutActivity.class);
        startActivity(intentOpening);
    }

    ActivityResultLauncher<Intent> launcherNewItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult ar) {
                    if (ar.getResultCode() == ItemActivity.RESULT_OK) {
                        Intent intent = ar.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String name = bundle.getString(ItemActivity.KEY_NAME);
                            String quantityType = bundle.getString(ItemActivity.KEY_QUANTITY_TYPE);

                            Item item = new Item(name, QuantityType.valueOf(quantityType));

                            itemList.add(item);

                            sortList();
                        }
                    }
                }
            });

    public void openNewItem() {
        Intent intentOpening = new Intent(this, ItemActivity.class);

        intentOpening.putExtra(ItemActivity.KEY_MODO, ItemActivity.KEY_NEW);

        launcherNewItem.launch(intentOpening);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opc_items, menu);

        menuItemSorting = menu.findItem(R.id.menuItemSort);
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

        if (idMenuItem == R.id.menuItemAdd) {
            openNewItem();
            return true;
        } else if (idMenuItem == R.id.menuAbout) {
            openAbout();
            return true;
        } else if (idMenuItem == R.id.menuItemSort) {
            writeAscSortPreferences(!ascSort);
            updateSortingIcon();
            sortList();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.opc_item_selected, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemEdit) {
            editItem();
            return true;
        } else if (idMenuItem == R.id.menuItemRemove) {
            removeItem();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void removeItem() {
        itemList.remove(selectedPosition);

        itemAdapter.notifyDataSetChanged();
    }

    ActivityResultLauncher<Intent> launcherEditItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult ar) {
                    if (ar.getResultCode() == ItemActivity.RESULT_OK) {
                        Intent intent = ar.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String name = bundle.getString(ItemActivity.KEY_NAME);
                            String quantityType = bundle.getString(ItemActivity.KEY_QUANTITY_TYPE);

                            Item item = itemList.get(selectedPosition);

                            item.setName(name);

                            QuantityType quantityTypeValue = QuantityType.valueOf(quantityType);
                            item.setQuantityType(quantityTypeValue);

                            sortList();
                        }
                    }
                    selectedPosition = -1;

                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            });

    private void editItem() {

        Item item = itemList.get(selectedPosition);

        Intent intentOpening = new Intent(this, ItemActivity.class);

        intentOpening.putExtra(ItemActivity.KEY_MODO, ItemActivity.KEY_EDIT);

        intentOpening.putExtra(ItemActivity.KEY_NAME, item.getName());
        intentOpening.putExtra(ItemActivity.KEY_QUANTITY_TYPE, item.getQuantityType().name());

        launcherEditItem.launch(intentOpening);
    }

    private void readPreferences() {
        SharedPreferences shared = getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);

        ascSort = shared.getBoolean(KEY_ASC_SORT, ascSort);
        lightTheme = shared.getBoolean(KEY_LIGHT_THEME, lightTheme);
    }

    private void writeAscSortPreferences(boolean newValue) {
        SharedPreferences shared = getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(KEY_ASC_SORT, newValue);
        editor.commit();

        ascSort = newValue;
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

    private void changeTheme() {
        if (lightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void sortList() {
        if (ascSort) {
            Collections.sort(itemList, Item.ascSort);
        } else {
            Collections.sort(itemList, Item.descSort);
        }
        itemAdapter.notifyDataSetChanged();
    }

    private void updateSortingIcon() {
        if (ascSort) {
            menuItemSorting.setIcon(R.drawable.ic_action_ascending);
        } else {
            menuItemSorting.setIcon(R.drawable.ic_action_descending);
        }
    }
}