package com.ormoyo.timiss.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.ormoyo.timiss.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditTextWithSuffix extends androidx.appcompat.widget.AppCompatEditText {
    TextPaint textPaint = new TextPaint();
    private String suffix = "";
    private int maxValue;
    private int minValue;

    public EditTextWithSuffix(Context context) {
        super(context);
    }

    public EditTextWithSuffix(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs, 0);
    }

    public EditTextWithSuffix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithSuffix, defStyleAttr, 0);
        if(a != null) {
            suffix = a.getString(R.styleable.EditTextWithSuffix_suffix);
            minValue = a.getInt(R.styleable.EditTextWithSuffix_minValue, 0);
            maxValue = a.getInt(R.styleable.EditTextWithSuffix_maxValue, 0);

            List<InputFilter> filters = new ArrayList<>();
            for(InputFilter inputFilter : getFilters()) {
                if(inputFilter instanceof InputFilter.LengthFilter) {
                    InputFilter.LengthFilter filter = (InputFilter.LengthFilter) inputFilter;
                    filters.add(new InputFilter.LengthFilter(filter.getMax() + 1 + suffix.length()));
                }else if(inputFilter instanceof InputFilter.AllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
                        filters.add(new InputFilter.AllCaps(locale));
                    }
                }
            }
            InputFilter[] arr = new InputFilter[filters.size()];
            filters.toArray(arr);
            setFilters(arr);
        }
        a.recycle();
    }

    String prevText;
    boolean cancelCheck;
    boolean createdSuffix;

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(suffix == null) return;
        if(cancelCheck) {
            cancelCheck = false;
            return;
        }
        if(!createdSuffix) {
            createdSuffix = true;
            cancelCheck = true;
            setText(text + " " + suffix);
            setSelection(1);
            prevText = text.toString();
            return;
        }
        if(getInputType() == InputType.TYPE_CLASS_NUMBER) {
            String n = text.toString().replaceAll("[^0-9]", "");
            if(!n.isEmpty()) {
                int number = Math.min(Math.max(Integer.parseInt(n), minValue), maxValue);
                cancelCheck = true;
                setText(number + " " + suffix);
                setSelection((int) Math.log10(number) + 1);
                prevText = text.toString();
                return;
            }
        }
        if(createdSuffix && isBetween(getSelectionStart(), text.length() - suffix.length(), text.length())) {
            cancelCheck = true;
            setText(prevText);
            setSelection(prevText.length() - suffix.length() - 1);
            return;
        }
        int index = text.toString().indexOf(suffix) - 1;
        String substring = text.toString().substring(0, index >= 0 ? index : text.length());
        if(prevText.indexOf(suffix) != -1 && index < 0 && !text.toString().isEmpty()){
            cancelCheck = true;
            setText(text + " " + suffix);
            prevText = text.toString();
            return;
        }

        if(text.toString().isEmpty() || substring.isEmpty()) {
            cancelCheck = true;
            createdSuffix = false;
            setText("");
            prevText = text.toString();
        }
    }

    private static boolean isBetween(int number, int min, int max) {
        return  number >= min && number <= max;
    }
}
