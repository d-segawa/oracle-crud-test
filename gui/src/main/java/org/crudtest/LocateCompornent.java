package org.crudtest;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.TextArea;

import org.crudtest.frame.CreateTriggerDialog;
import org.crudtest.frame.CrudTestFrame;
import org.crudtest.frame.DeleteAllObjectDialog;
import org.crudtest.frame.DeleteLogDialog;
import org.crudtest.frame.LayoutHelper;

public class LocateCompornent {

    public void locateCompornent() {
        //コンポーネントの追加する順番、addする数値、レイアウトの並び順を合わせる

        // Label
        Label l1 = new Label();
        CrudTestFrame.addCompornent(l1, LayoutHelper.createGridBagConstraints(0, 0, 1, 2), CompornentName.LABEL1);

        // Text
        TextArea t1 = new TextArea();
        t1.setColumns(30);
        t1.setRows(3);
        CrudTestFrame.addCompornent(t1, LayoutHelper.createGridBagConstraints(1, 0, 1, 1), CompornentName.TEXT1);

        // Create trigger button
        Button b1 = new Button("Create");
        CrudTestFrame.addCompornent(b1, LayoutHelper.createGridBagConstraints(1, 1, 1, 1),
                CompornentName.CREATE_TRIGGER_BUTTON);

        // Choice
        Choice c1 = new Choice();
        CoreHelper.renewChoice(c1);
        CrudTestFrame.addCompornent(c1, LayoutHelper.createGridBagConstraints(2, 0, 1, 2), CompornentName.CHOICE1);

        // Label
        Label l2 = new Label();
        CrudTestFrame.addCompornent(l2, LayoutHelper.createGridBagConstraints(3, 0, 1, 2), CompornentName.LABEL2);

        // prit data button
        Button b2 = new Button("Print");
        CrudTestFrame.addCompornent(b2, LayoutHelper.createGridBagConstraints(4, 0, 1, 2),
                CompornentName.OUTPUT_LOG_BUTTON);

        // delete all data button
        Button b3 = new Button("Clear");
        CrudTestFrame.addCompornent(b3, LayoutHelper.createGridBagConstraints(5, 0, 1, 1),
                CompornentName.DELETE_ALL_LOG_BUTTON);

        // drop button
        Button b4 = new Button("Drop");
        CrudTestFrame.addCompornent(b4, LayoutHelper.createGridBagConstraints(5, 1, 1, 1),
                CompornentName.DELETE_ALL_TRIGER_BUTTON);

        // all delete log dialog
        DeleteLogDialog dl = new DeleteLogDialog(CrudTestFrame.getFrame(), l1);
        dl.setTitle("Dialog");
        dl.setSize(250, 100);
        dl.setLocationRelativeTo(CrudTestFrame.getFrame());
        CrudTestFrame.addSubWindow(dl, CompornentName.DELETE_CONFIRM_DIALOG);

        // drop all object dialog
        DeleteAllObjectDialog deleteAllObjectDialog = new DeleteAllObjectDialog(CrudTestFrame.getFrame(), l1, c1);
        deleteAllObjectDialog.setTitle("Dialog");
        deleteAllObjectDialog.setSize(350, 150);
        deleteAllObjectDialog.setLocationRelativeTo(CrudTestFrame.getFrame());
        CrudTestFrame.addSubWindow(deleteAllObjectDialog, CompornentName.DELETE_ALL_OBJECT_DIALOG);

        // print file dialog
        FileDialog fd = new FileDialog(CrudTestFrame.getFrame(), "出力ファイル選択", FileDialog.SAVE);
        CrudTestFrame.addSubWindow(fd, CompornentName.FILE_OUTPUT_DIALOG);

        // create trigger dialog
        CreateTriggerDialog cdl = new CreateTriggerDialog(CrudTestFrame.getFrame(), l1, t1,c1);
        cdl.setTitle("Dialog");
        cdl.setSize(350, 150);
        cdl.setLocationRelativeTo(CrudTestFrame.getFrame());
        CrudTestFrame.addSubWindow(cdl, CompornentName.CREATE_TRIGGER_BUTTON);

    }
}
