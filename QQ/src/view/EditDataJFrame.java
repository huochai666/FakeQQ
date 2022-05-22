package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import dao.impl.ServiceDapImpl;
import model.QQUser;
import tools.*;
import utils.MyTools;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;


/*
编辑资料页面
 */


public class EditDataJFrame extends JFrame {
	private JFrame jFrame;
	private QQUser user;
	private JPanel contentPane;
	private JTextField EditNikeName_text;
	private JTextField EditSex_text;
	private JTextField EditBirthDay_year_text;
	private JTextField EditBirthDay_month_text;
	private JTextField EditBirthDay_day_text;
	private JTextArea EditSignature_text;
	private JButton SavePersonDataButton_button;
	private ServiceDapImpl server;
	private MainBody mainBody;
	private PersonDataJFrame pdf;


	public EditDataJFrame(ServiceDapImpl server,QQUser user,MainBody mainBody,PersonDataJFrame pdf) {
		this.pdf = pdf;
		this.mainBody = mainBody;
		this.jFrame = this;
		this.server = server;
		this.user = user;
		ini();
		listener();
	}



	void ini(){

		setUndecorated(true);
		setBounds(100, 100, 479, 317);
		setLocationRelativeTo(null);//设置jFrame居中显示
		setBackground(new Color(0,0,0,0));
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel EditDataJFrameBackground_panel = new JPanel();
		EditDataJFrameBackground_panel.setBounds(4, 4, 471, 309);
		contentPane.add(EditDataJFrameBackground_panel);
		EditDataJFrameBackground_panel.setLayout(null);

		EditSignature_text = new JTextArea(user.getQq_signature());
		EditSignature_text.setOpaque(false);
		EditSignature_text.setLineWrap(true);
		EditSignature_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditSignature_text.setColumns(10);
		EditSignature_text.setBorder(null);
		EditSignature_text.setBounds(97, 199, 346, 45);
		EditDataJFrameBackground_panel.add(EditSignature_text);

		EditBirthDay_year_text = new JTextField(new SimpleDateFormat("yyyy").format(user.getQq_birthday()==null?new Date(System.currentTimeMillis()):user.getQq_birthday()));
		EditBirthDay_year_text.setOpaque(false);
		EditBirthDay_year_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditBirthDay_year_text.setColumns(10);
		EditBirthDay_year_text.setBorder(null);
		EditBirthDay_year_text.setBounds(97, 149, 40, 21);
		EditDataJFrameBackground_panel.add(EditBirthDay_year_text);

		EditBirthDay_month_text = new JTextField(new SimpleDateFormat("MM").format(user.getQq_birthday()==null?new Date(System.currentTimeMillis()):user.getQq_birthday()));
		EditBirthDay_month_text.setHorizontalAlignment(SwingConstants.CENTER);
		EditBirthDay_month_text.setOpaque(false);
		EditBirthDay_month_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditBirthDay_month_text.setColumns(10);
		EditBirthDay_month_text.setBorder(null);
		EditBirthDay_month_text.setBounds(167, 149, 16, 21);
		EditDataJFrameBackground_panel.add(EditBirthDay_month_text);

		EditBirthDay_day_text = new JTextField(new SimpleDateFormat("dd").format(user.getQq_birthday()));
		EditBirthDay_day_text.setOpaque(false);
		EditBirthDay_day_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditBirthDay_day_text.setColumns(10);
		EditBirthDay_day_text.setBorder(null);
		EditBirthDay_day_text.setBounds(212, 149, 18, 21);
		EditDataJFrameBackground_panel.add(EditBirthDay_day_text);

		EditSex_text = new JTextField(user.getQq_sex()==0?"女":"男");
		EditSex_text.setOpaque(false);
		EditSex_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditSex_text.setColumns(10);
		EditSex_text.setBorder(null);
		EditSex_text.setBounds(335, 101, 108, 21);
		EditDataJFrameBackground_panel.add(EditSex_text);

		EditNikeName_text = new JTextField(user.getQq_nickname());
		EditNikeName_text.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		EditNikeName_text.setBounds(97, 101, 129, 21);
		EditNikeName_text.setBorder(null);
		EditNikeName_text.setOpaque(false);
		EditDataJFrameBackground_panel.add(EditNikeName_text);
		EditNikeName_text.setColumns(10);

		MinimizeButton minimize_Button = new MinimizeButton();
		minimize_Button.setFrame(this);
		minimize_Button.setBounds(399, 0, 40, 37);
		EditDataJFrameBackground_panel.add(minimize_Button);


		CloseButton close_Button = new CloseButton(this);
		close_Button.removeActionListener(close_Button.getActionListeners()[0]);
		close_Button.setBounds(439, 0, 32, 37);
		close_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		EditDataJFrameBackground_panel.add(close_Button);

		SavePersonDataButton_button = new JButton();
		SavePersonDataButton_button.setIcon(new ImageIcon(EditDataJFrame.class.getResource("/image/SavePersonDataButton.png")));
		SavePersonDataButton_button.setBounds(376, 274, 80, 30);
		EditDataJFrameBackground_panel.add(SavePersonDataButton_button);




		JLabel EditDataJFrameBackground_label = new JLabel();
		EditDataJFrameBackground_label.setIcon(new ImageIcon(EditDataJFrame.class.getResource("/image/EditDataJFrameBackground.png")));
		EditDataJFrameBackground_label.setBounds(0, 0, 471, 309);
		EditDataJFrameBackground_panel.add(EditDataJFrameBackground_label);

		JLabel EditDataJFrameBackgroundShadow_label = new JLabel();
		EditDataJFrameBackgroundShadow_label.setIcon(new ImageIcon(EditDataJFrame.class.getResource("/image/EditDataJFrameBackgroundShadow.png")));
		EditDataJFrameBackgroundShadow_label.setBounds(0, 0, 479, 317);
		contentPane.add(EditDataJFrameBackgroundShadow_label);
	}

	void listener(){
		SavePersonDataButton_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					user.setQq_birthday(new Date((new SimpleDateFormat("yyyy-MM-dd").parse(EditBirthDay_year_text.getText()+"-"+EditBirthDay_month_text.getText()+"-"+EditBirthDay_day_text.getText())).getTime()));
					user.setQq_sex(EditSex_text.getText().equals("男")?1:0);
					user.setQq_signature(EditSignature_text.getText());
					user.setQq_nickname(EditNikeName_text.getText());
					server.updateUser(user);
					setVisible(false);
					MyTools.getToast(jFrame,"资料更新成功！");
					mainBody.updateValue();
					pdf.updateValue();
				} catch (ParseException parseException) {
					parseException.printStackTrace();
				}
			}
		});
	}
}