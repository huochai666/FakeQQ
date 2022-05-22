package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.factory.ServiceFactory;
import dao.impl.ServiceDapImpl;
import tools.*;
import utils.MyTools;

import javax.swing.JLabel;

import java.awt.event.*;
import java.io.File;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Login  {

  ServiceDapImpl server = null;
  private JFrame jFrame;
  private JPanel contentPane;
  private JTextField idText;
  private JPasswordField passwordText;
  private JPanel background_panel;
  private JButton loginButton;
  private JLabel loginSuccessJLabel;
  private JPanel mainPanel;
  private JLabel profile_label;
  private JLabel loginStateIcon_label;
  private JButton register_button;
  private CardLayout loginCard;
  // 登录按钮移上去之后的动图特效
  ImageIcon loginRollover_Button = new ImageIcon(Login.class.getResource("/image/loginRollover_Button.gif"));// 登录按钮移动上去之后的渐变
  ImageIcon loginReleased_Button = new ImageIcon(Login.class.getResource("/image/loginReleased_Button.gif"));// 登录按钮点击之后的动效
  private JTextField register_phone_text;
  private JPasswordField register_password_text;
  private JLabel loginSuccessLine_label ;
  private JLabel background_label ;
  private MyButton registerIDButton;
  private MyButton returnLoginButton;
  private MyButton registerSuccessId_label;
  private JLabel registerErrorString_label;
  private JButton registerErrorBackLoginButton;
  private JButton loginErrorBackLoginButton;
  private JButton registerSuccessButton;
  private JLabel registerSuccessTitle_label;
  private JLabel register_phone_text_error;
  private JLabel register_password_text_error;
  private Hint register_phone_text_hint;
  private Hint register_password_text_hint;

  /**
   * Create the frame.
   */
  public Login() {
    init();

		initEffect();

    listener();
    //登录按钮绑定回车键
    loginButton.registerKeyboardAction(loginButton.getActionListeners()[0], KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  public void init() {
    jFrame = new JFrame();
    jFrame.setAlwaysOnTop(true);
    jFrame.getContentPane().show(false);
    loginCard = new CardLayout();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setSize(602, 571);
    int windowWidth = jFrame.getWidth(); // 获得窗口宽
    int windowHeight = jFrame.getHeight();// 获得窗口高
    Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
    Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
    int screenWidth = screenSize.width; // 获取屏幕的宽
    int screenHeight = screenSize.height; // 获取屏幕的高
    // 设置窗口居中
    jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
    jFrame.setUndecorated(true);
    jFrame.setBackground(new Color(0, 0, 0, 0));
    jFrame.getContentPane().setLayout(null);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    contentPane.setLayout(null);
    contentPane.setOpaque(false);
    jFrame.setContentPane(contentPane);

    background_panel = new JPanel();
    background_panel.setBackground(Color.BLACK);
    background_panel.setBounds(0, 0, 602, 571);
    background_panel.setLayout(null);
    background_panel.setOpaque(false);

    contentPane.add(background_panel, BorderLayout.CENTER);

    /*
     * 名字：mainPanel 作用：放置登录前、登录中、登录成功，的画面 注：采用卡片式布局loginCard
     */

    loginStateIcon_label = new JLabel();
    loginStateIcon_label.setIcon(new ImageIcon(Login.class.getResource("/image/stateIcon.png")));
    loginStateIcon_label.setBounds(320, 251, 23, 23);
    background_panel.add(loginStateIcon_label);

    loginSuccessLine_label = new JLabel();
    loginSuccessLine_label.setVerticalAlignment(SwingConstants.BOTTOM);
    loginSuccessLine_label.setBackground(new Color(130,172,200));
    loginSuccessLine_label.setBounds(600, 280, 2, 10);
    loginSuccessLine_label.setOpaque(true);
    loginSuccessLine_label.setVisible(false);
    background_panel.add(loginSuccessLine_label);

    profile_label = new JLabel();
    profile_label.setIcon(new ImageIcon(Login.class.getResource("/image/profileDefaultIcon.png")));
    profile_label.setBounds(261, 192, 79, 79);
    background_panel.add(profile_label);


    CloseButton close_Button = new CloseButton(jFrame);
    close_Button.setBounds(529, 80, 40, 40);
    background_panel.add(close_Button);

    MinimizeButton minimize_Button = new MinimizeButton();
    minimize_Button.setFrame(jFrame);
    minimize_Button.setBounds(492, 80, 37, 40);
    background_panel.add(minimize_Button);

    mainPanel = new JPanel();
    mainPanel.setBounds(33, 80, 536, 412);
    mainPanel.setOpaque(false);
    mainPanel.setLayout(loginCard);
    background_panel.add(mainPanel);

    JPanel loginMain_panel = new JPanel();
    loginMain_panel.setBackground(Color.WHITE);
    loginMain_panel.setBounds(33, 80, 536, 412);
    loginMain_panel.setOpaque(false);
    loginMain_panel.setLayout(null);
    mainPanel.add("login", loginMain_panel);

    // 点击登录按钮之后出现的panel
    JPanel loginIng_panel = new JPanel();
    loginIng_panel.setBackground(Color.WHITE);
    loginIng_panel.setBounds(33, 80, 536, 412);
    loginIng_panel.setOpaque(false);
    loginIng_panel.setLayout(null);
    mainPanel.add("loginIng", loginIng_panel);

    loginSuccessJLabel = new JLabel();
    // 图片默认对其方式设置为上对其
    loginSuccessJLabel.setVerticalAlignment(SwingConstants.TOP);
    loginSuccessJLabel.setIcon(new ImageIcon(Login.class.getResource("/image/logining.png")));
    loginSuccessJLabel.setSize(536, 157);
    loginSuccessJLabel.setLocation(0, 0);
    loginIng_panel.add(loginSuccessJLabel);

    loginButton = new JButton();

    loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/loginDefault_Button.png")));
    loginButton.setPressedIcon(new ImageIcon(Login.class.getResource("/image/loginPressed_Button.png")));
    loginButton.setRolloverIcon(loginRollover_Button);
    loginButton.setBounds(119, 340, 302, 49);
    loginButton.setBorder(null);

    loginMain_panel.add(loginButton);

    registerIDButton = new MyButton("注册账号");
    registerIDButton.setBounds(15, 382, 64, 17);
    loginMain_panel.add(registerIDButton);

    MyButton findBackPasswordButton = new MyButton("找回密码");
    findBackPasswordButton.setBounds(357, 309, 64, 17);
    loginMain_panel.add(findBackPasswordButton);

    MyCheckBox remPasswordCheckBox = new MyCheckBox("记住密码");
    remPasswordCheckBox.setBounds(239, 306, 109, 23);
    remPasswordCheckBox.setForeground(new Color(166, 166, 166));
    loginMain_panel.add(remPasswordCheckBox);

    MyCheckBox autoLoginCheckBox = new MyCheckBox("自动登录");
    autoLoginCheckBox.setBounds(122, 306, 109, 23);
    autoLoginCheckBox.setForeground(new Color(166, 166, 166));
    loginMain_panel.add(autoLoginCheckBox);

    passwordText = new JPasswordField();
    passwordText.setFont(new Font("微软雅黑", Font.BOLD, 35));
    passwordText.setBounds(144, 263, 221, 26);
    passwordText.setOpaque(false);// 设置文本框为透明
    passwordText.setSelectionColor(new Color(0, 120, 215));
    passwordText.setSelectedTextColor(Color.WHITE);
    passwordText.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));// 取消文本框边框
    loginMain_panel.add(passwordText);

    Hint passwordTextHint = new Hint("密码", 146, 268, 70, 18);
    passwordTextHint.setVerticalAlignment(SwingConstants.BOTTOM);
    passwordTextHint.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    loginMain_panel.add(passwordTextHint);

    Hint idTextHint = new Hint("QQ号码/手机/邮箱", 147, 217, 144, 18);
    idTextHint.setVerticalAlignment(SwingConstants.BOTTOM);
    idTextHint.setFont(new Font("微软雅黑", Font.PLAIN, 16));
    loginMain_panel.add(idTextHint);

    idText = new JTextField();
    idText.setForeground(Color.BLACK);
    idText.setFont(new Font("黑体", Font.PLAIN, 26));
    idText.setBounds(147, 211, 246, 30);
    idText.setSelectionColor(new Color(0, 120, 215));
    idText.setSelectedTextColor(Color.WHITE);
    idText.setOpaque(false);// 设置文本框为透明
    idText.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));// 取消文本框边框

    loginMain_panel.add(idText);
    idText.setColumns(10);

    JLabel passwordTextBackground = new JLabel("password");
    passwordTextBackground
        .setIcon(new ImageIcon(Login.class.getResource("/image/passwordTextBackgroundDefault.png")));
    passwordTextBackground.setBounds(121, 246, 300, 47);
    loginMain_panel.add(passwordTextBackground);

    JLabel idTextBackground_label = new JLabel("id");
    idTextBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/idTextBackgroundDefault.png")));
    idTextBackground_label.setBounds(121, 217, 300, 29);
    loginMain_panel.add(idTextBackground_label);

    JLabel loginMain_top_label = new JLabel();
    loginMain_top_label.setIcon(new ImageIcon(Login.class.getResource("/image/login_top.gif")));
    loginMain_top_label.setSize(536, 157);
    loginMain_top_label.setLocation(0, 0);
    loginMain_panel.add(loginMain_top_label);

    JLabel loginMain_down_label = new JLabel();
    loginMain_down_label.setBackground(Color.WHITE);
    loginMain_down_label.setSize(536, 255);
    loginMain_down_label.setLocation(0, 157);
    loginMain_panel.add(loginMain_down_label);

    background_label = new JLabel("");
    background_label.setIcon(new ImageIcon(Login.class.getResource("/image/background1.png")));
    background_label.setForeground(Color.RED);
    background_label.setFont(new Font("宋体", Font.PLAIN, 69));
    background_label.setBounds(0, 0, 602, 571);
    background_panel.add(background_label);

    idTextHint.addbindingText(idText, idTextBackground_label, "id");
    passwordTextHint.addbindingText(passwordText, passwordTextBackground, "password");

    JPanel register_panel = new JPanel();

    register_panel.setOpaque(false);
    mainPanel.add("register",register_panel);
    register_panel.setLayout(null);

    register_phone_text_hint = new Hint("手机号码");

    register_phone_text_hint.setForeground(new Color(170,170,170));
    register_phone_text_hint.setFont(new Font("微软雅黑", Font.PLAIN, 18));


    register_phone_text_hint.setBounds(115, 216, 90, 18);
    register_panel.add(register_phone_text_hint);

    JLabel register_label = new JLabel("· 欢迎注册");
    register_label.setForeground(Color.WHITE);
    register_label.setFont(new Font("微软雅黑", Font.PLAIN, 26));
    register_label.setBounds(98, 3, 150, 54);
    register_panel.add(register_label);

    JLabel registerJLabel = new JLabel();
    registerJLabel.setIcon(new ImageIcon(Login.class.getResource("/image/loginSuccess.png")));
    registerJLabel.setVerticalAlignment(SwingConstants.TOP);
    registerJLabel.setBounds(0, 0, 536, 157);
    register_panel.add(registerJLabel);

    register_phone_text = new JTextField();
    register_phone_text.setFont(new Font("微软雅黑", Font.PLAIN, 18));
    register_phone_text.setBounds(98, 204, 341, 44);
    register_panel.add(register_phone_text);
    register_phone_text.setColumns(10);
    register_phone_text.setOpaque(false);
    register_phone_text.setBorder(new RoundBorder(new Color(171,170,170)));
    register_phone_text.setSelectionColor(new Color(0, 120, 215));
    register_phone_text.setSelectedTextColor(Color.WHITE);


    register_password_text_hint = new Hint("输入密码");
    register_password_text_hint.setForeground(new Color(170, 170, 170));
    register_password_text_hint.setFont(new Font("微软雅黑", Font.PLAIN, 18));
    register_password_text_hint.setBounds(115, 286, 90, 18);
    register_panel.add(register_password_text_hint);

    register_password_text = new JPasswordField(20);
    register_password_text.setOpaque(false);
    register_password_text.setFont(new Font("微软雅黑", Font.PLAIN, 24));
    register_password_text.setColumns(10);
    register_password_text.setBorder(new RoundBorder(new Color(171,170,170)));
    register_password_text.setBounds(98, 273, 341, 44);
    register_password_text.setSelectionColor(new Color(0, 120, 215));
    register_password_text.setSelectedTextColor(Color.WHITE);
    register_panel.add(register_password_text);

    register_button = new JButton("立即注册");
    register_button.setFont(new Font("微软雅黑", Font.BOLD, 18));
    register_button.setForeground(Color.white);
    register_button.setBounds(98, 340, 341, 44);
    register_button.setBackground(Color.black);
    register_button.setFocusPainted(false);
    register_button.setBorder(new RoundBorder(new Color(0,174,255)));
    register_button.setBackground(new Color(50,133,255));

    register_panel.add(register_button);

    returnLoginButton = new MyButton("已有账号");
    returnLoginButton.setBounds(15, 382, 64, 17);
    register_panel.add(returnLoginButton);

    register_phone_text_error = new JLabel("手机号错误");
    register_phone_text_error.setVisible(false);
    register_phone_text_error.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    register_phone_text_error.setForeground(new Color(255,91,91));
    register_phone_text_error.setBounds(117, 244, 322, 24);
    register_panel.add(register_phone_text_error);

    register_phone_text_hint.regBandingText(register_phone_text,register_phone_text_error,"phone");

    register_password_text_error = new JLabel("密码格式错误");
    register_password_text_error.setVisible(false);
    register_password_text_error.setForeground(new Color(255, 91, 91));
    register_password_text_error.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    register_password_text_error.setBounds(117, 313, 322, 24);
    register_panel.add(register_password_text_error);

    register_password_text_hint.regBandingText(register_password_text,register_password_text_error,"password");


    //注册失败页面：
    JPanel registerError_Panel = new JPanel();
    registerError_Panel.setLayout(null);
    mainPanel.add(registerError_Panel, "registerError");

    registerErrorString_label = new JLabel("注册失败！手机号已存在！");
    registerErrorString_label.setVerticalAlignment(SwingConstants.TOP);
    registerErrorString_label.setFont(new Font("微软雅黑", Font.PLAIN, 22));
    registerErrorString_label.setBounds(106, 98, 390, 227);
    registerError_Panel.add(registerErrorString_label);

    registerErrorBackLoginButton = new JButton("");
    registerErrorBackLoginButton.setIcon(new ImageIcon(Login.class.getResource("/image/cancelButton_Default.png")));
    registerErrorBackLoginButton.setBorder(null);
    registerErrorBackLoginButton.setBounds(419, 375, 103, 30);
    registerError_Panel.add(registerErrorBackLoginButton);

    JLabel registerErrorBackground_label = new JLabel();
    registerErrorBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/ErrorBackgroundPanel.png")));
    registerErrorBackground_label.setVerticalAlignment(SwingConstants.TOP);
    registerErrorBackground_label.setBounds(0, 0, 536, 412);
    registerError_Panel.add(registerErrorBackground_label);

    //登录失败页面：

    JPanel loginError_Panel = new JPanel();
    mainPanel.add(loginError_Panel, "loginError");
    loginError_Panel.setLayout(null);

    JLabel loginErrorString_label = new JLabel("您输入的账号或密码不正确，请重试");
    loginErrorString_label.setVerticalAlignment(SwingConstants.TOP);
    loginErrorString_label.setFont(new Font("微软雅黑", Font.PLAIN, 22));
    loginErrorString_label.setBounds(106, 98, 390, 227);
    loginError_Panel.add(loginErrorString_label);

    loginErrorBackLoginButton = new JButton("");
    loginErrorBackLoginButton.setIcon(new ImageIcon(Login.class.getResource("/image/cancelButton_Default.png")));
    loginErrorBackLoginButton.setRolloverIcon(new ImageIcon(Login.class.getResource("/image/cancelButton_Rollover.png")));
    loginErrorBackLoginButton.setBorder(null);
    loginErrorBackLoginButton.setBounds(419, 375, 103, 30);

    loginError_Panel.add(loginErrorBackLoginButton);

    JLabel loginErrorBackground_label = new JLabel();
    loginErrorBackground_label.setVerticalAlignment(SwingConstants.TOP);
    loginErrorBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/ErrorBackgroundPanel.png")));
    loginErrorBackground_label.setBounds(0, 0, 536, 412);
    loginError_Panel.add(loginErrorBackground_label);

    //注册成功页面：
    JPanel registerSuccess_panel = new JPanel();
    registerSuccess_panel.setOpaque(false);
    mainPanel.add(registerSuccess_panel, "registerSuccess");
    registerSuccess_panel.setLayout(null);

    JLabel registerSuccess_label = new JLabel();
    registerSuccess_label.setIcon(new ImageIcon(Login.class.getResource("/image/registerSuccessIcon.png")));
    registerSuccess_label.setBounds(235, 87, 65, 65);
    registerSuccess_panel.add(registerSuccess_label);

    JLabel registerSuccessTop_label = new JLabel();
    registerSuccessTop_label.setOpaque(false);
    registerSuccessTop_label.setIcon(new ImageIcon(Login.class.getResource("/image/registerSuccessTop_label.png")));
    registerSuccessTop_label.setBounds(0, 0, 536, 41);
    registerSuccess_panel.add(registerSuccessTop_label);

    JLabel registerSuccessGonxi_label = new JLabel("注册成功");
    registerSuccessGonxi_label.setHorizontalAlignment(SwingConstants.CENTER);
    registerSuccessGonxi_label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
    registerSuccessGonxi_label.setBounds(182, 162, 172, 41);
    registerSuccess_panel.add(registerSuccessGonxi_label);

    registerSuccessId_label = new MyButton("103778222");
    registerSuccessId_label.setHorizontalAlignment(SwingConstants.CENTER);
    registerSuccessId_label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
    registerSuccessId_label.setBounds(133, 222, 272, 41);
    registerSuccessId_label.setForegroundColor(Color.black);
    registerSuccess_panel.add(registerSuccessId_label);

    registerSuccessTitle_label = new JLabel("您可以将手机null作为辅助账号登录QQ");
    registerSuccessTitle_label.setHorizontalAlignment(SwingConstants.CENTER);
    registerSuccessTitle_label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
    registerSuccessTitle_label.setForeground(new Color(162,153,156));
    registerSuccessTitle_label.setBounds(78, 273, 393, 25);
    registerSuccess_panel.add(registerSuccessTitle_label);

    registerSuccessButton = new JButton("");
    registerSuccessButton.setIcon(new ImageIcon(Login.class.getResource("/image/registerSuccessButton_default.png")));
    registerSuccessButton.setRolloverIcon(new ImageIcon(Login.class.getResource("/image/registerSuccessButton_Pressed.png")));
    registerSuccessButton.setBorder(null);
    registerSuccessButton.setBounds(95, 320, 346, 54);
    registerSuccess_panel.add(registerSuccessButton);

    background_panel.add(loginSuccessLine_label);
    server = ServiceFactory.newInstance();
    close_Button.requestFocus();
    //获取服务器，用于执行服务器各种方法

  }

  public void initEffect()  {
    int height = background_panel.getHeight();
    background_panel.setSize(background_panel.getWidth(), 1);
    jFrame.setVisible(true);
    for (int i = 0; i < height; i = i + 10) {
      background_panel.setSize(602, i);
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /*
    public void initEffect()  {
    int height = jFrame.getHeight();
    jFrame.setSize(jFrame.getWidth(), 1);
    jFrame.setVisible(true);
    for (int i = 0; i < height; i = i + 10) {
      jFrame.setSize(602, i);
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
   */

  public void listener() {

    //读取头像
    idText.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        File file = new File("C:\\miniQQHead\\"+idText.getText()+"_after.png");
        if(file.exists()){
          profile_label.setIcon(MyTools.getImage("C:\\miniQQHead\\"+idText.getText()+"_after.png",profile_label.getWidth(),profile_label.getHeight()));
        }
      }
    });

    // 控制移动上去的时候特效和点击特效
    loginButton.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseEntered(MouseEvent e) {
        loginButton.setRolloverIcon(loginRollover_Button);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        loginRollover_Button.getImage().flush();
      }

      @Override
      public void mousePressed(MouseEvent e) {
        loginButton.setPressedIcon(new ImageIcon(Login.class.getResource("/image/loginPressed_Button.png")));
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        loginButton.setRolloverIcon(loginReleased_Button);
        loginReleased_Button.getImage().flush();
      }

    });

    loginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        if(idText.getText().equals("")){
          idText.requestFocus();
          MyTools.getJWindow("请你输入账号后再登录", jFrame.getX()+142,jFrame.getY()+271);
        }else if(new String(passwordText.getPassword()).equals("")){
          passwordText.requestFocus();
          MyTools.getJWindow("请你输入密码后再登录",jFrame.getX()+144,jFrame.getY()+321);
        }else {
          System.out.println(idText.getText()+"密码："+new String(passwordText.getPassword()));
          loginIngEffects();
          Thread aa = new Thread(new Runnable() {

            @Override
            public void run() {
              try {
                Thread.sleep(600);
              } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
              }
              System.out.println("判断登录");

              MyTools.Flag loginFlag = server.doLogin(idText.getText(), String.valueOf(passwordText.getPassword()));
              System.out.println("判断结束"+loginFlag);
              if(loginFlag == MyTools.Flag.Login_Success_inNumber || loginFlag == MyTools.Flag.Login_Success_inPhone){
                //调用登录成功的特效
                loginSuccessEffects();
                System.out.println("登录成功！"+loginFlag);
                MainBody logged = null;
                if(loginFlag == MyTools.Flag.Login_Success_inPhone){
                  logged = new MainBody(server,server.queryQQID(idText.getText()));
                }else if (loginFlag == MyTools.Flag.Login_Success_inNumber) {
                  logged = new MainBody(server,idText.getText());
                }
                logged.setVisible(true);

              } else {
                profile_label.setVisible(false);
                loginStateIcon_label.setVisible(false);
                loginCard.show(mainPanel,"loginError");
              }
            }

          });
          aa.start();
        }

      }

    });

    registerSuccessButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        profile_label.setVisible(true);
        loginStateIcon_label.setVisible(true);
        loginCard.show(mainPanel,"login");
      }

    });

    registerErrorBackLoginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        profile_label.setVisible(true);
        loginStateIcon_label.setVisible(true);
        loginCard.show(mainPanel,"register");
      }

    });

    loginErrorBackLoginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        profile_label.setVisible(true);
        loginStateIcon_label.setVisible(true);
        profile_label.setLocation(261,192);
        loginStateIcon_label.setLocation(320,254);
        loginCard.show(mainPanel,"login");
      }

    });

    //注册按钮被点击的时候，判断是否成功注册
    register_button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        //Window parent, String message, int period, int type
