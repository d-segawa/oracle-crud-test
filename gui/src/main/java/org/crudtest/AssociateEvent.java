package org.crudtest;

import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.TextField;

import org.crudtest.frame.CreateTriggerDialog;
import org.crudtest.frame.CrudTestFrame;
import org.crudtest.frame.DeleteAllObjectDialog;
import org.crudtest.frame.DeleteLogDialog;
import org.crudtest.listener.OutPutHtmlListener;

public class AssociateEvent {

    public void assemble() {
        TextField t1 = CrudTestFrame.getComponent(CompornentName.TEXT1, TextField.class);
        Label l1 = CrudTestFrame.getComponent(CompornentName.LABEL1, Label.class);
        Choice c1 = CrudTestFrame.getComponent(CompornentName.CHOICE1, Choice.class);

        // トリガー作成
        CreateTriggerDialog createTriggerDialog = CrudTestFrame.getSubwindow(
                CompornentName.CREATE_TRIGGER_BUTTON,
                CreateTriggerDialog.class);

        CrudTestFrame.setListener(CompornentName.CREATE_TRIGGER_BUTTON, e -> {
            if (t1.getText() == null || t1.getText().length() <= 0) {
                l1.setText("テーブルを入力してください");
            } else {
                if (c1.getItemCount() <= 0) {
                    createTriggerDialog.setVisible(true);
                } else {
                    createTrigger(l1, t1, c1);
                }
            }
        });

        //
        FileDialog fd = CrudTestFrame.getSubwindow(CompornentName.FILE_OUTPUT_DIALOG, FileDialog.class);
        OutPutHtmlListener ohl = new OutPutHtmlListener(c1, l1, fd, () -> {
        });
        CrudTestFrame.setListener(CompornentName.OUTPUT_LOG_BUTTON, ohl);

        // ログテーブル削除
        DeleteLogDialog dl = CrudTestFrame.getSubwindow(CompornentName.DELETE_CONFIRM_DIALOG,
                DeleteLogDialog.class);
        CrudTestFrame.setListener(CompornentName.DELETE_ALL_LOG_BUTTON, e -> {
            //            String item = c1.getSelectedItem();
            //            if (item == null || item.isEmpty()) {
            //                l1.setText("対象テーブルを選択してください。");
            //                return;
            //            }
            dl.setVisible(true);
        });

        // トリガー,オブジェクト削除
        DeleteAllObjectDialog deleteAllObjectDialog = CrudTestFrame.getSubwindow(
                CompornentName.DELETE_ALL_OBJECT_DIALOG,
                DeleteAllObjectDialog.class);
        CrudTestFrame.setListener(CompornentName.DELETE_ALL_TRIGER_BUTTON, e -> {
            deleteAllObjectDialog.setVisible(true);
        });

    }

    void createTrigger(Label label, TextField textField, Choice c1) {
        label.setText("");

        String text = textField.getText();
        org.crudtest.CoreHelper.Result result = CoreHelper.createTrigger(text);
        if (result.result) {
        } else {
            label.setText(result.errorMessage);
        }

        CoreHelper.renewChoice(c1);
        textField.setText("");
    }
}
