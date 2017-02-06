package org.openmrs.module.biometrics;

import com.neurotec.lang.NCore;
import org.json.JSONException;
import org.openmrs.module.biometrics.panels.MainPanel;

import javax.swing.*;
import java.applet.Applet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SimpleFingersApplication extends Applet {

    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {

                    MainPanel panel = null;
                    try {
                        panel = new MainPanel();
                    } catch (IOException e) {
                        e.getStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    add(panel);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void start() {
    }

    public void stop() {
    }

    public void destroy() {
        NCore.shutdown();
    }
}
