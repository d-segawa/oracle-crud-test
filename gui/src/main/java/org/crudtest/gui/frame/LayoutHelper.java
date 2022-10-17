package org.crudtest.gui.frame;

import java.awt.GridBagConstraints;

public class LayoutHelper {

    public static GridBagConstraints createGridBagConstraints(int y, int w) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = y;
        gbc.gridwidth = w;
        return gbc;
    }

    /**
     * @param y y座標 0始まり
     * @param x x座標 0始まり
     * @param h 縦のセル数　１始まり、結合する場合は数値を増やす
     * @param w 横のセル数　１始まり、結合する場合は数値を増やす
     * @return
     */
    public static GridBagConstraints createGridBagConstraints(int y, int x, int h, int w) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = y;
        gbc.gridx = x;
        gbc.gridheight = h;
        gbc.gridwidth = w;
        return gbc;
    }

}
