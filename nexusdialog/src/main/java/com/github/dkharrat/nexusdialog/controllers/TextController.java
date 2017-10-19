package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by markus on 19.10.17.
 */

public abstract class TextController extends LabeledFieldController {

    protected final int editTextId = FormController.generateViewId();
    protected final int textViewId = FormController.generateViewId();
    protected boolean readonly;


    /**
     * Creates a labeled text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. If null, no label is displayed and the field will
     *                      occupy the entire length of the row.
     * @param isRequired    indicates whether this field is required. If true, this field checks for a non-empty or
     *                      non-null value upon validation. Otherwise, this field can be empty.
     */
    public TextController(Context ctx, String name, String labelText, boolean isRequired) {
        super(ctx, name, labelText, isRequired);
    }

    /**
     * Creates a labeled text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. If null, no label is displayed and the field will
     *                      occupy the entire length of the row.
     * @param validators    The list of input validations to add to the field.
     */
    public TextController(Context ctx, String name, String labelText, Set<InputValidator> validators) {
        super(ctx, name, labelText, validators);
    }
    /**
     * Is this a multiline field?
     *
     * @return true if it's multiline, false if not.
     */
    public boolean isMultiLine()
    {
        return false;
    }

    /**
     * Return EditText placeholder.
     *
     * @return Placeholder string or null
     */
    public String getPlaceholder()
    {
        return null;
    }

    /**
     * Return EditText input type.
     *
     * @return EditText input type.
     */
    public int getInputType()
    {
        return InputType.TYPE_CLASS_TEXT;
    }

    /**
     * Returns the EditText view associated with this element.
     *
     * @return the EditText view associated with this element
     */
    public EditText getEditText() {
        return (EditText)getView().findViewById(editTextId);
    }

    /**
     * return the readonly TextView
     *
     * @return
     */
    protected TextView getTextView() {
        return (TextView)getView().findViewById(textViewId);
    }


        /**
     * create an EditText and its readonly-counterpart TextView for all controls
     * that use EditText fields (e.g. EditTextController, DatePickerController,
     * TimePickerController ...)
     *
     * The readonly field is transparently created and added to the container.
     *
     * @param container The container this field is added to.
     * @return the EditText instance.
     */
    protected View createEditTextFieldView(FrameLayout container) {
        /* we use a TextView for read only state and an EditText for !readonly... */

        /* create TextView */
        final TextView textView = new TextView(getContext());
        textView.setId(textViewId);
        textView.setSingleLine(!isMultiLine());
        container.addView(textView);

        /* create EditText */
        final EditText editText = new EditText(getContext());
        editText.setId(editTextId);

        editText.setSingleLine(!isMultiLine());
        if (getPlaceholder() != null) {
            editText.setHint(getPlaceholder());
        }
        editText.setInputType(getInputType());
        refresh(editText, textView);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editText.getText().toString();
                getModel().setValue(getName(), text);
            }
        });

        return editText;
    }

    /**
     * Set readonly mode. Setting readonly to true disables editing.
     *
     * Note: Android does not support readonly EditText fields. If set to disabled,
     *       it won't scroll, renderung disabled/readonly EditText fields useless.
     *       Thus, we add a supplicant TextView that swaps place with the EditText
     *       as appropriate.
     *
     * @param readonly if true, element will be readonly.
     */
    public void setReadonly(boolean readonly)
    {
        this.readonly = readonly;

        EditText editText = getEditText();
        TextView textView = getTextView();
        if (editText == null || textView == null) {
            return;
        }

        if (readonly) {
            textView.setText(editText.getText());
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

    }


    /**
     * Refresh fields with current model value.
     *
     * @param editText the EditText instance
     * @param textView the associated TextView instance
     */

    protected void refresh(EditText editText, TextView textView) {
        Object value = getModel().getValue(getName());
        String valueStr = value != null ? value.toString() : "";
        if (!valueStr.equals(editText.getText().toString())) {
            editText.setText(valueStr);
            /* also update the readonly widget */
            if (textView != null) {
                textView.setText(valueStr);
            }
        }

        editText.setVisibility(this.readonly ? View.GONE : View.VISIBLE);
        textView.setVisibility(this.readonly ? View.VISIBLE : View.GONE);

    }

    @Override
    protected View createFieldView(FrameLayout container) {
        /* we use a TextView for read only state and an EditText for !readonly... */
        return createEditTextFieldView(container);
    }

    @Override
    public void refresh() {
        refresh(getEditText(), getTextView());
    }

}
