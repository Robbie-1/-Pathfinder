package uk.ac.reigate.rm13030.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

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

public class Splash3 extends JFrame {

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
                    Splash3 frame = new Splash3();
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
    public Splash3() {
        setResizable(false);
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 224, 467);
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
        lblNewLabel.setIcon(new ImageIcon(Splash3.class.getResource("/evidence/logo2.png")));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        JLabel lblDatabase = new JLabel("Database Status:");
        
        JLabel lblConnecting = new JLabel("Connecting...");
        
        dbConnectThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Database.getConnection();
				try {
					Application.DB_Connection = Database.getConnection();
					lblConnecting.setText(!Application.DB_Connection.isClosed() ? "<html><p color=\"#49DB00\">ONLINE</p></html>" : 
																				 "<html><p color=\"#ff0000\">OFFLINE</p></html>");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					lblConnecting.setText("OFFLINE");
				} 
				
				progressBar.setIndeterminate(false);
				progressBar.setValue(lblConnecting.getText().contains("ONLINE") ? 100 : 0);
				//progressBar.setVisible(false);
			}
        	
        });
        
        dbConnectThread.start();
        
        JTextPane txtpnDgfdhgh = new JTextPane();
        txtpnDgfdhgh.setEditable(false);
        txtpnDgfdhgh.setText("TODO: put recent updates/dev log here\r\n\r\n\t\r\n\r\n              Splish Splash in the tub");
        
        
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 235, Short.MAX_VALUE)
        		.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
        					.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnLoad, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblDatabase)
        					.addPreferredGap(ComponentPlacement.UNRELATED, 40, Short.MAX_VALUE)
        					.addComponent(lblConnecting))
        				.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        				.addComponent(txtpnDgfdhgh, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
        			.addGap(27))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(lblNewLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(txtpnDgfdhgh, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblDatabase)
        				.addComponent(lblConnecting))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnStart)
        				.addComponent(btnLoad)))
        );
        contentPane.setLayout(gl_contentPane);
    }
}