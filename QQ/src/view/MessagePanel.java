package view;


import model.Message;
import model.QQUser;
import utils.MyTools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class MessagePanel extends JPanel {

	private int type = 0;//为0的时候是刚刚登录完成时的消息框，为1时 代表聊天窗口左侧的消息框，右侧的时间以及消息提示都会-95
	private Message message;//消息信息
	private QQUser user; //给你发消息的人

	public MessagePanel(){
		ini();
	}

	public MessagePanel(int type,Message message,QQUser user) {
		this.type = type;
		this.message = message;
		this.user = user;
		ini();
	}
	void ini() {
		setLayout(null);
		setBackground(Color.white);
		if(type==0) {
			setPreferredSize(new Dimension(374,75));//设置强制大小
		}
		else {
			setPreferredSize(new Dimension(374-95,75));//设置强制大小
		}

		JLabel message_nickname_label = new JLabel(user.getQq_nickname());
		message_nickname_label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		message_nickname_label.setBounds(80, 20, 208, 18);
		add(message_nickname_label);

		JLabel message_content_label = new JLabel((String) message.getMsg());
		message_content_label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		message_content_label.setForeground(new Color(117,117,117));
		message_content_label.setBounds(81, 42, 231, 15);
		add(message_content_label);

		JLabel message_profile_label = new JLabel("profile");
		message_profile_label.setBounds(19, 13, 50, 50);
		MyTools.loadProfile(user,message_profile_label);
		add(message_profile_label);

		JLabel recentMessageTime_label = new JLabel(message.getSendTime().toString());
		recentMessageTime_label.setForeground(new Color(127,127,127));
		recentMessageTime_label.setHorizontalAlignment(SwingConstants.RIGHT);
		recentMessageTime_label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		if(type == 0) {
			recentMessageTime_label.setBounds(280, 20, 80, 15);
		}else {
			recentMessageTime_label.setBounds(280-95, 20, 80, 15);
		}

		add(recentMessageTime_label);

		JLabel unreadMessage_label = new JLabel(String.valueOf(message.getIsRead()));
		unreadMessage_label.setHorizontalAlignment(SwingConstants.CENTER);
		unreadMessage_label.setForeground(Color.WHITE);
		unreadMessage_label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		if(type == 0) {
			unreadMessage_label.setBounds(340, 42, 19, 15);
		}else {
			unreadMessage_label.setBounds(340-95, 42, 19, 15);
		}

		add(unreadMessage_label);

		JLabel unreadMessageBackground_label = new JLabel("N");
		unreadMessageBackground_label.setIcon(new ImageIcon(MessagePanel.class.getResource("/image/unreadMessageBackground.png")));
		if(type == 0) {
			unreadMessageBackground_label.setBounds(339, 39, 20, 20);
		}else {
			unreadMessageBackground_label.setBounds(339-95, 39, 20, 20);
		}

		add(unreadMessageBackground_label);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(242,242,242));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.white);
			}

		});
	}
}

