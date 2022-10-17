package org.crudtest.gui;

import org.crudtest.gui.frame.CrudTestFrame;

public class GuiProcess {

    public void initation() {
        LocateCompornent lc = new LocateCompornent();
        lc.locateCompornent();

        AssociateEvent ae = new AssociateEvent();
        ae.assemble();

        CrudTestFrame.visible();
    }

}
