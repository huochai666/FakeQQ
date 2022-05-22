package view;

import dao.impl.ServiceDapImpl;
import model.Message;
import model.QQUser;
import tools.*;
import utils.MyTools;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ChatJFrame extends JFrame {

	private String qq_id;//好友列表属于哪个qq
	private ServiceDapImpl service;
	private QQUser user;
	private JButton sendMessageButton;
	private JPanel contentPane;
	private JTextArea valueTextArea;
	private int sum=0;
	private JPanel chatTop_jsp_panel;
	private JScrollBar js1;
	private JLabel nikename;
	private JButton sendImageButton;
	private long lastSendTime = 0;
	private JButton sendFileButton;

	public ChatJFrame(QQUser user,ServiceDapImpl service,String qq_id) {
		this.user = user;
		this.service =service;
		this.qq_id =qq_id;
		ini();
		listener();

	}

	void ini(){

		setUndecorated(true);
		setBounds(100, 100, 1232, 762);
		setBackground(new Color(0,0,0,0));

		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel chatBackground_panel = new JPanel();
		chatBackground_panel.setOpaque(false);
		chatBackground_panel.setBounds(8, 8, 1217, 748);
		contentPane.add(chatBackground_panel);
		chatBackground_panel.setLayout(null);




		JScrollPane chatTop_jsp = new JScrollPane();
		DemoScrollBarUI chatTopUI = new DemoScrollBarUI();
		chatTop_jsp.getVerticalScrollBar().setUI(chatTopUI);
		chatTop_jsp.setBounds(288, 105, 754, 450);

		chatTop_jsp_panel = new JPanel();
		chatTop_jsp_panel.setBackground(Color.WHITE);
		chatTop_jsp_panel.setPreferredSize(new Dimension(710,chatTop_jsp_panel.getPreferredSize().height));//这边设置搜索框尺寸（影响到右边滚动条

		chatTop_jsp_panel.setBorder(null);

		chatTop_jsp_panel.setLayout(new FlowLayout(1,0,0));
		chatTop_jsp.setViewportView(chatTop_jsp_panel);



		chatTop_jsp.validate();
		chatTop_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
		chatTop_jsp.setBorder(null);
		chatTop_jsp.setBackground(new Color(0,0,0,0));
		chatBackground_panel.add(chatTop_jsp);




		nikename = new JLabel(user.getQq_nickname()==null?user.getQq_id():user.getQq_nickname());
		nikename.setHorizontalAlignment(SwingConstants.CENTER);
		nikename.setForeground(Color.WHITE);
		nikename.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		nikename.setBounds(495, 10, 250, 28);
		nikename.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chatBackground_panel.add(nikename);

		JPanel chatValue_panel = new JPanel();
		chatValue_panel.setBackground(Color.WHITE);
		chatValue_panel.setBounds(288, 560, 756, 172);
		chatBackground_panel.add(chatValue_panel);
		chatValue_panel.setLayout(null);

		valueTextArea = new JTextArea();
		valueTextArea.setLineWrap(true);
		valueTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		valueTextArea.setBounds(11, 48, 694, 86);
		valueTextArea.setSelectionColor(new Color(0, 120, 215));
		valueTextArea.setSelectedTextColor(Color.WHITE);
//		chatValue_panel.add(valueTextArea);

		JScrollPane jsp = new JScrollPane(valueTextArea);
		jsp.setBorder(null);
		DemoScrollBarUI valueUI = new DemoScrollBarUI();
		jsp.getVerticalScrollBar().setUI(valueUI);
		jsp.setBounds(11, 48, 725, 86);
		//默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
		jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//把滚动条添加到容器里面
		chatValue_panel.add(jsp);



		sendImageButton = new JButton();
		sendImageButton.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendImageButton_default.png")));
		sendImageButton.setRolloverIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendImageButton_rollover.png")));
		sendImageButton.setBorder(null);
		sendImageButton.setContentAreaFilled(false);
		sendImageButton.setFocusPainted(false);
		sendImageButton.setBounds(11, 7, 36, 35);
		chatValue_panel.add(sendImageButton);

		sendFileButton = new JButton();
		sendFileButton.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendFileButton_default.png")));
		sendFileButton.setRolloverIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendFileButton_rollover.png")));
		sendFileButton.setBorder(null);
		sendFileButton.setContentAreaFilled(false);
		sendFileButton.setFocusPainted(false);
		sendFileButton.setBounds(52, 7, 36, 35);
		chatValue_panel.add(sendFileButton);

		JButton closeChatPanelButton = new JButton();
		closeChatPanelButton.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/closeChatPanelButton_default.png")));
		closeChatPanelButton.setRolloverIcon(new ImageIcon(ChatJFrame.class.getResource("/image/closeChatPanelButton_rollover.png")));
		closeChatPanelButton.setBorder(null);
		closeChatPanelButton.setContentAreaFilled(false);
		closeChatPanelButton.setFocusPainted(false);
		closeChatPanelButton.setBounds(519, 135, 90, 35);
		closeChatPanelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		chatValue_panel.add(closeChatPanelButton);




		sendMessageButton = new JButton();
		sendMessageButton.setMnemonic(KeyEvent.VK_S);
		sendMessageButton.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendMessageButton_default.png")));
		sendMessageButton.setRolloverIcon(new ImageIcon(ChatJFrame.class.getResource("/image/sendMessageButton_rollover.png")));
		sendMessageButton.setBorder(null);
		sendMessageButton.setContentAreaFilled(false);
		sendMessageButton.setFocusPainted(false);
		sendMessageButton.setBounds(633, 135, 78, 35);

		chatValue_panel.add(sendMessageButton);
		JScrollBar jScrollBar1 = jsp.getVerticalScrollBar();//获得垂直滚动条
		jScrollBar1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println(jScrollBar1.getMaximum());
			}

		});


		MinimizeButton minimize_Button = new MinimizeButton();
		minimize_Button.setFrame(this);
		minimize_Button.setBounds(1139, 0, 37, 40);
		chatBackground_panel.add(minimize_Button);

		CloseButton close_Button = new CloseButton(this);
		close_Button.setBounds(1176, 0, 40, 40);
		close_Button.removeActionListener(close_Button.getActionListeners()[0]);
		close_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		chatBackground_panel.add(close_Button);




		//消息滚动条（将panel放在里面。JScrollPane本身算是一个容器）
		JScrollPane message_jsp = new JScrollPane();
		message_jsp.setBackground(Color.white);
		DemoScrollBarUI messageUI = new DemoScrollBarUI();
		message_jsp.getVerticalScrollBar().setUI(messageUI);
		message_jsp.setBounds(0, 49, 287, 697);
		message_jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel message_panel = new JPanel();
		message_panel.setBackground(Color.white);
		message_panel.setBounds(-5, 0, 287, 697);
		message_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		message_panel.setPreferredSize(new Dimension(287,75*6));

		message_jsp.setViewportView(message_panel);
		message_jsp.validate();
		message_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
		message_jsp.setBorder(null);
		message_jsp.setBackground(new Color(0,0,0,0));
		chatBackground_panel.add(message_jsp);
