package org.crudtest;

import org.crudtest.frame.CrudTestFrame;

public class GuiProcess {

    public void initation() {
        LocateCompornent lc = new LocateCompornent();
        lc.locateCompornent();

        AssociateEvent ae = new AssociateEvent();
        ae.assemble();

        CrudTestFrame.visible();
    }

}
