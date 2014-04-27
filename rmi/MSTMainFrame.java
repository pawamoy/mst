import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

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
    pan.setBounds(50, 630, 50, 390);
    pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
    
    textPane = new JTextPane();
    pan.add(textPane);
    
    textField = new JTextField();
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
        Style InfoStyle = textPane.addStyle("info", textPane.getStyle("default"));
        StyleConstants.setForeground(InfoStyle, Color.BLUE);
        
        Style ErrorStyle = textPane.addStyle("error", textPane.getStyle("default"));
        StyleConstants.setForeground(ErrorStyle, Color.RED);
        
        Style SentMessageStyle = textPane.addStyle("sent_message", textPane.getStyle("default"));
        StyleConstants.setForeground(SentMessageStyle, Color.GREEN);
        
        Style SentMessageStyle = textPane.addStyle("received_message", textPane.getStyle("default"));
        StyleConstants.setForeground(SentMessageStyle, Color.BLACK);
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
