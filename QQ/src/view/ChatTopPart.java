package view;

import model.Message;
import model.QQUser;
import utils.MyTools;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ChatTopPart extends JPanel {

	int type = 1;//为1时代表自己的发言，为0时是对面
	public Message message = null;
	private QQUser user;
	int pictureOrFile = 0;//为0时是文本消息，1是图片，2是文件
	ImageIcon imageIcon;
	File file;

	public ChatTopPart(int type, Message message, QQUser user, int pictureOrFile, File file) {
		this.type = type;
		this.message = message;

		if(message.getType()==2 || message.getType()==1){

		}else {
			this.message.setMsg(check((String) message.getMsg()));
		}

		this.user = user;
		this.pictureOrFile = pictureOrFile;
		if(pictureOrFile==1){
			this.imageIcon = new ImageIcon(file.getAbsolutePath());
			this.file =file;
		}else if(pictureOrFile==2){
			this.file = file;
		}

		ini();
	}

	void ini() {

		System.out.println("type:"+type+",pictureOrFile:"+pictureOrFile);
		if (type==1) {//自己发送的
			if(pictureOrFile==0){//消息
				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,50));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(689, 5, 38, 38);
				System.out.println("自己发的，"+user);
				MyTools.loadProfile(user,profile_label);
				add(profile_label);


				JPanel chatBubble = new JPanel();
//				chatBubble.setBorder(new LineBorder(Color.white, 2, true));
				chatBubble.setBackground(new Color(18,183,245));
				chatBubble.setLayout(new FlowLayout(1,0,0));
				chatBubble.setBounds(80, 17, 593, 26);

				add(chatBubble);

				JLabel justSpace = new JLabel();
				chatBubble.add(justSpace);


				JTextArea chatText_textArea = new JTextArea();
				chatText_textArea.setForeground(Color.WHITE);
				chatText_textArea.setBackground(new Color(18,183,245));
				chatText_textArea.setWrapStyleWord(true);
				chatText_textArea.setSelectionColor(new Color(51,153,255));
				chatText_textArea.setText((String) message.getMsg());
				chatText_textArea.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				chatText_textArea.setSelectedTextColor(Color.white);
				chatBubble.add(chatText_textArea);

				justSpace.setPreferredSize(new Dimension(chatText_textArea.getPreferredScrollableViewportSize().width+30,3));

				setSize(getWidth(),chatText_textArea.getPreferredScrollableViewportSize().height+56);
				setPreferredSize(new Dimension(729,25+chatText_textArea.getPreferredScrollableViewportSize().height));
				chatBubble.setLocation(729-80-chatText_textArea.getPreferredScrollableViewportSize().width, 16);
				chatBubble.setSize(chatText_textArea.getPreferredScrollableViewportSize().width+30,chatText_textArea.getPreferredScrollableViewportSize().height+20);
			}if(pictureOrFile==1){//自己发的图片

				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,50));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(689, 5, 38, 38);
				System.out.println("自己发的，"+user);
				MyTools.loadProfile(user,profile_label);
				add(profile_label);


				JPanel chatBubble = new JPanel();

				chatBubble.setBorder(new LineBorder(new Color(18,183,245), 2, true));
				chatBubble.setBackground(new Color(18,183,245));
				chatBubble.setLayout(new FlowLayout(1,0,0));
				chatBubble.setBounds(86, 17, 593, 26);

				add(chatBubble);

				JLabel justSpace = new JLabel();
				chatBubble.add(justSpace);

				JTextPane chatText_textArea = new JTextPane();
				chatText_textArea.insertIcon(imageIcon);
				chatText_textArea.setForeground(Color.WHITE);
				chatText_textArea.setBackground(new Color(18,183,245));
				chatText_textArea.setSelectionColor(new Color(51,153,255));

				chatText_textArea.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int clickTimes = e.getClickCount();
						if (clickTimes == 2) {
							try {
								Desktop.getDesktop().open(file);
							} catch (IOException ioException) {
								ioException.printStackTrace();
							}
						}
					}
				});

				chatText_textArea.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				chatText_textArea.setSelectedTextColor(Color.white);

				chatBubble.add(chatText_textArea);

				justSpace.setPreferredSize(new Dimension(chatText_textArea.getPreferredScrollableViewportSize().width+30,3));

				setSize(getWidth(),chatText_textArea.getPreferredScrollableViewportSize().height+56);
				setPreferredSize(new Dimension(729,25+chatText_textArea.getPreferredScrollableViewportSize().height));
				chatBubble.setLocation(729-80-chatText_textArea.getPreferredScrollableViewportSize().width, 16);
				chatBubble.setSize(chatText_textArea.getPreferredScrollableViewportSize().width+30,chatText_textArea.getPreferredScrollableViewportSize().height+20);

			}if(pictureOrFile==2){//自己发送的文件


				System.out.println("自己发了个文件");
				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,159));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(689, 5, 38, 38);
				MyTools.loadProfile(user,profile_label);
				add(profile_label);




				//1
				JPanel sendFilePanel = new JPanel();
				sendFilePanel.setSize(369, 121);
				sendFilePanel.setLayout(null);
				add(sendFilePanel);

				//文件名
				JLabel fileName = new JLabel(file.getName());
				fileName.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				fileName.setBounds(73, 18, 197, 15);
				sendFilePanel.add(fileName);

				//文件路径
				JLabel fileTips = new JLabel("成功发送文件"+file.getName());
				fileTips.setForeground(new Color(136,136,136));
				fileTips.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				fileTips.setBounds(74, 47, 246, 16);
				sendFilePanel.add(fileTips);



				JButton openFileDirButton = new JButton();
				openFileDirButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Desktop.getDesktop().open(new File(file.getParent()));
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
					}
				});
				openFileDirButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				openFileDirButton.setIcon(new ImageIcon(Login.class.getResource("/image/openFileDir.png")));
				openFileDirButton.setBounds(280, 93, 79, 17);
				sendFilePanel.add(openFileDirButton);

				JButton openFileButton = new JButton();
				openFileButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}

					}
				});
				openFileButton.setBorder(null);
				openFileButton.setFocusPainted(false);
				openFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				openFileButton.setIcon(new ImageIcon(Login.class.getResource("/image/openFile.png")));
				openFileButton.setBounds(237, 93, 33, 18);
				sendFilePanel.add(openFileButton);

				JLabel SendFileBackground_label = new JLabel();

				SendFileBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/sendFileBackground.png")));
				SendFileBackground_label.setBounds(0, 0, 369, 121);
				sendFilePanel.add(SendFileBackground_label);
				//2



				sendFilePanel.setLocation(300, 16);
			}


		}else {//对面传来的消息
			if(pictureOrFile==0){
				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,50));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(7, 5, 38, 38);
				System.out.println("收到消息，显示的qq为"+user.getQq_id());
				MyTools.loadProfile(user,profile_label);
				add(profile_label);


				JPanel chatBubble = new JPanel();
				chatBubble.setBackground(new Color(229,229,229));
				chatBubble.setLayout(new FlowLayout(1,0,0));
				chatBubble.setBounds(70, 16, 593, 26);

				add(chatBubble);

				JLabel justSpace = new JLabel();
				chatBubble.add(justSpace);


				JTextArea chatText_textArea = new JTextArea();
				chatBubble.add(chatText_textArea);
				chatText_textArea.setForeground(Color.black);
				chatText_textArea.setBackground(new Color(229,229,229));
				chatText_textArea.setWrapStyleWord(true);
				chatText_textArea.setText((String) message.getMsg());
				chatText_textArea.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				chatText_textArea.setSelectionColor(new Color(51,153,255));
				chatText_textArea.setSelectedTextColor(Color.white);

				justSpace.setPreferredSize(new Dimension(chatText_textArea.getPreferredScrollableViewportSize().width+30,3));

				setSize(getWidth(),chatText_textArea.getPreferredScrollableViewportSize().height+56);
				chatBubble.setSize(chatText_textArea.getPreferredScrollableViewportSize().width+30,chatText_textArea.getPreferredScrollableViewportSize().height+20);

				setPreferredSize(new Dimension(729,25+chatText_textArea.getPreferredScrollableViewportSize().height));
			}
			if(pictureOrFile==1){//对面传来的图片

				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,50));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(7, 5, 38, 38);
				System.out.println("收到消息，显示的qq为"+user.getQq_id());
				MyTools.loadProfile(user,profile_label);
				add(profile_label);

				JPanel chatBubble = new JPanel();
				chatBubble.setBackground(new Color(229,229,229));
				chatBubble.setLayout(new FlowLayout(1,0,0));
				chatBubble.setBounds(70, 16, 593, 26);
				add(chatBubble);

				JLabel justSpace = new JLabel();
				chatBubble.add(justSpace);

				JTextPane chatText_textArea = new JTextPane();
				chatText_textArea.insertIcon(imageIcon);
				chatText_textArea.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int clickTimes = e.getClickCount();
						if (clickTimes == 2) {
							try {
								Desktop.getDesktop().open(file);
							} catch (IOException ioException) {
								ioException.printStackTrace();
							}
						}
					}
				});
				chatText_textArea.setForeground(Color.WHITE);
				chatText_textArea.setBackground(new Color(18,183,245));
				chatText_textArea.setSelectionColor(new Color(51,153,255));

				chatText_textArea.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				chatText_textArea.setSelectedTextColor(Color.white);

				chatBubble.add(chatText_textArea);

				justSpace.setPreferredSize(new Dimension(chatText_textArea.getPreferredScrollableViewportSize().width+30,3));

				setSize(getWidth(),chatText_textArea.getPreferredScrollableViewportSize().height+56);
				chatBubble.setSize(chatText_textArea.getPreferredScrollableViewportSize().width+30,chatText_textArea.getPreferredScrollableViewportSize().height+20);

				setPreferredSize(new Dimension(729,25+chatText_textArea.getPreferredScrollableViewportSize().height));

			}if(pictureOrFile==2){//收到的文件

				System.out.println("收到一个文件");
				setBackground(Color.WHITE);
				setPreferredSize(new Dimension(729,159));
				setLayout(null);

				JLabel profile_label = new JLabel();
				profile_label.setBounds(7, 5, 38, 38);
				MyTools.loadProfile(user,profile_label);
				add(profile_label);

				//1
				JPanel sendFilePanel = new JPanel();
				sendFilePanel.setSize(369, 121);
				sendFilePanel.setLayout(null);
				add(sendFilePanel);

				//文件名
				JLabel fileName = new JLabel(file.getName());
				fileName.setFont(new Font("微软雅黑", Font.PLAIN, 17));
				fileName.setBounds(73, 18, 197, 15);
				sendFilePanel.add(fileName);

				//文件路径
				JLabel fileTips = new JLabel("成功存至 C:\\miniQQHead\\file"+file.getName());
				fileTips.setForeground(new Color(136,136,136));
				fileTips.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				fileTips.setBounds(74, 47, 246, 16);
				sendFilePanel.add(fileTips);


				JButton openFileDirButton = new JButton();
				openFileDirButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Desktop.getDesktop().open(new File(file.getParent()));
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
					}
				});
				openFileDirButton.setBorder(null);
				openFileDirButton.setFocusPainted(false);
				openFileDirButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				openFileDirButton.setIcon(new ImageIcon(Login.class.getResource("/image/openFileDir.png")));
				openFileDirButton.setBounds(280, 93, 79, 17);
				sendFilePanel.add(openFileDirButton);

				JButton openFileButton = new JButton();
				openFileButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
					}
				});
				openFileButton.setBorder(null);
				openFileButton.setFocusPainted(false);
				openFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				openFileButton.setIcon(new ImageIcon(Login.class.getResource("/image/openFile.png")));
				openFileButton.setBounds(237, 93, 33, 18);
				sendFilePanel.add(openFileButton);

				JLabel SendFileBackground_label = new JLabel();

				SendFileBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/sendFileBackground.png")));
				SendFileBackground_label.setBounds(0, 0, 369, 121);
				sendFilePanel.add(SendFileBackground_label);
				//2



				sendFilePanel.setLocation(56, 16);
			}


		}

	}

	public String check(String s) {
		StringBuilder sb = new StringBuilder(s);
		int count = 0;
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '\n') {
				count = 0;
			} else if (count == 30) {
				sb.insert(i, "\n");
				count = 0;
			} else {
				count++;
			}
		}
		return sb.toString();
	}

}
