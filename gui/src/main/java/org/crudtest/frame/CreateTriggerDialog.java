package org.crudtest.frame;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;

import org.crudtest.listener.CoreHelper;

public class CreateTriggerDialog extends java.awt.Dialog {

    public CreateTriggerDialog(java.awt.Frame owner, Label l1, TextField textField, Choice c1) {
        super(owner, true);

        Label l2 = new Label();
        l2.setText("トリガーを作成しますか？");
        Label l3 = new Label();
        l3.setText("（管理用のテーブル、シーケンスも作成されます）");


        //Button
        Button b1 = new Button("Ok");
        Button b2 = new Button("Cancel");

        // action listner
        b1.addActionListener(e -> {
            l1.setText("");
            String text = textField.getText();
            org.crudtest.listener.CoreHelper.Result result = CoreHelper.createTrigger(text);
            if (result.result) {
            } else {
                l1.setText(result.errorMessage);
            }
            CoreHelper.renewChoice(c1);
            textField.setText("");
            setVisible(false);
        });
        b2.addActionListener(e -> {
            setVisible(false);
            l1.setText("");
        });

        GridBagLayout gl = new GridBagLayout();
        gl.setConstraints(l2, LayoutHelper.createGridBagConstraints(0, 0, 1, 2));
        gl.setConstraints(l3, LayoutHelper.createGridBagConstraints(1, 0, 1, 2));
        gl.setConstraints(b1, LayoutHelper.createGridBagConstraints(2, 0, 1, 1));
        gl.setConstraints(b2, LayoutHelper.createGridBagConstraints(2, 1, 1, 1));
        setLayout(gl);

        add(l2);
        add(l3);
        add(b1);
        add(b2);
    }

}
