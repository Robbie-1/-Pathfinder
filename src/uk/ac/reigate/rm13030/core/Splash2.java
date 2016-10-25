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

public class Splash2 extends JFrame {

	private static final long serialVersionUID = -5862160684399610827L;
	
	private JPanel contentPane;
	private static Thread dbConnectThread;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Splash2 frame = new Splash2();
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
    public Splash2() {
        setResizable(false);
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 223, 467);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
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
        lblNewLabel.setIcon(new ImageIcon(Splash2.class.getResource("/evidence/logo2.png")));
        
        JProgressBar progressBar = new JProgressBar();
        //progressBar.setIndeterminate(true);

        
        JButton btnConnectToDatabase = new JButton("connect to database");
        btnConnectToDatabase.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			progressBar.setIndeterminate(true);
        			
        			dbConnectThread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
		        			Database.getConnection();
		        			progressBar.setIndeterminate(false);
						}
        				
        			});
        			
        			dbConnectThread.start();
        		} finally {
        			//progressBar.setIndeterminate(false);
        		}
        	}
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNewLabel)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(28)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        						.addComponent(btnConnectToDatabase)
        						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(lblNewLabel)
        			.addGap(119)
        			.addComponent(btnConnectToDatabase)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(68)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        				.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }
}