package uk.ac.reigate.rm13030.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Robbie <http://reigate.ac.uk/>
 *
 */

public class Splash extends JFrame {

	private static final long serialVersionUID = -5862160684399610827L;
	
	private JPanel contentPane;
    private boolean selectedCreateNew;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Splash frame = new Splash();
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
    public Splash() {
        setResizable(false);
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 387, 375);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblWelcome = new JLabel("Welcome to <PathFinder>");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 24));

        JTextPane txtpnSplash = new JTextPane();
        txtpnSplash.setFont(new Font("DialogInput", Font.PLAIN, 13));
        txtpnSplash.setEditable(false);
        txtpnSplash.setText("TODO: startup/version/dev log text here\r\n\r\n//could potentially link this text to SQL Database?\r\n~> e.g. Database Status [Online/Offline]");

        JButton btnStart = new JButton("Create New");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Application.main("true"); //Create new selected
                dispose();
            }
        });
        
        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Application.main("false"); //Load selected
        		dispose();
        	}
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.TRAILING)
        		.addComponent(txtpnSplash, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap(53, Short.MAX_VALUE)
        			.addComponent(lblWelcome)
        			.addGap(44))
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(7)
        			.addComponent(lblWelcome)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(txtpnSplash, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        				.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        				.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
        );
        contentPane.setLayout(gl_contentPane);
    }
	
}