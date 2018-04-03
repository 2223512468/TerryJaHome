package com.jajahome.feature.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jajahome.R;

/**
 * Created by laotang on 2016/8/16.
 */
public class ChoseSexDialog extends Dialog {

    public ChoseSexDialog(Context context) {
        super(context);
    }

    public ChoseSexDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener manClickListener;
        private OnClickListener womenClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setManText(OnClickListener listener) {
            this.manClickListener = listener;
            return this;
        }

        public Builder setWomanText(OnClickListener listener) {
            this.womenClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }


        public CacheDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CacheDialog dialog = new CacheDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_sex_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the confirm button

            if (positiveButtonClickListener != null) {
                layout.findViewById(R.id.positiveButton)
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                            }
                        });
            }
            if (manClickListener != null) {
                layout.findViewById(R.id.man)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                manClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
            }
            if (womenClickListener != null) {
                layout.findViewById(R.id.woman)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                womenClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEUTRAL);
                            }
                        });
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }
}
