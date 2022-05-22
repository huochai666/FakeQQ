package view;

import model.QQUser;
import utils.MyTools;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FriendListPanel extends JPanel {

	public Icon icon;
	public QQUser user;

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public QQUser getUser() {
		return user;
	}

	public void setUser(QQUser user) {
		this.user = user;
	}

	/**
	 * Create the panel.
	 */
	public FriendListPanel(Icon icon,QQUser user) {
		this.icon = icon;
		this.user = user;
		ini();
	}

	void ini(){

		//原来尺寸
//		setPreferredSize(new Dimension(374,75));
		setPreferredSize(new Dimension(380,75));
		setBackground(Color.white);
		setLayout(null);

		JLabel friendList_profile_label = new JLabel();
		friendList_profile_label.setIcon(icon);
		friendList_profile_label.setBounds(20, 13, 50, 50);
		MyTools.loadProfile(user,friendList_profile_label);
		add(friendList_profile_label);

		JLabel friendList_nickname_label = new JLabel(user.getQq_nickname() == null?user.getQq_id():user.getQq_nickname());
		friendList_nickname_label.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		friendList_nickname_label.setForeground(Color.black);
		friendList_nickname_label.setBounds(82, 18, 282, 17);
		add(friendList_nickname_label);

		JLabel friendList_signature_label = new JLabel(user.getQq_signature());
		friendList_signature_label.setForeground(new Color(117,117,117));
		friendList_signature_label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		friendList_signature_label.setBounds(82, 42, 282, 15);
		add(friendList_signature_label);

		addMouseListener(new MouseAdapter() {

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
