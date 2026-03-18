package br.edu.utfpr.kaue.sgoc.ui.util;

import android.content.Context;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.model.QuantityType;

public class QuantityTypeFormatter {

    public static String format(Context context, QuantityType type) {
        switch (type) {
            case UNITY:
                return context.getString(R.string.quantity_unity);
            case CENTIMETER:
                return context.getString(R.string.quantity_centimeter);
            case METER:
                return context.getString(R.string.quantity_meter);
            case SQUARE_METER:
                return context.getString(R.string.quantity_square_meter);
            case CUBIC_METER:
                return context.getString(R.string.quantity_cubic_meter);
            default:
                return "";
        }
    }
}
