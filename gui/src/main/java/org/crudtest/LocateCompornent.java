package org.crudtest;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.TextField;

import org.crudtest.frame.CrudTestFrame;
import org.crudtest.frame.DeleteAllTriggerDialog;
import org.crudtest.frame.DeleteLogDialog;
import org.crudtest.frame.LayoutHelper;
import org.crudtest.listener.CoreHelper;

public class LocateCompornent {

    public void locateCompornent() {
        //コンポーネントの追加する順番、addする数値、レイアウトの並び順を合わせる

        // Label
        Label l1 = new Label();
        CrudTestFrame.addCompornent(l1, LayoutHelper.createGridBagConstraints(0, 0, 1, 2), CompornentName.LABEL1);

        // Text
        TextField t1 = new TextField();
        t1.setColumns(30);
        CrudTestFrame.addCompornent(t1, LayoutHelper.createGridBagConstraints(1, 0, 1, 1), CompornentName.TEXT1);

        // Create trigger button
        Button b1 = new Button("Create trigger");
        CrudTestFrame.addCompornent(b1, LayoutHelper.createGridBagConstraints(1, 1, 1, 1),
                CompornentName.CREATE_TRIGGER_BUTTON);

        // Choice
        Choice c1 = new Choice();
        CoreHelper.renewChoice(c1);
        CrudTestFrame.addCompornent(c1, LayoutHelper.createGridBagConstraints(2, 0, 1, 1), CompornentName.CHOICE1);

        // prit data button
        Button b2 = new Button("Print data");
        CrudTestFrame.addCompornent(b2, LayoutHelper.createGridBagConstraints(2, 1, 1, 1),
                CompornentName.OUTPUT_LOG_BUTTON);

        // delete all data button
        Button b3 = new Button("Delete all data");
        CrudTestFrame.addCompornent(b3, LayoutHelper.createGridBagConstraints(3, 0, 1, 1),
                CompornentName.DELETE_ALL_LOG_BUTTON);

        // print button
        Button b4 = new Button("Delete all trigger");
        CrudTestFrame.addCompornent(b4, LayoutHelper.createGridBagConstraints(3, 1, 1, 1),
                CompornentName.DELETE_ALL_TRIGER_BUTTON);

        // all delete log dialog
        DeleteLogDialog dl = new DeleteLogDialog(CrudTestFrame.getFrame(), l1);
        dl.setTitle("Dialog");
        dl.setSize(250, 100);
        dl.setLocationRelativeTo(CrudTestFrame.getFrame());
        CrudTestFrame.addSubWindow(dl, CompornentName.DELETE_CONFIRM_DIALOG);

        // drop all triger dialog
        DeleteAllTriggerDialog deleteAllTriggerDialog = new DeleteAllTriggerDialog(CrudTestFrame.getFrame(), l1, c1);
        deleteAllTriggerDialog.setTitle("Dialog");
        deleteAllTriggerDialog.setSize(250, 100);
        deleteAllTriggerDialog.setLocationRelativeTo(CrudTestFrame.getFrame());
        CrudTestFrame.addSubWindow(deleteAllTriggerDialog, CompornentName.DELETE_ALL_TRIGER_DIALOG);

        // print file dialog
        FileDialog fd = new FileDialog(CrudTestFrame.getFrame(), "出力ファイル選択", FileDialog.SAVE);
        CrudTestFrame.addSubWindow(fd, CompornentName.FILE_OUTPUT_DIALOG);

    }
}