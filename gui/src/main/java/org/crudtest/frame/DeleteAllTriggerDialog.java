package org.crudtest.frame;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridBagLayout;
import java.awt.Label;

import org.crudtest.listener.CoreHelper;

public class DeleteAllTriggerDialog extends java.awt.Dialog {

    public DeleteAllTriggerDialog(java.awt.Frame owner, Label l1, Choice c1) {
        super(owner, true);

        Label l2 = new Label();
        l2.setText("triggerを全て削除しますか？");

        //Button
        Button b1 = new Button("Ok");
        Button b2 = new Button("Cancel");

        // action listner
        b1.addActionListener(e -> {
            CoreHelper.allDropTrigger();
            CoreHelper.renewChoice(c1);
            setVisible(false);
            l1.setText("");
        });
        b2.addActionListener(e -> {
            setVisible(false);
            l1.setText("");
        });

        GridBagLayout gl = new GridBagLayout();
        gl.setConstraints(l2, LayoutHelper.createGridBagConstraints(0, 0, 1, 2));
        gl.setConstraints(b1, LayoutHelper.createGridBagConstraints(1, 0, 1, 1));
        gl.setConstraints(b2, LayoutHelper.createGridBagConstraints(1, 1, 1, 1));
        setLayout(gl);

        add(l2);
        add(b1);
        add(b2);
    }

}
