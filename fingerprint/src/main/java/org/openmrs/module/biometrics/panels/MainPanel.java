package org.openmrs.module.biometrics.panels;

import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class MainPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    private EnrollFromScanner enrollFromScanner;
    private ScanFingerprint scanFingerprint;

    public MainPanel() throws IOException, JSONException {
        super(new GridLayout(1, 1));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initGUI();
    }

    private void initGUI() throws IOException, JSONException {

        scanFingerprint = new ScanFingerprint();
        scanFingerprint.setBackground(Color.white);
        add(scanFingerprint);
        scanFingerprint.init();
        scanFingerprint.updateFingersTools();
        setPreferredSize(new Dimension(500, 100));
    }
}
