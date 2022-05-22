package view;

import dao.impl.ServiceDapImpl;
import model.FriendTable;
import model.QQUser;
import utils.MyTools;

import javax.swing.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class AddFriend_searchResult_panel extends JPanel {
	private QQUser user;
	private String myid;//主人的QQ号
	private ServiceDapImpl server;

	/**
	 * Create the panel.
	 */
	public AddFriend_searchResult_panel(ServiceDapImpl server,QQUser user,String myid) {
		this.server= server;
		this.user = user;
		this.myid = myid;
		ini();
	}
	void ini(){

		setPreferredSize(new Dimension(275,116));
		setLayout(null);
		setBackground(Color.white);

		JLabel profile_label = new JLabel();
		profile_label.setBounds(25, 23, 75, 75);
		profile_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profile_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PersonDataJFrame pdf = new PersonDataJFrame(user,false);
				pdf.setVisible(true);
			}
		});
		MyTools.loadProfile(user,profile_label);

		add(profile_label);

		JLabel nickname_label = new JLabel(user.getQq_nickname()==null?user.getQq_id():user.getQq_nickname());
		nickname_label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		nickname_label.setBounds(118, 23, 140, 17);
		add(nickname_label);

		JLabel ageAndconstellation_label = null;
		try {
			ageAndconstellation_label = new JLabel(MyTools.getAge(user.getQq_birthday()) +"岁 | "+MyTools.constellation(user.getQq_birthday()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ageAndconstellation_label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		ageAndconstellation_label.setForeground(new Color(110,110,110));
		ageAndconstellation_label.setBounds(118, 50, 125, 15);
		add(ageAndconstellation_label);

		JButton addFriendApplyButton = new JButton("");
		addFriendApplyButton.setBorder(null);
		addFriendApplyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addFriendApplyButton.setFocusable(false);
		addFriendApplyButton.setIcon(new ImageIcon(AddFriend_searchResult_panel.class.getResource("/image/addFriendApplyButton_default.png")));
		addFriendApplyButton.setRolloverIcon(new ImageIcon(AddFriend_searchResult_panel.class.getResource("/image/addFriendApplyButton_rollove.png")));
		addFriendApplyButton.setBounds(117, 73, 66, 26);
		addFriendApplyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FriendTable friendTable = server.queryFriendList(myid);
				Set<String> set = friendTable.getFriendAccount_And_Note().keySet();
				Boolean isFriend = false;
				for(String str:set){
					if(str.equals(user.getQq_id())){
						isFriend =true;
					}
				}
				if(isFriend){
					MyTools.getToast(new JFrame(),"你已经与对方是好友关系，请不要重复添加！");
				}else {
					if(myid.equals(user.getQq_id())){
						MyTools.getToast(new JFrame(),"你不能添加自己为好友！");
					}
					else {
						server.addFriend(myid,user.getQq_id());
						String str = user.getQq_nickname()==null?user.getQq_id():user.getQq_nickname();
						MyTools.getToast(new JFrame(),"添加用户："+str+" 成功！");
					}
				}




			}
		});
		add(addFriendApplyButton);

	}

}
