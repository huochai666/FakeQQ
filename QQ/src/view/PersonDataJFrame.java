package view;

import dao.impl.ServiceDapImpl;
import model.QQUser;
import tools.*;
import utils.MyTools;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/*
资料卡
 */
public class PersonDataJFrame extends JFrame {

	private MainBody mainBody;
	private JFrame jFrame;
	private JPanel contentPane;
	private QQUser user;
	private Boolean self;//资料卡是否是自己的
	private JButton editDataButton_button;
	private ServiceDapImpl server;
	private JLabel profile_label;
	private String imagePath;
	private PersonDataJFrame pdf;
	private JLabel bigNikeName_label;
	private JLabel signature_label;
	private JLabel nikename_label;
	private JLabel moreData_label;
	private JLabel profile_background_label;


	public PersonDataJFrame( QQUser user, Boolean self) {
		this.pdf = this;
		this.jFrame = this;
		this.user = user;
		this.self = self;
		ini();
		listener();
	}


	public PersonDataJFrame(ServiceDapImpl server, QQUser user, Boolean self,MainBody mainBody) {
		this.pdf = this;
		this.mainBody = mainBody;
		this.server = server;
		this.user = user;
		this.self = self;
		ini();
		listener();
	}

	void ini(){

		setUndecorated(true);
		setBounds(100, 100, 906, 656);
		setLocationRelativeTo(null);//设置jFrame居中显示
		setBackground(new Color(0,0,0,0));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel personDataPanelBackground_panel = new JPanel();
		personDataPanelBackground_panel.setOpaque(false);
		personDataPanelBackground_panel.setBounds(4, 4, 900, 650);
		contentPane.add(personDataPanelBackground_panel);
		personDataPanelBackground_panel.setLayout(null);



		MinimizeButton minimize_Button = new MinimizeButton();
		minimize_Button.setFrame(this);
		minimize_Button.setBounds(823, 0, 37, 40);
		minimize_Button.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/personDataMinimizeIcon.png")));
		minimize_Button.setRolloverIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/personDataMinimizeIcon.png")));
		personDataPanelBackground_panel.add(minimize_Button);


		CloseButton close_Button = new CloseButton(this);
		close_Button.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/personDataCloseIcon.png")));
		close_Button.setRolloverIcon(null);
		close_Button.setPressedIcon(null);
		close_Button.removeActionListener(close_Button.getActionListeners()[0]);
		close_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				close_Button.setContentAreaFilled(false);
				close_Button.setOpaque(false);
				setVisible(false);
			}

		});
		close_Button.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				close_Button.setContentAreaFilled(true);
				close_Button.setOpaque(true);
				close_Button.setBackground(new Color(224,74,50));
			}

		});
		close_Button.setRolloverIcon(null);
		close_Button.setBounds(860, 0, 40, 40);

		personDataPanelBackground_panel.add(close_Button);

		editDataButton_button = new JButton("");
		editDataButton_button.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/editDataButton.png")));
		editDataButton_button.setBounds(797, 44, 76, 26);
		editDataButton_button.setFocusPainted(false);
		editDataButton_button.setBorder(null);
		editDataButton_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editDataButton_button.setVisible(self);
		personDataPanelBackground_panel.add(editDataButton_button);

		moreData_label = new JLabel(user.getQq_sex()==0?"女":"男"+"   "+new SimpleDateFormat("MM月dd日(公历)").format(user.getQq_birthday()) +"  "+ MyTools.constellation(user.getQq_birthday()));
		moreData_label.setVerticalAlignment(SwingConstants.TOP);
		moreData_label.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		moreData_label.setBounds(513, 186, 294, 25);
		personDataPanelBackground_panel.add(moreData_label);

		nikename_label = new JLabel(user.getQq_nickname());
		nikename_label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		nikename_label.setBounds(599, 78, 252, 25);
		personDataPanelBackground_panel.add(nikename_label);

		JLabel id_label = new JLabel(user.getQq_id());
		id_label.setVerticalAlignment(SwingConstants.TOP);
		id_label.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		id_label.setBounds(513, 44, 159, 33);
		personDataPanelBackground_panel.add(id_label);

		signature_label = new JLabel(user.getQq_signature());
		signature_label.setForeground(Color.WHITE);
		signature_label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		signature_label.setBounds(163, 564, 252, 25);
		personDataPanelBackground_panel.add(signature_label);

		bigNikeName_label = new JLabel(user.getQq_nickname()==null?user.getQq_id():user.getQq_nickname());
		bigNikeName_label.setVerticalAlignment(SwingConstants.TOP);
		bigNikeName_label.setForeground(Color.WHITE);
		bigNikeName_label.setFont(new Font("微软雅黑", Font.PLAIN, 33));
		bigNikeName_label.setBounds(160, 514, 255, 54);
		personDataPanelBackground_panel.add(bigNikeName_label);

		profile_label = new JLabel();
		profile_label.setBounds(75, 512, 75, 75);
		if(self)
			profile_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		MyTools.loadProfile(user,profile_label);

		personDataPanelBackground_panel.add(profile_label);

		profile_background_label = new JLabel();
		profile_background_label.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/profile_background_default.png")));
		profile_background_label.setBounds(72,509,81,81);
		personDataPanelBackground_panel.add(profile_background_label);




		JLabel personDataPanelBackground_label = new JLabel();
		personDataPanelBackground_label.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/personDataPanelBackground.png")));
		personDataPanelBackground_label.setBounds(0, 0, 900, 650);
		personDataPanelBackground_panel.add(personDataPanelBackground_label);

		JLabel personDataPanelBackgroundShadow_label = new JLabel();
		personDataPanelBackgroundShadow_label.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/personDataPanelBackgroundShadow.png")));
		personDataPanelBackgroundShadow_label.setBounds(0, 0, 906, 656);
		contentPane.add(personDataPanelBackgroundShadow_label);

		System.out.println(profile_label.getIcon());
	}

	void updateValue(){
		bigNikeName_label.setText(user.getQq_nickname());
		nikename_label.setText(user.getQq_nickname());
		signature_label.setText(user.getQq_signature());
		moreData_label.setText((user.getQq_sex()==0?"女":"男"+"   "+new SimpleDateFormat("MM月dd日(公历)").format(user.getQq_birthday()) +"  "+ MyTools.constellation(user.getQq_birthday())));
	}

	void 	listener(){

		profile_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				profile_background_label.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/profile_background_rollover.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				profile_background_label.setIcon(new ImageIcon(PersonDataJFrame.class.getResource("/image/profile_background_default.png")));
			}

			@Override
			//双击显示高清头像
			public void mouseClicked(MouseEvent e) {

				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {
					try {
						Desktop.getDesktop().open(new File("C:\\miniQQHead\\"+user.getQq_id()+"_after.png"));
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				}

			}
		});

		editDataButton_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditDataJFrame edj = new EditDataJFrame(server,user,mainBody,pdf);
				edj.setVisible(true);
			}

		});

		if(self){

			profile_label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						//把文件选择器的样式设置成windows
						UIManager.setLookAndFeel(
								UIManager.getSystemLookAndFeelClassName());
					} catch (Exception error) {
						error.printStackTrace();
					}
					JFileChooser jfc = new JFileChooser("D://");
					FileNameExtensionFilter filter =
							new FileNameExtensionFilter("图片文件(*.jpg, *.jpeg, *.gif, *.png)",
									"jpg", "jpeg", "png", "gif");
					jfc.setFileFilter(filter);
					jfc.showOpenDialog(null);
					if (jfc.getSelectedFile() != null) {
						File file = jfc.getSelectedFile();
						if (file.isFile()) {
							imagePath = file.getAbsolutePath();
							MyTools.cut(file,user.getQq_id());//裁剪图片，裁剪后的图片在C:\miniQQHead\qq号.png

							byte[] bytes = MyTools.fileToByteArray("C:\\miniQQHead\\"+user.getQq_id()+".png");
							user.setQq_profile(bytes);
							server.addProfile(user);

							user = server.getPersonData(user.getQq_id());

							MyTools.byteArrayToFile(user.getQq_profile(),"C:\\miniQQHead\\"+user.getQq_id()+"_after.png");
							profile_label.setIcon(MyTools.getImage("C:\\miniQQHead\\"+user.getQq_id()+"_after.png",75,75));

							MyTools.getToast(jFrame,"头像更换成功");
							mainBody.updateValue();
						}
					}
				}

			});
		}
	}

}
