package br.edu.utfpr.kaue.sgoc.ui.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.data.ItemDatabase;
import br.edu.utfpr.kaue.sgoc.model.Item;
import br.edu.utfpr.kaue.sgoc.model.QuantityType;
import br.edu.utfpr.kaue.sgoc.ui.util.Alert;
import br.edu.utfpr.kaue.sgoc.ui.util.QuantityTypeFormatter;

public class ItemActivity extends AppCompatActivity {

    public static final String KEY_ID = "ID";
    public static final String KEY_MODO = "MODO";
    public static final int KEY_NEW = 0;
    public static final int KEY_EDIT = 1;

    private EditText editTextItemName;
    private Spinner spinnerItemQuantityType;

    private int modo;

    private Item dbItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        editTextItemName = findViewById(R.id.editTextItemName);
        spinnerItemQuantityType = findViewById(R.id.spinnerItemQuantityType);

        closeKeyboardOnEnter();

        List<String> spinnerItems = new ArrayList<>();

        for (QuantityType q : QuantityType.values()) {
            spinnerItems.add(QuantityTypeFormatter.format(this, q));
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemQuantityType.setAdapter(adapter);

        Intent intentOpening = getIntent();

        Bundle bundle = intentOpening.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == KEY_NEW) {
                setTitle(R.string.novo_item);
            } else {
                setTitle(R.string.editando_item);

                long id = bundle.getLong(KEY_ID);

                ItemDatabase database = ItemDatabase.getInstance(this);

                dbItem = database.getItemDao().queryForId(id);

                editTextItemName.setText(dbItem.getName());
                spinnerItemQuantityType.setSelection(dbItem.getQuantityType().ordinal());

            }
        }
    }

    public void clearActivity() {
        editTextItemName.setText(null);
        spinnerItemQuantityType.setSelection(0);

        requestFocusOnItemName();

        Toast.makeText(
                this,
                R.string.as_entradas_foram_apagadas,
                Toast.LENGTH_LONG).show();
    }

    public void saveActivityValues() {
        String itemName = editTextItemName.getText().toString();
        QuantityType itemQuantityType = QuantityType.values()[spinnerItemQuantityType.getSelectedItemPosition()];

        if (itemName.isBlank()) {
            Alert.showAlert(this, R.string.o_valor_de_nome_deve_ser_preenchido);

            requestFocusOnItemName();

            return;
        }

        Item item = new Item(itemName, itemQuantityType);

        if (item.equals(dbItem)) {
            setResult(ItemActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResponse = new Intent();

        ItemDatabase database = ItemDatabase.getInstance(this);

        if (modo == KEY_NEW) {
            long newId = database.getItemDao().insert(item);

            if (newId <= 0){
                Alert.showAlert(this, R.string.erro_ao_tentar_inserir);
                return;
            }

            item.setId(newId);

        }else {
            item.setId(dbItem.getId());

            int updatedRows = database.getItemDao().update(item);

            if (updatedRows != 1){
                Alert.showAlert(this, R.string.erro_ao_tentar_alterar);
                return;
            }
        }

        intentResponse.putExtra(KEY_ID, item.getId());

        setResult(ItemActivity.RESULT_OK, intentResponse);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opc_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSave) {
            saveActivityValues();
            return true;
        } else if (idMenuItem == R.id.menuItemClear) {
            clearActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void requestFocusOnItemName() {
        editTextItemName.requestFocus();

        // Solução para abrir o teclado ao solicitar o focus
        editTextItemName.post(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextItemName, InputMethodManager.SHOW_IMPLICIT);
        });
    }

    // Solução para fechar o teclado ao clicar no botão Enter
    private void closeKeyboardOnEnter() {
        editTextItemName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                v.clearFocus();

                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                return true;
            }
            return false;
        });
    }
}