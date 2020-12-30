package com.together.nosheng.view;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.together.nosheng.R;

/**
 * atrr 속성 필요함
 * res>values>styles>newDialog  필요함
 *
 */
public class ProgressCircleDialog extends Dialog {
    public static final boolean DEF_CANCELABLE = true ;
    public static final boolean DEF_CANNOT_CANCELABLE = false ;


    public static  com.together.nosheng.view.ProgressCircleDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, DEF_CANCELABLE);
    }

    public static  com.together.nosheng.view.ProgressCircleDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, DEF_CANCELABLE, null);
    }

    public static  com.together.nosheng.view.ProgressCircleDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }


    public static  com.together.nosheng.view.ProgressCircleDialog show(Context context, CharSequence title,
                                                                    CharSequence message, boolean indeterminate,
                                                                    boolean cancelable, OnCancelListener cancelListener) {
        com.together.nosheng.view.ProgressCircleDialog dialog = new com.together.nosheng.view.ProgressCircleDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public ProgressCircleDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}
