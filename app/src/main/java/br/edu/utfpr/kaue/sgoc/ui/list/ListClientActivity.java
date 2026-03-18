package br.edu.utfpr.kaue.sgoc.ui.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.kaue.sgoc.R;

public class ListClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

        setTitle(getString(R.string.client_managment));
    }
}