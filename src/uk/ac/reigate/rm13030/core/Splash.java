package uk.ac.reigate.rm13030.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.reigate.rm13030.storage.Database;

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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

/**
 * 
 * @author Robbie <http://reigate.ac.uk/>
 *
 */

public class Splash extends JFrame {

	private static final long serialVersionUID = -5862160684399610827L;
	
	private JPanel contentPane;
	private static Thread pollDB;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Splash frame = new Splash();
                    frame.setVisible(true);
                    pollDB.start();
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
        setBounds(100, 100, 223, 467);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JTextPane txtpnSplash = new JTextPane();
        txtpnSplash.setFont(new Font("DialogInput", Font.PLAIN, 13));
        txtpnSplash.setEditable(false);
        
        txtpnSplash.setText("Polling database server...");
        pollDB = new Thread(new Runnable() {
            public void run() {
                try {
        			txtpnSplash.setText("Database Server: "+(Database.getConnection().isValid(10) ? "Online!" : "Offline"));
        			System.out.println("ran the thread!");
        		} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
                 // code goes here.
            }
       });
        //txtpnSplash.setText("TODO: startup/version/dev log text here\r\n\r\n//could potentially link this text to SQL Database?\r\n~> e.g. Database Status [Online/Offline]");

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
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Splash.class.getResource("/evidence/logo2.png")));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
        					.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
        					.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        				.addGroup(Alignment.LEADING, gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(txtpnSplash, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        			.addContainerGap(7, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(lblNewLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtpnSplash, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
        			.addGap(86))
        );
        contentPane.setLayout(gl_contentPane);
    }
}