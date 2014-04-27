import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

public class MSTMainFrame extends JFrame 
{
    private JTextPane textPane;
    private JTextField textField;
    
    public MSTMainFrame()
    {
        this.setTitle("MST");
        this.setSize(640, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
        this.setVisible(true);
        
        //Instanciation d'un objet JPanel
        JPanel pan = new JPanel();
        pan.setBorder(new EmptyBorder(10, 10, 10, 10) );
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        
        textPane = new JTextPane();
        textPane.setBorder(new EmptyBorder(10, 10, 10, 10) );
        pan.add(new JScrollPane(textPane));
        
        DefaultCaret caret = (DefaultCaret)textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        textField = new JTextField();
        textField.setMaximumSize(
            new Dimension(Integer.MAX_VALUE,
            textField.getPreferredSize().height));
        pan.add(textField);
           
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(pan); 
        
        InitStyles(); 
    }
  
    public JTextField GetTextField()
    {
        return textField;
    }

    public void InitStyles()
    {
        Style style = textPane.addStyle("info", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.BLUE);

        style = textPane.addStyle("error", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.RED);

        style = textPane.addStyle("sent_message", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.GREEN);

        style = textPane.addStyle("received_message", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.BLACK);
    }
    
    public void Print(String msg, String style)
    {
        StyledDocument sDoc = (StyledDocument)textPane.getDocument();
        String str = msg + '\n';
        try 
        {
            sDoc.insertString(sDoc.getLength(), str, textPane.getStyle(style));
        } 
        catch (BadLocationException e) { }
    }
}
