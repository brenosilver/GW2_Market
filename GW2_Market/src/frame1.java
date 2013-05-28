import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class frame1 extends JFrame implements ActionListener {

	JPanel pane = new JPanel();
	//FlowLayout layout = new FlowLayout(5,10,10);
	GridLayout layout = new GridLayout();
	
	
	JButton pressme = new JButton("Run Crawler");
	JRadioButton opt1 = new JRadioButton("Supply bag", true);
	JRadioButton opt2 = new JRadioButton("Stolen Supplies bag", false);
	static TextArea text = new TextArea("", 10, 70);
	
	ButtonGroup group = new ButtonGroup();
	
	String bagText;
	
	frame1(){
		super("Guild Wars 2 Bag Market"); setBounds(100,200,800,250);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   // Container con = this.getContentPane(); // inherit main frame
	    setLayout(layout);
	    super.setResizable(false);
	    
	    group.add(opt1);
	    group.add(opt2);
		
	   // con.add(pane);    // JPanel containers default to FlowLayout
	    pressme.addActionListener(this);
	    pressme.setMnemonic('P'); // associate hotkey to button
	    
	    super.getContentPane().add(opt1);
	    super.getContentPane().add(opt2);
	    super.getContentPane().add(pressme);
	    super.getContentPane().add(text);
	    
	    opt1.addActionListener(this);
	    opt2.addActionListener(this);

	    pressme.requestFocus();
	    
	    setVisible(true); // make frame visible
	    
	}
	

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if (source == pressme){
			text.replaceRange("", 0, 2000);
			BasicCrawlController crawl = new BasicCrawlController();
			// Options
			String bagText = "1";
			if (opt1.isSelected())
				bagText = "1";
			else if (opt2.isSelected())
				bagText = "2";

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
