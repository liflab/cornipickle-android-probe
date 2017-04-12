package ca.liflab.examples;

/**
 * Created by chafik on 2017-03-21.
 */

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;


public class CharacterCounter implements TextWatcher {
    private final TextInputLayout mTextInputLayout;
    private final TextView txtCounter;
    public boolean inverse = false;
    private final ForegroundColorSpan mNormalTextAppearance;
    private final AlignmentSpan mAlignmentSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
    private final SpannableStringBuilder mErrorText = new SpannableStringBuilder();
    private int mMinLen;
    private int mMaxLen;

    public CharacterCounter(TextInputLayout textInputLayout, int minLen, int maxLen, TextView txtCounter) {
        mTextInputLayout = textInputLayout;
        mNormalTextAppearance = new ForegroundColorSpan(Color.GRAY);

        this.txtCounter = txtCounter;
        mMinLen = minLen;
        mMaxLen = maxLen;
        updateTextCounter();
    }

    private void updateTextCounter() {
        txtCounter.setText("");

        final int length = mTextInputLayout.getEditText().length();

        if (length >=0) {
            txtCounter.append(String.valueOf(length));
            txtCounter.append(" / ");
            txtCounter.append(String.valueOf(mMaxLen));
            if (inverse) {
                txtCounter.setTextColor(Color.BLUE);
                mTextInputLayout.getEditText().getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            } else {
                txtCounter.setTextColor(Color.RED);
                mTextInputLayout.getEditText().getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

            }
            int clr = txtCounter.getCurrentTextColor();


            if (hasValidLength()) {
                if (inverse) {
                    txtCounter.setTextColor(Color.RED);
                    mTextInputLayout.getEditText().getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                } else {

                    txtCounter.setTextColor(Color.BLUE);
                    mTextInputLayout.getEditText().getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                }
                clr = txtCounter.getCurrentTextColor();
                mTextInputLayout.getEditText().getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //    Log.d("color2", "RGB(" + Color.red(clr)+","+Color.green(clr)+","+Color.blue(clr)+"");
            }
        }


    }

    public boolean hasValidLength() {
        final int length = mTextInputLayout.getEditText().length();
        return (length >= mMinLen && length <= mMaxLen);
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateTextCounter();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
    }
}