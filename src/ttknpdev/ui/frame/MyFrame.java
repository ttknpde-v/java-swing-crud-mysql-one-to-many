package ttknpdev.ui.frame;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame(String title) throws HeadlessException {
        super(title);
        setLocation(250, 285); // location frame when created
        setSize(500,500);
        setResizable(false); // set JFrame that can not change size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // dispose on close means closing one by one frame
    }
}
