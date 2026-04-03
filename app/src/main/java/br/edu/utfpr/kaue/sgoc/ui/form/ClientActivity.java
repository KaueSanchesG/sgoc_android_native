package br.edu.utfpr.kaue.sgoc.ui.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.data.ClientDatabase;
import br.edu.utfpr.kaue.sgoc.model.Client;
import br.edu.utfpr.kaue.sgoc.ui.util.Alert;

public class ClientActivity extends AppCompatActivity {

    public static final String KEY_ID = "ID";
    public static final String KEY_MODO = "MODO";
    public static final int KEY_NEW = 0;
    public static final int KEY_EDIT = 1;

    private EditText editTextClientName;
    private EditText editTextClientAdress;
    private EditText editTextClientContact;

    private int modo;

    private Client dbClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        editTextClientName = findViewById(R.id.editTextClientName);
        editTextClientAdress = findViewById(R.id.editTextClientAddress);
        editTextClientContact = findViewById(R.id.editTextClientContact);

        Intent intentOpening = getIntent();

        Bundle bundle = intentOpening.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == KEY_NEW) {
                setTitle(getString(R.string.novo_cliente));
            } else {
                setTitle(getString(R.string.editando_cliente));

                long id = bundle.getLong(KEY_ID);

                ClientDatabase database = ClientDatabase.getInstance(this);

                dbClient = database.getClientDao().queryForId(id);

                editTextClientName.setText(dbClient.getName());
                editTextClientAdress.setText(dbClient.getAddress());
                editTextClientContact.setText(dbClient.getContact());
            }
        }
    }

    public void clearActivity() {
        editTextClientName.setText(null);
        editTextClientAdress.setText(null);
        editTextClientContact.setText(null);

        requestFocusOnItemName();

        Toast.makeText(
                this,
                R.string.as_entradas_foram_apagadas,
                Toast.LENGTH_LONG).show();
    }

    public void saveActivityValues() {
        String clientName = editTextClientName.getText().toString();
        String clientAddress = editTextClientAdress.getText().toString();
        String clientContact = editTextClientContact.getText().toString();

        if (clientName.isBlank()) {
            Alert.showAlert(this, R.string.o_valor_de_nome_deve_ser_preenchido);

            requestFocusOnItemName();

            return;
        }

        Client client = new Client(clientName, clientAddress, clientContact);

        if (client.equals(dbClient)) {
            setResult(ClientActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResponse = new Intent();

        ClientDatabase database = ClientDatabase.getInstance(this);

        if (modo == KEY_NEW) {
            long newId = database.getClientDao().insert(client);

            if (newId <= 0) {
                Alert.showAlert(this, R.string.erro_ao_tentar_inserir);
                return;
            }

            client.setId(newId);

        } else {
            client.setId(dbClient.getId());

            int updatedRows = database.getClientDao().update(client);

            if (updatedRows != 1) {
                Alert.showAlert(this, R.string.erro_ao_tentar_alterar);
                return;
            }
        }

        intentResponse.putExtra(KEY_ID, client.getId());

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
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void requestFocusOnItemName() {
        editTextClientName.requestFocus();

        // Solução para abrir o teclado ao solicitar o focus
        editTextClientName.post(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextClientName, InputMethodManager.SHOW_IMPLICIT);
        });
    }
}