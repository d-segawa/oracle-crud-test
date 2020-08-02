package org.crudtest;

public enum CompornentName {

    // TODO refactoring
    LABEL1(0), TEXT1(1), CHOICE1(3), CREATE_TRIGGER_BUTTON(2), OUTPUT_LOG_BUTTON(4), DELETE_ALL_LOG_BUTTON(
            5), DELETE_ALL_TRIGER_BUTTON(6), FILE_OUTPUT_DIALOG(
                    7), DELETE_CONFIRM_DIALOG(8), DELETE_ALL_TRIGER_DIALOG(9), CREATE_ALL_TRIGER_DIALOG(10);

    int i;

    private CompornentName(int i) {
        this.i = i;
    }

    public int toInt() {
        return this.i;
    }

}
