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
import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.text.Normalizer;
import java.util.Set;

/**
 * Represents a field that allows free-form text.
 */
public class EditTextController extends TextController {

    private int inputType;
    private final String placeholder;
    private boolean readonly;


    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param validators    contains the validations to process on the field
     * @param inputType     the content type of the text box as a mask; possible values are defined by {@link InputType}.
     *                      For example, to enable multi-line, enable {@code InputType.TYPE_TEXT_FLAG_MULTI_LINE}.
     */
    public EditTextController(Context ctx, String name, String labelText, String placeholder, Set<InputValidator> validators, int inputType) {
        super(ctx, name, labelText, validators);
        this.placeholder = placeholder;
        this.inputType = inputType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param validators    contains the validations to process on the field
     */
    public EditTextController(Context ctx, String name, String labelText, String placeholder, Set<InputValidator> validators) {
        this(ctx, name, labelText, placeholder, validators, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field. Set to {@code null} to not show a label.
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param isRequired    indicates if the field is required or not
     * @param inputType     the content type of the text box as a mask; possible values are defined by {@link InputType}.
     *                      For example, to enable multi-line, enable {@code InputType.TYPE_TEXT_FLAG_MULTI_LINE}.
     */
    public EditTextController(Context ctx, String name, String labelText, String placeholder, boolean isRequired, int inputType) {
        super(ctx, name, labelText, isRequired);
        this.placeholder = placeholder;
        this.inputType = inputType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     * @param isRequired    indicates if the field is required or not
     */
    public EditTextController(Context ctx, String name, String labelText, String placeholder, boolean isRequired) {
        this(ctx, name, labelText, placeholder, isRequired, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     * @param placeholder   a placeholder text to show when the input field is empty. If null, no placeholder is displayed
     */
    public EditTextController(Context ctx, String name, String labelText, String placeholder) {
        this(ctx, name, labelText, placeholder, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx           the Android context
     * @param name          the name of the field
     * @param labelText     the label to display beside the field
     */
    public EditTextController(Context ctx, String name, String labelText) {
        this(ctx, name, labelText, null, false, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Returns a mask representing the content input type. Possible values are defined by {@link InputType}.
     *
     * @return a mask representing the content input type
     */
    public int getInputType() {
        return inputType;
    }

    /**
     * placeholder getter.
     *
     * @return String containing the current placeholder or null.
     */
    public String getPlaceholder()
    {
        return placeholder;
    }



    private void setInputTypeMask(int mask, boolean enabled) {
        if (enabled) {
            inputType = inputType | mask;
        } else {
            inputType = inputType & ~mask;
        }
        if (isViewCreated()) {
            getEditText().setInputType(inputType);
        }
    }

    /**
     * Indicates whether this text box has multi-line enabled.
     *
     * @return  true if this text box has multi-line enabled, or false otherwise
     */
    public boolean isMultiLine() {
        return (inputType | InputType.TYPE_TEXT_FLAG_MULTI_LINE) != 0;
    }

    /**
     * Enables or disables multi-line input for the text field. Default is false.
     *
     * @param multiLine if true, multi-line input is allowed, otherwise, the field will only allow a single line.
     */
    public void setMultiLine(boolean multiLine) {
        setInputTypeMask(InputType.TYPE_TEXT_FLAG_MULTI_LINE, multiLine);
    }

    /**
     * Indicates whether this text field hides the input text for security reasons.
     *
     * @return  true if this text field hides the input text, or false otherwise
     */
    public boolean isSecureEntry() {
        return (inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0;
    }

    /**
     * Enables or disables secure entry for this text field. If enabled, input will be hidden from the user. Default is
     * false.
     *
     * @param isSecureEntry if true, input will be hidden from the user, otherwise input will be visible.
     */
    public void setSecureEntry(boolean isSecureEntry) {
        setInputTypeMask(InputType.TYPE_TEXT_VARIATION_PASSWORD, isSecureEntry);
    }


}
