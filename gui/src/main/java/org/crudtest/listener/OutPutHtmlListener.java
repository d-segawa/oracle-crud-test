package org.crudtest.listener;

import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.CoreHelper;

public class OutPutHtmlListener implements ActionListener {

    Choice choice;
    Label label;
    FileDialog fileDialog;
    Runnable runnable;

    public OutPutHtmlListener(Choice choice, Label label, FileDialog fileDialog, Runnable runnable) {
        this.choice = choice;
        this.label = label;
        this.fileDialog = fileDialog;
        this.runnable = runnable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("");

        try {
            if (validate(choice, fileDialog, label)) {
                Path filePath = Paths.get(fileDialog.getDirectory(), fileDialog.getFile());

                List<String> tableList = getItemList();
                // TODO
//                CoreHelper.printHtml(tableList, filePath);
                CoreHelper.printExcel(tableList, filePath);
                CoreHelper.deleteData(tableList);
            }
        } catch (Exception ex) {
            label.setText(ex.getMessage());
        }
    }

    boolean validate(Choice choice, FileDialog fileDialog, Label label) {
        if (choice.getSelectedItem() == null || choice.getSelectedItem().isEmpty()) {
            label.setText("トリガーがありません。");
            return false;
        }

        int count = CoreHelper.countData(getItemList());
        if (count <= 0) {
            label.setText("出力するデータがありません。");
            return false;
        }

        // TODO
//        String fileName = String.format("%s_%s.html", "CRUD", formattedSysDate());
        String fileName = String.format("%s_%s.xlsx", "CRUD", formattedSysDate());

        fileDialog.setFile(fileName);
        fileDialog.setVisible(true);

        if (fileDialog.getDirectory() == null || fileDialog.getFile() == null) {
            //            label.setText("出力先ファイルを指定してください。");
            return false;
        }

        return true;
    }

    List<String> getItemList() {
        int count = choice.getItemCount();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(choice.getItem(i));
        }
        return items;
    }

    String formattedSysDate() {
        return DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss").format(LocalDateTime.now());
    }

}
