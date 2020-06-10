package org.crudtest.listener;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateTriggerListener implements ActionListener {

    TextField textField;
    Label label;
    Runnable runnable;

    public CreateTriggerListener(TextField textField, Label label, Runnable runnable) {
        this.textField = textField;
        this.label = label;
        this.runnable = runnable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("");

        String text = textField.getText();
        org.crudtest.listener.CoreHelper.Result result = CoreHelper.createTrigger(text);
        if (result.result) {
        } else {
            label.setText(result.errorMessage);
        }

        runnable.run();
    }

}
