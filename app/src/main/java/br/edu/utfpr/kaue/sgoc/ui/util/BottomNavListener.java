package br.edu.utfpr.kaue.sgoc.ui.util;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.ui.list.ListBudgetActivity;
import br.edu.utfpr.kaue.sgoc.ui.list.ListClientActivity;
import br.edu.utfpr.kaue.sgoc.ui.list.ListItemActivity;

public class BottomNavListener {

    public static void setup(Activity activity, BottomNavigationView bottomNav, int selectedId) {
        bottomNav.setSelectedItemId(selectedId);

        bottomNav.setOnItemSelectedListener(itemClicked -> {
            int id = itemClicked.getItemId();

            if (id == selectedId) {
                return true;

            }

            Intent intent = null;

            if (id == R.id.item) {
                intent = new Intent(activity, ListItemActivity.class);
            } else if (id == R.id.budget) {
                intent = new Intent(activity, ListBudgetActivity.class);
            } else if (id == R.id.client) {
                intent = new Intent(activity, ListClientActivity.class);
            }

            if (intent != null) {
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
                activity.finish();
            }
            return true;
        });

    }
}