//        new Toast(jFrame, "验证码已发送", 3000, Toast.msg).start();
        if(register_phone_text.getText().length()!=11){
          register_phone_text_error.setText("手机号格式不正确，长度需为11位");
          register_phone_text_error.setVisible(true);
        }else{
          if(register_password_text.getPassword().length<6){
            register_password_text_error.setText("密码不得少于六位");
            register_password_text_error.setVisible(true);
          }else {
            //返回值不为空时，表示注册成功
            Object num = server.doRegister(register_phone_text.getText(), String.valueOf(register_password_text.getPassword()));
            System.out.println(num);
            if(num instanceof String){
              System.out.println("注册成功！qq号："+num);
              profile_label.setVisible(false);
              loginStateIcon_label.setVisible(false);
              registerSuccessId_label.setText((String) num);
              registerSuccessTitle_label.setText("您可以将手机"+register_phone_text.getText()+"作为辅助账号登录QQ");

              loginCard.show(mainPanel,"registerSuccess");
            }else if(num instanceof MyTools.Flag){
              if(num == MyTools.Flag.Register_Failed_Phone_exists){
                System.out.println("注册失败！手机号已存在");
                registerErrorString_label.setText("注册失败！手机号已存在");
                profile_label.setVisible(false);
                loginStateIcon_label.setVisible(false);
                loginCard.show(mainPanel,"registerError");
              }
              if(num == MyTools.Flag.Register_Failed){
                System.out.println("注册失败！数据库添加失败");
                registerErrorString_label.setText("注册失败！数据库添加失败");
                profile_label.setVisible(false);
                loginStateIcon_label.setVisible(false);
                loginCard.show(mainPanel,"registerError");
              }
            }
          }
        }


      }

    });

    registerSuccessId_label.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        MyTools.setClipboardString(registerSuccessId_label.getText());
        MyTools.getToast(jFrame,"QQ号："+registerSuccessId_label.getText()+"已复制到剪贴板");
      }

    });

    registerIDButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        register_phone_text.setText("");
        register_password_text.setText("");
        register_phone_text_hint.setVisible(true);
        register_password_text_hint.setVisible(true);
        register_phone_text_error.setVisible(false);
        register_password_text_error.setVisible(false);
        loginCard.show(mainPanel,"register");
      }

    });

    returnLoginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        loginCard.show(mainPanel,"login");
      }

    });
  }

  public void loginSuccessEffects(){
    loginCard.show(mainPanel,"loginIng");
    Thread th = new Thread(new Runnable() {

      @Override
      public void run() {
        loginSuccessJLabel.setIcon(new ImageIcon(Login.class.getResource("/image/loginSuccess.png")));
        loginSuccessLine_label.setVisible(true);
        //登录成功的动画，一条小线（出现
        int y = loginSuccessLine_label.getLocation().y;
        for(int i = 1;i<130;i++) {
          loginSuccessLine_label.setBounds(loginSuccessLine_label.getLocation().x, y-i, loginSuccessLine_label.getSize().width, i*2);
          try {
            Thread.sleep(2);
          } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
          }
        }
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          // TODO 自动生成的 catch 块
          e.printStackTrace();
        }

        //窗体的移动
        int defaultSize = mainPanel.getLocation().x;
        int defaultBackground_labelX = background_label.getLocation().x;
        int defaultProfile1 = profile_label.getLocation().x;
        int defaultProfile2 = loginStateIcon_label.getLocation().x;
        for(int i=mainPanel.getLocation().x;i<=background_panel.getSize().width+20;i=i+5) {
          mainPanel.setLocation(i, mainPanel.getLocation().y);
          background_label.setLocation(defaultBackground_labelX+i-defaultSize, background_label.getLocation().y);
          profile_label.setLocation(defaultProfile1+i-defaultSize, profile_label.getLocation().y);
          loginStateIcon_label.setLocation(defaultProfile2+i-defaultSize, loginStateIcon_label.getLocation().y);
          try {
            Thread.sleep(2);
          } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
          }
        }

        for(int i = 130;i>1;i=i-2) {
          loginSuccessLine_label.setBounds(loginSuccessLine_label.getLocation().x, y-i, loginSuccessLine_label.getSize().width, i*2);
          try {
            Thread.sleep(2);
          } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
          }
        }
        loginSuccessLine_label.setVisible(false);
        jFrame.setVisible(false);



      }

    });

    th.start();
  }

  public void loginIngEffects(){
    loginCard.show(mainPanel,"loginIng");
    Thread th = new Thread(new Runnable() {

      @Override
      public void run() {
        profile_label.setLocation(profile_label.getLocation().x, profile_label.getLocation().y + 14);
        loginStateIcon_label.setLocation(loginStateIcon_label.getLocation().x, loginStateIcon_label.getLocation().y + 15);
        for (int i = 157; i <= 450; i = i + 10) {
          loginSuccessJLabel.setSize(loginSuccessJLabel.getWidth(), i);
          try {
            Thread.sleep(5);
          } catch (InterruptedException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
          }
        }
      }

    });
    th.start();
  }

}

//将文件处理成圆形
//    String filepath = new File("").getCanonicalPath()+"\\src\\image\\";//image文件夹下的东西
//    System.out.println(filepath);
//    ImageUtil.resizeImg(filepath+"5.png", filepath+"2-256-256.png", 79, 79, 1.0f); //filepath下的5.png文件
//    ImageUtil.makeRoundedCornerImg(filepath+"2-256-256.png", filepath+"2-256-256-360.png", 360);//输出成圆形（保存到第一个逗号的前面）
