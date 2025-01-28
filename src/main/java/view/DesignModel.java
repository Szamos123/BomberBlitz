package view;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DesignModel {
    Color bgcolor = new Color(42, 9, 68);
    Color textColor = new Color(254, 194, 96);
    Color primaryColor = new Color(161, 37, 104);
    Color secondaryColor = new Color(59, 24, 95);
    Color darkAccentColor = new Color(25, 5, 40);
    Font font;

    public DesignModel() {
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("Button.select", new ColorUIResource(new Color(0, 0, 0, 0)));
        try {
            Font firstFont = Font.createFont(Font.TRUETYPE_FONT, new File("./bomberblitz/src/assets/04B_03__.TTF"));
            font = firstFont.deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
