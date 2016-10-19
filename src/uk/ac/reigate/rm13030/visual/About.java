package uk.ac.reigate.rm13030.visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

public class About extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    About frame = new About();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public About() {
        setResizable(false);
        setTitle("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 272, 329);

        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setToolTipText("");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel title = new JLabel("<PathFinder>");
        title.setFont(new Font("Tahoma", Font.PLAIN, 24));

        JLabel reigateLink = new JLabel("http://www.reigate.ac.uk");
        reigateLink.setToolTipText("Go to: www.reigate.ac.uk");
        reigateLink.setFont(new Font("Tahoma", Font.PLAIN, 13));
        reigateLink.setText("<html><a href=\"\">Reigate College</a></html>");
        reigateLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reigateLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                        Desktop.getDesktop().browse(new URI("http://www.reigate.ac.uk"));
                    }
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });

        JLabel reigateImg = new JLabel("");
        reigateImg.setIcon(new ImageIcon(About.class.getResource("/SF-ReigateCollege.png")));

        JEditorPane editorPane = new JEditorPane();
        editorPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
        editorPane.setEditable(false);
        editorPane.setText("     \r\n\r\n                  \r\n         A project by Robbie McLeod");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPane.createSequentialGroup()
                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                    .addComponent(reigateImg)
                    .addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addGroup(gl_contentPane.createSequentialGroup()
                        .addGap(54)
                        .addComponent(title)))
                .addContainerGap())
            .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addComponent(reigateLink, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                .addGap(78))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_contentPane.createSequentialGroup()
                .addComponent(title, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(reigateLink)
                .addGap(13)
                .addComponent(reigateImg))
        );
        contentPane.setLayout(gl_contentPane);
    }
}