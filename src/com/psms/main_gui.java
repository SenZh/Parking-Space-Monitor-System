package com.psms;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Canvas;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;


public class main_gui {

	private JFrame frame;
	JComboBox<String> comboBox_port = new JComboBox<String>();
	JComboBox<String> comboBox_rate = new JComboBox<String>();
	JComboBox<String> comboBox_databits = new JComboBox<String>();
	JComboBox<String> comboBox_stopbits = new JComboBox<String>();
	JComboBox<String> comboBox_parity = new JComboBox<String>();
	JTextArea textArea_out = new JTextArea();
	JButton Button_open = new JButton("\u6253\u5F00\u4E32\u53E3");
	JTextArea textArea_in = new JTextArea();
	JButton Button_send = new JButton("\u53D1\u9001");
	JButton Button_clear = new JButton("\u6E05\u5C4F");
	JTextArea textArea_nodeInfo;
	JTabbedPane tabbedPane;
	JLabel Label_isonline;
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					main_gui window = new main_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public main_gui() {
		initialize();
	}
	public JFrame getFrame()
	{
		return frame;
	}
	public void setVisiable(boolean flag)
	{
		frame.setVisible(flag);
	}
	
	public JComboBox<String> getPortBox()
	{
		return comboBox_port;
	}
	
	public String getRate()
	{
		return (String)comboBox_rate.getSelectedItem();
	}
	public String getStopBits() {
		
		return (String)comboBox_stopbits.getSelectedItem();
	}
	
	public String getDataBits()
	{
		return (String)comboBox_databits.getSelectedItem();
	}
	public int getParity()
	{
		return comboBox_parity.getSelectedIndex();
	}
	public String getPort()
	{
		return (String)comboBox_port.getSelectedItem();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("\u914D\u7F6E");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem menuItem = new JMenuItem("\u4E32\u53E3\u914D\u7F6E");
		menuItem.setBorderPainted(true);
		menuItem.setAlignmentX(Component.LEFT_ALIGNMENT);
		mnNewMenu_1.add(menuItem);
			
			JPanel panel = new JPanel();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			panel.add(tabbedPane, BorderLayout.CENTER);
			
			JPanel panel_parkingSts = new JPanel();
			tabbedPane.addTab("≥µŒª–≈œ¢", null, panel_parkingSts, null);
			panel_parkingSts.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_netInfo = new JPanel();
			tabbedPane.addTab("\u7F51\u7EDC\u4FE1\u606F", null, panel_netInfo, null);
			panel_netInfo.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane_2 = new JScrollPane();
			panel_netInfo.add(scrollPane_2, BorderLayout.WEST);
			
			textArea_nodeInfo = new JTextArea();
			textArea_nodeInfo.setEditable(false);
			textArea_nodeInfo.setColumns(35);
			scrollPane_2.setViewportView(textArea_nodeInfo);
			
			JPanel panel_test = new JPanel();
			tabbedPane.addTab("µ˜ ‘√Ê∞Â", null, panel_test, null);
			tabbedPane.setEnabledAt(2, true);
			panel_test.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_3 = new JPanel();
			panel_test.add(panel_3, BorderLayout.WEST);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_9 = new JPanel();
			panel_3.add(panel_9, BorderLayout.NORTH);
			panel_9.setLayout(new BorderLayout(0, 5));
			
			JPanel panel_5 = new JPanel();
			panel_9.add(panel_5, BorderLayout.NORTH);
			panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
			
			JPanel panel_6 = new JPanel();
			panel_5.add(panel_6);
			panel_6.setLayout(new GridLayout(0, 2, 0, 0));
			
			JPanel panel_7 = new JPanel();
			panel_6.add(panel_7);
			panel_7.setLayout(new GridLayout(5, 0, 0, 15));
			
				
				JLabel label = new JLabel("\u4E32\u53E3 :");
				label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
				panel_7.add(label);
				
				JLabel lblNewLabel = new JLabel("\u6CE2\u7279\u7387 :");
				lblNewLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
				panel_7.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel("\u6570\u636E\u4F4D :");
				lblNewLabel_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
				panel_7.add(lblNewLabel_1);
				
				JLabel lblNewLabel_2 = new JLabel("\u505C\u6B62\u4F4D :");
				lblNewLabel_2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
				panel_7.add(lblNewLabel_2);
				
				JLabel lblNewLabel_3 = new JLabel("\u6821\u9A8C\u4F4D :");
				lblNewLabel_3.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
				panel_7.add(lblNewLabel_3);
				
				JPanel panel_8 = new JPanel();
				panel_6.add(panel_8);
				panel_8.setLayout(new GridLayout(5, 1, 0, 15));
				
				
				panel_8.add(comboBox_port);
				
				
				comboBox_rate.setModel(new DefaultComboBoxModel<String>(new String[] {"9600", "38400", "115200"}));
				comboBox_rate.setSelectedIndex(2);
				panel_8.add(comboBox_rate);
				
				
				comboBox_databits.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "6", "7", "8"}));
				comboBox_databits.setSelectedIndex(3);
				panel_8.add(comboBox_databits);
				
				
				comboBox_stopbits.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "1.5", "2"}));
				panel_8.add(comboBox_stopbits);
				
		
				comboBox_parity.setModel(new DefaultComboBoxModel<String>(new String[] {"\u65E0\u6821\u9A8C", "\u5947\u6821\u9A8C", "\u5076\u6821\u9A8C"}));
				comboBox_parity.setSelectedIndex(0);
				panel_8.add(comboBox_parity);
				
				JPanel panel_10 = new JPanel();
				panel_9.add(panel_10, BorderLayout.SOUTH);
				

				panel_10.add(Button_open);
				Button_open.setMinimumSize(new Dimension(60, 30));
				
				JPanel panel_11 = new JPanel();
				panel_test.add(panel_11, BorderLayout.CENTER);
				panel_11.setLayout(new BorderLayout(0, 0));
				
				JScrollPane scrollPane = new JScrollPane();
				panel_11.add(scrollPane, BorderLayout.CENTER);
				textArea_out.setEditable(false);
				
		
				textArea_out.setLineWrap(true);
				scrollPane.setViewportView(textArea_out);
				
				JPanel panel_4 = new JPanel();
				panel_11.add(panel_4, BorderLayout.SOUTH);
				panel_4.setLayout(new BorderLayout(0, 0));
				
				JScrollPane scrollPane_1 = new JScrollPane();
				panel_4.add(scrollPane_1, BorderLayout.CENTER);
				textArea_in.setPreferredSize(new Dimension(60, 30));
				
		
				textArea_in.setLineWrap(true);
				textArea_in.setRows(3);
				scrollPane_1.setViewportView(textArea_in);
				
				JPanel panel_12 = new JPanel();
				panel_4.add(panel_12, BorderLayout.EAST);
				panel_12.setLayout(new BorderLayout(0, 0));
				Button_send.setPreferredSize(new Dimension(52, 35));
				
				
				panel_12.add(Button_send, BorderLayout.NORTH);
				Button_clear.setPreferredSize(new Dimension(68, 35));
				Button_clear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				
				
				panel_12.add(Button_clear, BorderLayout.SOUTH);
				
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.NORTH);
				panel_1.setLayout(new GridLayout(0, 15, 0, 0));
				
				Label_isonline = new JLabel("");
				Label_isonline.setIcon(new ImageIcon(main_gui.class.getResource("/image/offline.png")));
				Label_isonline.setIconTextGap(20);
				panel_1.add(Label_isonline);
	}

}
