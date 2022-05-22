package view;

import dao.impl.ServiceDapImpl;
import model.QQUser;
import utils.MyTools;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//在主面板搜索后的好友列表框

public class SearchResultJPanel extends JPanel {

	//设想：通过QQ号自动识别昵称和头像
	private QQUser user;
	private String blueFont;
	private ServiceDapImpl server;

	public SearchResultJPanel(QQUser user, String blueFont, ServiceDapImpl server) {
		this.user = user;
		this.blueFont = blueFont;
		this.server = server;
		ini();
	}

	void ini(){

		setLayout(null);
		setBackground(Color.white);
		setPreferredSize(new Dimension(377,65));

		JLabel search_profile_label = new JLabel("Ne");
		search_profile_label.setBounds(19, 10, 45, 45);
		MyTools.loadProfile(user,search_profile_label);
		add(search_profile_label);

		//用来判断什么字是蓝色的
		String strBlue = "<HTML><font color=#000000>"+user.getQq_nickname()+"</font><font color=#898989> ("+user.getQq_id()+")</font></html>";
		String newStrBlue = strBlue.replace(blueFont,"<font color=#009bdb>"+blueFont+"</font>");

		JLabel search_nickname_label = new JLabel(newStrBlue);
		search_nickname_label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		search_nickname_label.setBounds(76, 10, 221, 18);
		add(search_nickname_label);

		JLabel search_from_label = new JLabel("来自：我的好友");
		search_from_label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		search_from_label.setForeground(new Color(130,130,130));
		search_from_label.setBounds(76, 35, 127, 15);
		add(search_from_label);

		JButton checkInfo_button = new JButton();
		checkInfo_button.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_checkInfo_default.png")));
		checkInfo_button.setContentAreaFilled(false);
		checkInfo_button.setBorder(null);
		checkInfo_button.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_checkInfo_rollover.png")));
		checkInfo_button.setBounds(302, 22, 25, 21);
		checkInfo_button.setVisible(false);
		add(checkInfo_button);

		JButton goChat_button = new JButton();
		goChat_button.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_goChat_default.png")));
		goChat_button.setContentAreaFilled(false);
		goChat_button.setBorder(null);
		goChat_button.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_goChat_rollover.png")));
		goChat_button.setBounds(337, 22, 23, 21);
		goChat_button.setVisible(false);
		add(goChat_button);


		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(242,242,242));
				checkInfo_button.setVisible(true);
				goChat_button.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//等鼠标移出才会隐藏
				if(e.getX()>=374 || e.getX()<=0 ||e.getY()>=65||e.getY()<=0) {
					setBackground(Color.white);
					checkInfo_button.setVisible(false);
					goChat_button.setVisible(false);
				}

			}

		});




		checkInfo_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("点了");
				PersonDataJFrame pdf = new PersonDataJFrame(user,false);
				pdf.setVisible(true);
			}

		});

		goChat_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				server.chat.get(user.getQq_id()).setVisible(true);
			}

		});

	}

}
