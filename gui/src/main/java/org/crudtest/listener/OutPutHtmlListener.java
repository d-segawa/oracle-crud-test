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

                int count = choice.getItemCount();
                List<String> items = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    items.add(choice.getItem(i));
                }
                CoreHelper.printHtml(items, filePath);
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

        String fileName = String.format("%s_%s.html", "CRUD", formattedSysDate());
        fileDialog.setFile(fileName);
        fileDialog.setVisible(true);

        if (fileDialog.getDirectory() == null || fileDialog.getFile() == null) {
            //            label.setText("出力先ファイルを指定してください。");
            return false;
        }

        return true;
    }

    String formattedSysDate() {
        return DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss").format(LocalDateTime.now());
    }

}
