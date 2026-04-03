package br.edu.utfpr.kaue.sgoc.ui.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.edu.utfpr.kaue.sgoc.R;

public final class Alert {

    private Alert() {
    }

    public static void showAlert(Context context, int idMessage) {
        showAlert(context, context.getString(idMessage), null);
    }


    public static void showAlert(Context context, int idMessage, DialogInterface.OnClickListener listener) {
        showAlert(context, context.getString(idMessage), listener);
    }

    public static void showAlert(Context context, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(message);

        builder.setNeutralButton(R.string.ok, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void acceptAction(Context context, int idMessage, DialogInterface.OnClickListener acceptListener, DialogInterface.OnClickListener denyListener) {
        acceptAction(context, context.getString(idMessage), acceptListener, denyListener);
    }

    public static void acceptAction(Context context, String message, DialogInterface.OnClickListener acceptListener, DialogInterface.OnClickListener denyListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmar);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(message);

        builder.setPositiveButton(R.string.sim, acceptListener);
        builder.setNegativeButton(R.string.nao, denyListener);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
