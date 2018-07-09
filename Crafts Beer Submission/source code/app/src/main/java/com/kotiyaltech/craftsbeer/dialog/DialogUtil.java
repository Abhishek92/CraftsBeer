package com.kotiyaltech.craftsbeer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kotiyaltech.craftsbeer.R;

/**
 * Created by hp pc on 30-06-2018.
 */

public final class DialogUtil {

    private DialogUtil(){

    }

    public static void showFilterDialog(Context context, final FilterEventListener filterEventListener){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.filter_dialog_layout);
        final RadioGroup radioGroup = dialog.findViewById(R.id.beerStyleRG);
        TextView cancel = dialog.findViewById(R.id.cancel);
        TextView ok = dialog.findViewById(R.id.ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                if(radioButton != null) {
                    filterEventListener.onBeerStyleSelected(radioButton.getText().toString());
                }
                dialog.cancel();

            }
        });

        dialog.show();
    }

    public interface FilterEventListener{
        public void onBeerStyleSelected(String style);
    }
}
