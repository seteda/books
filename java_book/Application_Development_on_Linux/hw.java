import java.awt.*;
import javax.swing.*;

public class
hw
{
    public static void
    main(String[] args)
    {
        //Create the top-level container
        JFrame frame = new JFrame();
        JLabel hi = new JLabel("Hello, world.");
        frame.getContentPane().add(hi, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // kicks the UI into action
        frame.setVisible(true);

    } // main
} // class hw
