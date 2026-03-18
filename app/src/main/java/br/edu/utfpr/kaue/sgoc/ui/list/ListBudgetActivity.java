package br.edu.utfpr.kaue.sgoc.ui.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.kaue.sgoc.R;

public class ListBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_budget);

        setTitle(getString(R.string.budget_management));
    }
}