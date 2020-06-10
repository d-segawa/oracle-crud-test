package org.crudtest;

import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.TextField;

import org.crudtest.frame.CrudTestFrame;
import org.crudtest.frame.DeleteAllTriggerDialog;
import org.crudtest.frame.DeleteLogDialog;
import org.crudtest.listener.CreateTriggerListener;
import org.crudtest.listener.CoreHelper;
import org.crudtest.listener.OutPutHtmlListener;

public class AssociateEvent {

    public void assemble() {
        TextField t1 = CrudTestFrame.getComponent(CompornentName.TEXT1, TextField.class);
        Label l1 = CrudTestFrame.getComponent(CompornentName.LABEL1, Label.class);
        Choice c1 = CrudTestFrame.getComponent(CompornentName.CHOICE1, Choice.class);
        CreateTriggerListener ctl = new CreateTriggerListener(t1, l1,
                () -> CoreHelper.renewChoice(c1));

        CrudTestFrame.setListener(CompornentName.CREATE_TRIGGER_BUTTON, ctl);

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

        // トリガー削除
        DeleteAllTriggerDialog deleteAllTriggerDialog = CrudTestFrame.getSubwindow(
                CompornentName.DELETE_ALL_TRIGER_DIALOG,
                DeleteAllTriggerDialog.class);
        CrudTestFrame.setListener(CompornentName.DELETE_ALL_TRIGER_BUTTON, e -> {
            deleteAllTriggerDialog.setVisible(true);
        });

    }

}
