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
    
    textField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            textFieldKeyPressed(evt);
        }
    });
       
    //On prévient notre JFrame que notre JPanel sera son content pane
    this.setContentPane(pan); 
    
    InitStyles(); 
  }
  
  public void InitStyles()
  {
        Style InfoStyle = textPane.addStyle("info", textPane.getStyle("default"));
        StyleConstants.setForeground(InfoStyle, Color.BLUE);
        
        Style ErrorStyle = textPane.addStyle("error", textPane.getStyle("default"));
        StyleConstants.setForeground(ErrorStyle, Color.RED);
        
        Style SentMessageStyle = textPane.addStyle("sent_message", textPane.getStyle("default"));
        StyleConstants.setForeground(SentMessageStyle, Color.GREEN);
  }
  
    private void textFieldKeyPressed(java.awt.event.KeyEvent evt) 
    {
    // Send command to parser to analyze the sentence.
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            Print("> " + textField.getText(), "sent_message");
            textField.setText("");
        }
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
