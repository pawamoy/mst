import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.*;

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
        textPane.setEditable(false);
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
        MutableAttributeSet attrs = textPane.getInputAttributes();
        Font font = new Font("Verdana", Font.ITALIC, 20);

        Style style = textPane.addStyle("info", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.BLUE);

        style = textPane.addStyle("error", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.RED);

        style = textPane.addStyle("sent_message", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.GREEN);

        style = textPane.addStyle("received_message", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.BLACK);
        
        style = textPane.addStyle("help", textPane.getStyle("default"));
        StyleConstants.setForeground(style, Color.ORANGE);
    }
    
    public void Wizz()
    {
        //~ Print("sound played", "info");
          //~ try {
             //~ // Open an audio input stream.
             //~ URL url = this.getClass().getClassLoader().getResource("appdata/wizz.wav");
             //~ AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
             //~ // Get a sound clip resource.
             //~ Clip clip = AudioSystem.getClip();
             //~ // Open audio clip and load samples from the audio input stream.
             //~ clip.open(audioIn);
             //~ clip.start();
          //~ } catch (UnsupportedAudioFileException e) {
             //~ e.printStackTrace();
          //~ } catch (IOException e) {
             //~ e.printStackTrace();
          //~ } catch (LineUnavailableException e) {
             //~ e.printStackTrace();
          //~ }
          
Clip clip = AudioSystem.getClip();
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = AudioSystem.
            getAudioInputStream( "appdata/wizz.wav" );
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                JOptionPane.showMessageDialog(null, "Close to exit!");
            }
        });
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
        
        MutableAttributeSet attrs = textPane.getInputAttributes();
        Font font = new Font("Liberation Mono", Font.PLAIN, 14);
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        sDoc.setCharacterAttributes(0, sDoc.getLength() + 1, attrs, false);
    }
}
