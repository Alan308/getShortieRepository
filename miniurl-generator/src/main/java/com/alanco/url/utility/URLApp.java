package com.alanco.url.utility;

import java.awt.event.*;
import javax.swing.*;

public class URLApp extends JFrame implements ActionListener{
	
	String longURL = "";
	JLabel lblIn = new JLabel("Please enter long URL here: ");
	JTextField txtFldIn = new JTextField(40);
	JLabel lblOut = new JLabel("Short URL: ");
	JTextField txtFldOut = new JTextField(15); 
	JLabel lblStats = new JLabel();
	JButton btnGo = new JButton("Go");
	JButton btnCopy = new JButton("Copy");
	JButton btnCancel = new JButton("Cancel");
	static final long serialVersionUID = 1L;
	
	public URLApp() {
		
		GroupLayout gL = new GroupLayout(getContentPane());
		getContentPane().setLayout(gL);
		gL.setAutoCreateGaps(true);
		gL.setAutoCreateContainerGaps(true);
		
		gL.setHorizontalGroup(gL.createSequentialGroup()
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblIn)
						.addComponent(this.lblOut))
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.txtFldIn)
						.addComponent(this.txtFldOut)
						.addComponent(this.lblStats))
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.btnGo)
						.addComponent(this.btnCopy)
						.addComponent(this.btnCancel)));

		gL.linkSize(SwingConstants.HORIZONTAL, this.btnGo, this.btnCopy, this.btnCancel);				//Override buttons 'Preferred Size' attributes and force them to be the same size.
		
		gL.setVerticalGroup(gL.createSequentialGroup()
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.lblIn)
						.addComponent(this.txtFldIn)
						.addComponent(this.btnGo))
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.lblOut)
						.addComponent(this.txtFldOut)
						.addComponent(this.btnCopy))
				.addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.lblStats)
						.addComponent(this.btnCancel)));
		
		setTitle("Short URL Generator");
		pack();																			//pack() ensures frame size is correct to display all components.
		
		this.btnGo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				URLApp.this.longURL = URLApp.this.txtFldIn.getText();
				if(URLApp.this.txtFldIn.getText().trim().equals("")){
					//Object panel;
					JOptionPane alert = new JOptionPane(
							"Please enter a Valid URL!", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_OPTION);
					JDialog msg = alert.createDialog("Warning!");
					msg.setAlwaysOnTop(true);
				    msg.setVisible(true);
				}
				else {
					String lURL = URLApp.this.txtFldIn.getText();
					System.out.println("The Long URL is: " + lURL);
				}
				URLApp.this.txtFldOut.setText("ShortURL.com"); 
				URLApp.this.lblStats.setText("Stats");
			} 
		});
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new URLApp().setVisible(true);
            }
        });
    }



	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
