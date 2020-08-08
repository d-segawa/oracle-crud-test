package org.crudtest.frame;

import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import org.crudtest.CompornentName;
import org.crudtest.listener.MyWindowListener;

public class CrudTestFrame {

    static final Frame frame;

    static final Map<Integer, Window> subWindow = new HashMap<Integer, Window>();

    static {
        frame = new Frame("CRUD TEST");

        frame.setSize(450, 250);
        // Closeボタン押下時に、System.exitするWindowListenreを設定
        frame.addWindowListener(new MyWindowListener());
        frame.setLocationRelativeTo(null);

        // オブジェクトの配置を制御する
        GridBagLayout gbl = new GridBagLayout();
//        FlowLayout gbl = new FlowLayout();
        frame.setLayout(gbl);
    }

    public static void addSubWindow(Window window, CompornentName name) {
        subWindow.put(name.toInt(), window);
    }

    public static <T extends Window> T getSubwindow(CompornentName name, Class<T> clazz) {
        return clazz.cast(subWindow.get(name.toInt()));
    }

    public static void visible() {
        // visibleはbuttonを配置してから設定する
        frame.setVisible(true);
    }

    public static <T extends Component> T getComponent(CompornentName compornentName, Class<T> clazz) {
        return clazz.cast(frame.getComponent(compornentName.toInt()));
    }

    public static void addCompornent(Component component, GridBagConstraints gridBagConstraints,
            CompornentName compornentName) {

        GridBagLayout gbl = (GridBagLayout) frame.getLayout();
        gbl.setConstraints(component, gridBagConstraints);
        frame.add(component, compornentName.toInt());
    }

    public static void addCompornent(Component component,
            CompornentName compornentName) {
        frame.add(component, compornentName.toInt());
    }

    public static void setListener(CompornentName compornentName, ActionListener listener) {
        Button b = (Button) frame.getComponent(compornentName.toInt());
        b.addActionListener(listener);
    }

    public static Frame getFrame() {
        return frame;
    }

}
