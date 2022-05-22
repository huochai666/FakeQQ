package view;

import dao.impl.ServiceDapImpl;
import model.FriendTable;
import model.QQUser;
import tools.*;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class AddFriendJFrame extends JFrame {

	private ServiceDapImpl server;
	private JPanel contentPane;
	private JTextField searchFriend_text;
	private JButton addFriendBackground_searchButton;
	private JPanel addFriend_down_panel;
	private JPanel search_result_panel;
	private JButton backButton;
	private CloseButton close_Button;
	private JLabel search_result_label;
private QQUser mine;

	/**
	 * Create the frame.
	 */
	public AddFriendJFrame(ServiceDapImpl server,QQUser mine) {
		this.server = server;
		this.mine = mine;
		ini();
		listener();
	}

	void ini(){

		setBounds(300, 100, 1138, 763);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel addFriendBackground_Panel = new JPanel();
		addFriendBackground_Panel.setOpaque(false);
		addFriendBackground_Panel.setBounds(8, 8, 1122, 747);
		contentPane.add(addFriendBackground_Panel);
		addFriendBackground_Panel.setLayout(null);

		close_Button = new CloseButton(this);
		close_Button.setBounds(1082, 0, 40, 40);

		close_Button.removeActionListener(close_Button.getActionListeners()[0]);

		JCheckBox isOnline_checkbox = new JCheckBox("在  线");
		isOnline_checkbox.setSelected(true);
		isOnline_checkbox.setEnabled(false);
		isOnline_checkbox.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		isOnline_checkbox.setBounds(635, 107, 68, 23);
		isOnline_checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		isOnline_checkbox.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/isOnline_default.png")));
		isOnline_checkbox.setSelectedIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/isOnline_selected.png")));
		isOnline_checkbox.setFocusPainted(false);
		isOnline_checkbox.setOpaque(false);
		addFriendBackground_Panel.add(isOnline_checkbox);

		Hint searchFriend_text_hint = new Hint("请输入QQ号码/昵称");
		searchFriend_text_hint.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		searchFriend_text_hint.setBounds(34, 111, 187, 15);
		searchFriend_text_hint.setForeground(new Color(170,170,170));
		searchFriend_text_hint.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		addFriendBackground_Panel.add(searchFriend_text_hint);

		addFriendBackground_searchButton = new JButton();
		addFriendBackground_searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addFriendBackground_searchButton.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackground_searchButton_default.png")));
		addFriendBackground_searchButton.setRolloverIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackground_searchButton_rollove.png")));
		addFriendBackground_searchButton.setBorder(null);
		addFriendBackground_searchButton.setFocusPainted(false);
		addFriendBackground_searchButton.setBounds(764, 116, 146, 49);
		addFriendBackground_Panel.add(addFriendBackground_searchButton);

		searchFriend_text = new JTextField();
		searchFriend_text.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		searchFriend_text.setBounds(34, 106, 528, 24);
		searchFriend_text.setBorder(null);
		searchFriend_text.setOpaque(false);
		searchFriend_text.setSelectionColor(new Color(0, 120, 215));
		searchFriend_text.setSelectedTextColor(Color.WHITE);
		addFriendBackground_Panel.add(searchFriend_text);
		searchFriend_text.setColumns(10);
		addFriendBackground_Panel.add(close_Button);

		MinimizeButton minimize_Button = new MinimizeButton();
		minimize_Button.setFrame(this);
		minimize_Button.setBounds(1045, 0, 37, 40);
		addFriendBackground_Panel.add(minimize_Button);

		JLabel cut_off_rule_label = new JLabel("");
		cut_off_rule_label.setOpaque(true);
		cut_off_rule_label.setBounds(560, 39, 2, 15);
		cut_off_rule_label.setBackground(new Color(132,197,226));
		addFriendBackground_Panel.add(cut_off_rule_label);

		JButton addFriendBackground_lookForPerson_button = new JButton();
		addFriendBackground_lookForPerson_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addFriendBackground_lookForPerson_button.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackground_lookForPerson_selected.png")));
		addFriendBackground_lookForPerson_button.setBounds(439, 21, 103, 55);
		addFriendBackground_lookForPerson_button.setBorder(null);
		addFriendBackground_lookForPerson_button.setContentAreaFilled(false);
		addFriendBackground_lookForPerson_button.setFocusPainted(false);
		addFriendBackground_Panel.add(addFriendBackground_lookForPerson_button);

		JButton addFriendBackground_lookForGroup = new JButton();
		addFriendBackground_lookForGroup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addFriendBackground_lookForGroup.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackground_lookForGroup_default.png")));
		addFriendBackground_lookForGroup.setBorder(null);
		addFriendBackground_lookForGroup.setContentAreaFilled(false);
		addFriendBackground_lookForGroup.setFocusPainted(false);
		addFriendBackground_lookForGroup.setBounds(581, 21, 102, 55);
		addFriendBackground_Panel.add(addFriendBackground_lookForGroup);

		addFriend_down_panel = new JPanel();
		addFriend_down_panel.setBounds(0, 211, 1122, 536);
		addFriend_down_panel.setOpaque(false);
		addFriendBackground_Panel.add(addFriend_down_panel);
		addFriend_down_panel.setLayout(null);
		addFriend_down_panel.setVisible(false);

		JPanel addFriend_down_top_panel = new JPanel();
		addFriend_down_top_panel.setBackground(new Color(233,237,238));
		addFriend_down_top_panel.setBounds(0, 0, 1122, 42);
		addFriend_down_panel.add(addFriend_down_top_panel);
		addFriend_down_top_panel.setLayout(null);

		JLabel cutOffLine = new JLabel();
		cutOffLine.setOpaque(true);
		cutOffLine.setBackground(new Color(207,211,211));
		cutOffLine.setBounds(99, 4, 1, 33);
		addFriend_down_top_panel.add(cutOffLine);

		backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setContentAreaFilled(false);
		backButton.setBorder(null);
		backButton.setFocusPainted(false);
		backButton.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/backButton.png")));
		backButton.setBounds(22, 4, 63, 34);
		addFriend_down_top_panel.add(backButton);

		search_result_label = new JLabel("搜索：1");
		search_result_label.setForeground(new Color(104,104,104));
		search_result_label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		search_result_label.setBounds(118, 10, 471, 20);
		addFriend_down_top_panel.add(search_result_label);





		//搜索结果滚动条（将panel放在里面。JScrollPane本身算是一个容器）
		JScrollPane search_result_jsp = new JScrollPane();
		DemoScrollBarUI demo = new DemoScrollBarUI();
		search_result_jsp.getVerticalScrollBar().setUI(demo);

		search_result_panel = new JPanel();
		search_result_panel.setBackground(Color.WHITE);
		search_result_panel.setPreferredSize(new Dimension(355,600));//这边设置搜索框尺寸（影响到右边滚动条
		search_result_panel.setBorder(null);

		search_result_panel.setLayout(new FlowLayout(3,0,0));
		search_result_jsp.setBounds(0, 42, 1122, 494);
		search_result_jsp.setViewportView(search_result_panel);
		search_result_jsp.validate();
		search_result_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
		search_result_jsp.setBorder(null);
		search_result_jsp.setBackground(new Color(0,0,0,0));
		addFriend_down_panel.add(search_result_jsp);






		JLabel addFriendBackground_label = new JLabel();
		addFriendBackground_label.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackground.png")));
		addFriendBackground_label.setBounds(0, 0, 1122, 747);
		addFriendBackground_Panel.add(addFriendBackground_label);

		JLabel addFriendBackgroundShadow_label = new JLabel();
		addFriendBackgroundShadow_label.setIcon(new ImageIcon(AddFriendJFrame.class.getResource("/image/addFriendBackgroundShadow.png")));
		addFriendBackgroundShadow_label.setBounds(0, 0, 1138, 763);

		searchFriend_text_hint.regBandingText(searchFriend_text,null,"addFriendSearch");

		contentPane.add(addFriendBackgroundShadow_label);
	}

	public void listener() {
		//查找按钮被点击后，显示出下方的搜索结果
		addFriendBackground_searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!searchFriend_text.getText().equals("")){
					search_result_label.setText("搜索："+searchFriend_text.getText());
					int count = search_result_panel.getComponentCount();
					for(int i=0;i<count;i++) {
						search_result_panel.remove(0);
					}
					List<QQUser> list = server.fuzzySearch(searchFriend_text.getText());
					for(QQUser user:list){

						search_result_panel.add(new AddFriend_searchResult_panel(server,user,mine.getQq_id()));
					}
					search_result_panel.updateUI();
				}
				addFriend_down_panel.setVisible(true);

			}

		});
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addFriend_down_panel.setVisible(false);
			}

		});
		//关闭按钮被点击
		close_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
	}

/*
			for(int i=0;i<30;i++) {
			search_result_panel.add(new AddFriend_searchResult_panel());
		}
	 */

}