//
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());
//		message_panel.add(new MessagePanel());

		JScrollBar jScrollBar = message_jsp.getVerticalScrollBar();//获得垂直滚动条
		jScrollBar.setValue(128);



		JLabel chatBackground_label = new JLabel();
		chatBackground_label.setVerticalAlignment(SwingConstants.TOP);
		chatBackground_label.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/chatBackground.png")));
		chatBackground_label.setBounds(-1, -2, 1217, 748);
		chatBackground_panel.add(chatBackground_label);


		JLabel chatBackgroundShadow_label = new JLabel("");
		chatBackgroundShadow_label.setIcon(new ImageIcon(ChatJFrame.class.getResource("/image/chatBackgroundShadow.png")));
		chatBackgroundShadow_label.setBounds(0, 0, 1232, 762);
		contentPane.add(chatBackgroundShadow_label);
		jScrollBar.setValue(128);

		js1 = chatTop_jsp.getVerticalScrollBar();//获得垂直滚动条


	}

	private void listener() {

		sendFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					//把文件选择器的样式设置成windows
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (Exception error) {
					error.printStackTrace();
				}
				JFileChooser jfc = new JFileChooser("C://");

				jfc.showOpenDialog(null);
				if (jfc.getSelectedFile() != null) {
					File file = jfc.getSelectedFile();
					if (file.isFile()) {
						byte[] bytes = MyTools.fileToByteArray(file.getAbsolutePath());
						Message message = new Message();
						message.setSendTime(new Date(System.currentTimeMillis()));
						message.setType(1);
						message.setIsRead(1);
						message.setSender(qq_id);
						message.setAccepter(user.getQq_id());
						message.setMsg(bytes);
						message.setFile(file);
						service.sendMessage(message);

						chatTop_jsp_panel.add(new ChatTopPart(1,message,service.getPersonData(qq_id),2,file));
						chatTop_jsp_panel.updateUI();
						sum=0;
						for(int i=0;i<chatTop_jsp_panel.getComponents().length;i++) {
							sum=sum+chatTop_jsp_panel.getComponent(i).getHeight();
						}

						JPanel a = new JPanel();
						a.setBackground(Color.white);
						a.setPreferredSize(new Dimension(754,20));
						chatTop_jsp_panel.add(a);
						chatTop_jsp_panel.updateUI();
						js1.setValue(sum);
						chatTop_jsp_panel.setPreferredSize(new Dimension(chatTop_jsp_panel.getWidth()-20,sum));

						valueTextArea.setText("");
						valueTextArea.requestFocus();



					}
				}





			}
		});

		sendImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//把文件选择器的样式设置成windows
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (Exception error) {
					error.printStackTrace();
				}
				JFileChooser jfc = new JFileChooser("C://");
				FileNameExtensionFilter filter =
						new FileNameExtensionFilter("图片文件(*.jpg, *.jpeg, *.gif, *.png)",
								"jpg", "jpeg", "png", "gif");
				jfc.setFileFilter(filter);
				jfc.showOpenDialog(null);
				if (jfc.getSelectedFile() != null) {
					File file = jfc.getSelectedFile();
					if (file.isFile()) {
						byte[] bytes = MyTools.fileToByteArray(file.getAbsolutePath());
						Message message = new Message();
						message.setSendTime(new Date(System.currentTimeMillis()));
						message.setType(2);
						message.setIsRead(1);
						message.setSender(qq_id);
						message.setAccepter(user.getQq_id());
						message.setMsg(bytes);
						service.sendMessage(message);

						chatTop_jsp_panel.add(new ChatTopPart(1,message,service.getPersonData(qq_id),1,file));
						chatTop_jsp_panel.updateUI();
						sum=0;
						for(int i=0;i<chatTop_jsp_panel.getComponents().length;i++) {
							sum=sum+chatTop_jsp_panel.getComponent(i).getHeight();
						}

						JPanel a = new JPanel();
						a.setBackground(Color.white);
						a.setPreferredSize(new Dimension(754,20));
						chatTop_jsp_panel.add(a);
						chatTop_jsp_panel.updateUI();
						js1.setValue(sum);
						chatTop_jsp_panel.setPreferredSize(new Dimension(chatTop_jsp_panel.getWidth()-20,sum));

						valueTextArea.setText("");
						valueTextArea.requestFocus();



					}
				}





			}

		});

		nikename.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				PersonDataJFrame pdf = new PersonDataJFrame(user,false);
				pdf.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nikename.setFont(new Font("微软雅黑", Font.BOLD, 22));
				String str = user.getQq_nickname()==null?user.getQq_id(): user.getQq_nickname();
				nikename.setText("<html><u>"+str+"</u></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nikename.setFont(new Font("微软雅黑", Font.PLAIN, 22));
				nikename.setText(user.getQq_nickname()==null?user.getQq_id(): user.getQq_nickname());
			}

		});



		sendMessageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				if(!valueTextArea.getText().equals("")){

					if(chatTop_jsp_panel.getComponentCount()!=0)
						//获取最后一个组件
						System.out.println("最后一次发的消息"+ ((ChatTopPart)chatTop_jsp_panel.getComponent(chatTop_jsp_panel.getComponentCount()-1)).message);

					//判断是否在前面插入时间
					long time = System.currentTimeMillis();
					long time2 = lastSendTime;
					int Minutes = (int)(time - time2) / (1000 * 60 );
					//若距离上一次发消息超过一分钟，则插入时间轴
					if(Minutes>1){
						JLabel timeSign = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
						timeSign.setFont(new Font("微软雅黑", Font.PLAIN, 15));
						timeSign.setForeground(new Color(127,127,127));
						chatTop_jsp_panel.add(timeSign);
					}



					Message message = new Message();
					message.setSender(qq_id);
					message.setAccepter(user.getQq_id());
					message.setMsg(valueTextArea.getText());
					message.setType(0);
					message.setIsRead(1);
					message.setSendTime( new Date(System.currentTimeMillis()));
					service.sendMessage(message);
					System.out.println("即将发送："+message);
					chatTop_jsp_panel.add(new ChatTopPart(1,message,service.getPersonData(qq_id),0,null));
					lastSendTime = message.getSendTime().getTime();
					chatTop_jsp_panel.updateUI();

					sum=0;
					for(int i=0;i<chatTop_jsp_panel.getComponents().length;i++) {
						sum=sum+chatTop_jsp_panel.getComponent(i).getHeight();
					}


					js1.setValue(sum);
					chatTop_jsp_panel.setPreferredSize(new Dimension(chatTop_jsp_panel.getWidth()-20,sum));

					valueTextArea.setText("");
					valueTextArea.requestFocus();
				}
			}

		});
	}

	public void acceptMessage(Message message){
		//2是图片,1是文件
		byte[] bytes = null;

		if(message.getType()==1){
			bytes = (byte[]) message.getMsg();
			System.out.println("收到一个文件，大小为"+bytes.length);
			String filePath = "C:\\miniQQHead\\file\\"+message.getFile().getName();
			MyTools.byteArrayToFile(bytes,filePath);

			//判断是否在前面插入时间

			long time = System.currentTimeMillis();
			long time2 = lastSendTime;
			int Minutes = (int)(time - time2) / (1000 * 60 );
			//若距离上一次发消息超过一分钟，则插入时间轴
			if(Minutes>1){
				JLabel timeSign = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
				timeSign.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				timeSign.setForeground(new Color(127,127,127));
				chatTop_jsp_panel.add(timeSign);
			}



			chatTop_jsp_panel.add(new ChatTopPart(0,message,user,2,new File(filePath)));
			lastSendTime = message.getSendTime().getTime();
			chatTop_jsp_panel.updateUI();
		}else if(message.getType()==2){
			bytes = (byte[]) message.getMsg();
			System.out.println("收到一个图片文件，大小为"+bytes.length);
			String filePath = "C:\\miniQQHead\\"+new SimpleDateFormat("HH-mm-ss").format(new Date(System.currentTimeMillis()))+".png";
			MyTools.byteArrayToFile(bytes,filePath);

			//判断是否在前面插入时间

			long time = System.currentTimeMillis();
			long time2 = lastSendTime;
			int Minutes = (int)(time - time2) / (1000 * 60 );
			//若距离上一次发消息超过一分钟，则插入时间轴
			if(Minutes>1){
				JLabel timeSign = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
				timeSign.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				timeSign.setForeground(new Color(127,127,127));
				chatTop_jsp_panel.add(timeSign);
			}


			chatTop_jsp_panel.add(new ChatTopPart(0,message,user,1,new File(filePath)));
			lastSendTime = message.getSendTime().getTime();
			chatTop_jsp_panel.updateUI();
		}else {

			//判断是否在前面插入时间
			long time = System.currentTimeMillis();
			long time2 = lastSendTime;
			int Minutes = (int)(time - time2) / (1000 * 60 );
			//若距离上一次发消息超过一分钟，则插入时间轴
			if(Minutes>1){
				JLabel timeSign = new JLabel(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
				timeSign.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				timeSign.setForeground(new Color(127,127,127));
				chatTop_jsp_panel.add(timeSign);
			}


			chatTop_jsp_panel.add(new ChatTopPart(0,message,user,0,null));
			lastSendTime = message.getSendTime().getTime();
			chatTop_jsp_panel.updateUI();
		}

		sum=0;
		for(int i=0;i<chatTop_jsp_panel.getComponents().length;i++) {
			sum=sum+chatTop_jsp_panel.getComponent(i).getHeight();
		}

		chatTop_jsp_panel.setPreferredSize(new Dimension(chatTop_jsp_panel.getWidth()-20,sum));

	}

}
