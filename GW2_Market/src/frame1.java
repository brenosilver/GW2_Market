import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class frame1 extends JFrame implements ActionListener {

	JPanel pane = new JPanel();
	FlowLayout layout = new FlowLayout(5,10,10);
	
	
	
	JButton pressme = new JButton("Run Crawler");
	JTextField bagNumber = new JTextField("", 5);
	JLabel lab = new JLabel("Bag Number");
	static TextArea text = new TextArea("", 10, 70);
	
	String bagText;
	
	frame1(){
		super("Guild Wars 2 Bag Market"); setBounds(100,200,800,250);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container con = this.getContentPane(); // inherit main frame
	    setLayout(layout);
	    
	    con.add(pane);    // JPanel containers default to FlowLayout
	    pressme.addActionListener(this);
	    pressme.setMnemonic('P'); // associate hotkey to button
	    
	    bagNumber.addActionListener(this);
	    pane.add(lab);
	    pane.add(bagNumber);
	    pane.add(pressme);
	    pane.add(text);
	    pressme.requestFocus();
	    setVisible(true); // make frame visible
	    
	}
	
	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if (source == pressme){
			text.replaceRange("", 0, 2000);
			BasicCrawlController crawl = new BasicCrawlController();
			String bagText = bagNumber.getText();

			try {
				crawl.startThis("C:\\sitecrawl", "1", bagText);
				BasicCrawler.clean();
				crawl = null;
				System.gc();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		
	}
}
