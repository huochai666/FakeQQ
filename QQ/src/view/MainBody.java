package view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import java.util.*;
import java.util.List;

import dao.impl.ServiceDapImpl;
import model.FriendTable;
import model.Message;
import model.QQUser;
import tools.CloseButton;
import tools.Demo3;
import tools.DemoScrollBarUI;
import tools.MinimizeButton;
import utils.MyTools;

public class MainBody extends JFrame {

  private ServiceDapImpl server;
  public String qq_id;//当前登录的qq号
  private QQUser mine;//个人资料
  private JPanel contentPane;
  private JTextField searchText;
  private JLabel searchBackground_label;
  private JButton search_closeButton;
  private MinimizeButton minimize_Button;
  private CardLayout card = new CardLayout();
  private JPanel center_panel;
  private JButton messageButton;
  private JButton friendListButton;
  private int state = 0;//判断当前center页面是在消息还是在联系人
  private JPanel friendListAndMessage_panel;
  private JLabel search_selected_friend;
  private JPanel search_result_panel;
  private JLabel search_selected_empty;
  private JButton addFriendButton;
  private JLabel loginSuccessLine_label;
  private JPanel mainPanel;
  private JLabel background_shadow;
  private JButton optionButton;
  private Set<String> friend;
  private JLabel profile_label;
  private MainBody mainBody;
  private JLabel nickname_label;
  private JLabel signature_label;

  public MainBody(ServiceDapImpl server, String qq_id) {
    this.server = server;
    this.qq_id = qq_id;
    this.mainBody = this;
    ini();
    initEffect();
    listener();
  }

  void ini(){

    mine = server.getPersonData(qq_id);

    System.out.println("登录成功的qq为："+qq_id);
    setBounds(1427, 94, 400, 910);
    setUndecorated(true);
    setBackground(new Color(0, 0, 0, 0));
    contentPane = new JPanel();
    contentPane.setBorder(null);
    contentPane.setOpaque(false);
    setContentPane(contentPane);
    contentPane.setLayout(null);

    loginSuccessLine_label = new JLabel();
    loginSuccessLine_label.setOpaque(true);
    loginSuccessLine_label.setBackground(new Color(130, 172, 200));
    loginSuccessLine_label.setBounds(0, 369, 2, 172);
    loginSuccessLine_label.setVisible(false);
    contentPane.add(loginSuccessLine_label);

    mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setBounds(-500, 11, 374, 889);
    mainPanel.setOpaque(false);
    contentPane.add(mainPanel);
    mainPanel.setLayout(null);

    search_closeButton = new JButton();
    search_closeButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_closeButton_default.png")));
    search_closeButton.setContentAreaFilled(false);
    search_closeButton.setBorder(null);
    search_closeButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_closeButton_rollover.png")));
    search_closeButton.setBounds(337, 143, 20, 20);
    search_closeButton.setVisible(false);
    mainPanel.add(search_closeButton);

    searchText = new JTextField();
    searchText.setFont(new Font("微软雅黑", Font.PLAIN, 17));
    searchText.setBounds(44, 142, 290, 23);
    searchText.setOpaque(false);
    searchText.setBorder(null);
    searchText.setSelectionColor(new Color(0, 120, 215));
    searchText.setSelectedTextColor(Color.WHITE);
    mainPanel.add(searchText);
    searchText.setColumns(10);

    signature_label = new JLabel(mine.getQq_signature()==null?"这个人很懒，什么也没留下":mine.getQq_signature());
    signature_label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    signature_label.setForeground(Color.WHITE);
    signature_label.setBounds(103, 93, 178, 15);
    mainPanel.add(signature_label);

    nickname_label = new JLabel(mine.getQq_nickname());
    nickname_label.setForeground(Color.WHITE);
    nickname_label.setFont(new Font("微软雅黑", Font.BOLD, 20));
    nickname_label.setBounds(101, 59, 144, 22);
    mainPanel.add(nickname_label);

    FriendTable friendTable = server.queryFriendList(qq_id);
    friend = friendTable.getFriendAccount_And_Note().keySet();

    JLabel loginStateIcon_label = new JLabel("\u6807");
    loginStateIcon_label.setBounds(69, 102, 16, 16);

    ImageIcon image = new ImageIcon(MainBody.class.getResource("/image/stateIcon.png"));
    Image img = image.getImage();
    img = img.getScaledInstance(16,16, Image.SCALE_DEFAULT);
    image.setImage(img);
    loginStateIcon_label.setIcon(image);

    mainPanel.add(loginStateIcon_label);


    profile_label = new JLabel();
    profile_label.setBounds(19, 52, 65, 65);
    profile_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    MyTools.loadProfile(mine,profile_label);
    mainPanel.add(profile_label);

    searchBackground_label = new JLabel();
    searchBackground_label.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search.png")));
    searchBackground_label.setBounds(0, 134, 374, 38);
    mainPanel.add(searchBackground_label);

    minimize_Button = new MinimizeButton();
    minimize_Button.setFrame(this);
    minimize_Button.setBounds(297, 0, 37, 40);
    mainPanel.add(minimize_Button);

    CloseButton close_Button = new CloseButton(this);
    close_Button.setBounds(334, 0, 40, 40);
    mainPanel.add(close_Button);

    JLabel top_background_label = new JLabel();
    top_background_label.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_top_background.png")));
    top_background_label.setBounds(0, 0, 374, 172);
    mainPanel.add(top_background_label);

    JPanel function_panel = new JPanel();
    function_panel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(240,240,240)));
    function_panel.setOpaque(false);
    function_panel.setBounds(0, 826, 374, 63);
    mainPanel.add(function_panel);
    function_panel.setLayout(null);

    optionButton = new JButton();
    optionButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_optionButton_default.png")));
    optionButton.setContentAreaFilled(false);
    optionButton.setBorder(null);
    optionButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_optionButton_rollover.png")));
    optionButton.setBounds(10, 1, 48, 62);
    function_panel.add(optionButton);

    addFriendButton = new JButton();
    addFriendButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_addFriendButton_default.png")));
    addFriendButton.setContentAreaFilled(false);
    addFriendButton.setBorder(null);
    addFriendButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_addFriendButton_rollover.png")));
    addFriendButton.setBounds(58, 1, 48, 62);
    function_panel.add(addFriendButton);

    center_panel = new JPanel();
    center_panel.setOpaque(false);
    center_panel.setBounds(0, 172, 374, 654);
    center_panel.setLayout(card);
    mainPanel.add(center_panel);

    JPanel friendList_panel = new JPanel();
    friendList_panel.setOpaque(false);
    center_panel.add(friendList_panel, "friendList");
    friendList_panel.setLayout(null);

    messageButton = new JButton();
    messageButton.setFocusable(false);
    messageButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_selected.png")));
    messageButton.setContentAreaFilled(false);
    messageButton.setBorder(null);
    messageButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_selected.png")));
    messageButton.setBounds(0, 0, 187, 50);
    friendList_panel.add(messageButton);

    friendListButton = new JButton();
    friendListButton.setFocusable(false);
    friendListButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_default.png")));
    friendListButton.setContentAreaFilled(false);
    friendListButton.setBorder(null);
    friendListButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_rollove.png")));
    friendListButton.setBounds(187, 0, 187, 50);
    friendList_panel.add(friendListButton);



    friendListAndMessage_panel = new JPanel();
    friendListAndMessage_panel.setBackground(Color.WHITE);
    friendListAndMessage_panel.setBounds(0, 54, 374, 600);
    friendList_panel.add(friendListAndMessage_panel);
    friendListAndMessage_panel.setBackground(Color.white);
    friendListAndMessage_panel.setSize(374*2,600);
    friendListAndMessage_panel.setLayout(null);


    //消息滚动条（将panel放在里面。JScrollPane本身算是一个容器）
    JScrollPane message_jsp = new JScrollPane();
    DemoScrollBarUI messageUI = new DemoScrollBarUI();
    message_jsp.getVerticalScrollBar().setUI(messageUI);
    message_jsp.setBounds(0, 0, 374, 600);

    JPanel message_panel = new JPanel();
    message_panel.setBounds(0, 0, 374, 600);
    message_panel.setBackground(Color.white);
    message_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    message_panel.setPreferredSize(new Dimension(355,75*11));

    message_jsp.setViewportView(message_panel);
    message_jsp.validate();
    message_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
    message_jsp.setBorder(null);
    message_jsp.setBackground(new Color(0,0,0,0));
    friendListAndMessage_panel.add(message_jsp);

    List<Message> listUnread = server.unreadMessage(qq_id);

    List<String> list = MyTools.howManyPerson(listUnread,qq_id);//消息聊天框,获取跟几个人聊天的消息

    for(String str:friend){
      server.getChat().put(str,new ChatJFrame(server.getPersonData(str),server,qq_id));
    }

    //消息加载这里
//    for(Message message:listUnread){
//      QQUser user = server.getPersonData(message.getSender());
//      MessagePanel messagePanel = new MessagePanel(0,message,user);
//      message_panel.add(messagePanel);
//    }
//
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());
//    message_panel.add(new MessagePanel());

    //消息滚动条（将panel放在里面。JScrollPane本身算是一个容器）
    JScrollPane friendList_jsp = new JScrollPane();
    friendList_jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    DemoScrollBarUI friendListUI = new DemoScrollBarUI();
    friendList_jsp.getVerticalScrollBar().setUI(friendListUI);
    friendList_jsp.setBounds(374, 0, 374, 600);


    Demo3 friendListShow_panel = new Demo3(server,qq_id,server.getChat());
    friendList_jsp.setViewportView(friendListShow_panel);
    friendList_jsp.validate();
    friendList_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
    friendList_jsp.setBorder(null);
    friendList_jsp.setBackground(new Color(0,0,0,0));
    friendListAndMessage_panel.add(friendList_jsp);




    //搜索结果滚动条（将panel放在里面。JScrollPane本身算是一个容器）
    JScrollPane search_result_jsp = new JScrollPane();
    DemoScrollBarUI demo = new DemoScrollBarUI();
    search_result_jsp.getVerticalScrollBar().setUI(demo);

    search_result_panel = new JPanel();
    search_result_panel.setBackground(Color.WHITE);
    search_result_panel.setPreferredSize(new Dimension(355,600));//这边设置搜索框尺寸（影响到右边滚动条
    search_result_panel.setBorder(null);

    search_result_panel.setLayout(new FlowLayout(1,0,0));
    search_result_jsp.setViewportView(search_result_panel);
    search_result_jsp.validate();
    search_result_jsp.getVerticalScrollBar().setUnitIncrement(20); //滚动条速度
    search_result_jsp.setBorder(null);
    search_result_jsp.setBackground(new Color(0,0,0,0));
    center_panel.add(search_result_jsp, "searchResult");
//		center_panel.add(search_result_panel, "searchResult");

    search_selected_empty = new JLabel();
    search_selected_empty.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_empty.png")));
    search_result_panel.add(search_selected_empty);

    search_selected_friend = new JLabel();
    search_selected_friend.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected_friend.png")));
//    search_result_panel.add(search_selected_friend);

//    SearchResultJPanel hh = new SearchResultJPanel();
//    search_result_panel.add(hh);
//
//    SearchResultJPanel hh1 = new SearchResultJPanel();
//    search_result_panel.add(hh1);
//
//    SearchResultJPanel hh2 = new SearchResultJPanel();
//    search_result_panel.add(hh2);
//
//    SearchResultJPanel hh3 = new SearchResultJPanel();
//    search_result_panel.add(hh3);
//
//    SearchResultJPanel hh4 = new SearchResultJPanel();
//    search_result_panel.add(hh4);
//
//    SearchResultJPanel hh5 = new SearchResultJPanel();
//    search_result_panel.add(hh5);



    background_shadow = new JLabel();
    background_shadow.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_background_shadow.png")));
    background_shadow.setBounds(-500, 0, 400, 910);
    contentPane.add(background_shadow);


  }

  void updateValue(){
    MyTools.loadProfile(mine,profile_label);
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    nickname_label.setText(mine.getQq_nickname()==null?mine.getQq_id():mine.getQq_nickname());
    signature_label.setText(mine.getQq_signature());
  }

  //登录成功动画
  public void initEffect() {

    Thread th = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        loginSuccessLine_label.setVisible(true);
        // 登录成功的动画，一条小线（出现
        int y = loginSuccessLine_label.getLocation().y;
        int height = loginSuccessLine_label.getSize().height;
        for (int i = 0; i < 358; i=i+2) {
          loginSuccessLine_label.setBounds(0, y - i,2, height + i * 2);
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
          }
        }
        loginSuccessLine_label.setVisible(false);
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          // TODO 自动生成的 catch 块
          e.printStackTrace();
        }

        //窗体出现
        for(int i = -500;i<13;i=i+2) {
          mainPanel.setLocation(i, 11);
          background_shadow.setLocation(i-13, 0);
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
          }
        }

      }

    });
    th.start();
  }

  public void listener() {

    profile_label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        PersonDataJFrame pdf = new PersonDataJFrame(server,mine,true,mainBody);
        pdf.setVisible(true);
      }
    });

    optionButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
//        System.out.println("11111");
//        //把我删除
//        Message message = new Message();
//        message.setSender(qq_id);
//        message.setAccepter("2730893411");
//        message.setMsg("收到了吗哈哈哈");
//        message.setIsRead(1);
//        message.setSendTime(null);
//        message.setType(0);
//        server.sendMessage(message);
//        System.out.println("要发送的数据包为："+message.toString());
      }

    });

    searchText.addFocusListener(new FocusListener() {

      @Override
      public void focusGained(FocusEvent e) {
        searching();
      }

      @Override
      public void focusLost(FocusEvent e) {

      }

    });

    search_closeButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        exitSearch();
      }

    });

    //消息按钮被点击
    messageButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if(state==1) {
          Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
              int x = friendListAndMessage_panel.getLocation().x;
              for(int i=1;i<=374;i=i+10) {
                friendListAndMessage_panel.setLocation(x+i, friendListAndMessage_panel.getLocation().y);
                try {
                  Thread.sleep(1);
                } catch (InterruptedException e) {
                  // TODO 自动生成的 catch 块
                  e.printStackTrace();
                }
              }

            }

          });
          th.start();
          messageButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_selected.png")));
          messageButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_selected.png")));
          friendListButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_default.png")));
          friendListButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_rollove.png")));
          state = 0;
        }
      }

    });

    friendListButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if(state==0) {
          Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
              int x = friendListAndMessage_panel.getLocation().x;
              for(int i=1;i<=374;i=i+10) {
                friendListAndMessage_panel.setLocation(x-i, friendListAndMessage_panel.getLocation().y);
                try {
                  Thread.sleep(1);
                } catch (InterruptedException e) {
                  // TODO 自动生成的 catch 块
                  e.printStackTrace();
                }
              }

            }

          });
          th.start();
          messageButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_default.png")));
          messageButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_messageButton_rollove.png")));
          friendListButton.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_selected.png")));
          friendListButton.setRolloverIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_friendList_friendListButton_selected.png")));
          state = 1;

        }
      }

    });

    //添加好友按钮被点击
    addFriendButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        AddFriendJFrame ha = new AddFriendJFrame(server,mine);
        ha.setVisible(true);
      }

    });

  }

  //用户点击搜索文本框后，进入搜索模式
  void searching() {
    searchBackground_label.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search_selected.png")));
    search_closeButton.setVisible(true);
    card.show(center_panel, "searchResult");

    //搜索框输入值的时候，检索是否有包含
    searchText.addKeyListener( new KeyAdapter() {

      @Override
      public void keyReleased(KeyEvent e) {
        //搜索框不为空的时候搜索
        if(!searchText.getText().equals("")){

          List<QQUser> users = server.fuzzySearch(searchText.getText());
          //删除之前的结果（保留第一个label，第一个是好友的图标）
          int count = search_result_panel.getComponentCount();
          for(int i=0;i<count;i++) {
            search_result_panel.remove(0);
          }
          search_result_panel.add(search_selected_friend);

          //获取好友列表（判断是好友的再放入panel中）

          for(String num:friend){
            for(QQUser user:users){
              if(num.equals(user.getQq_id())){
                SearchResultJPanel hh = new SearchResultJPanel(user,searchText.getText(),server);
                search_result_panel.add(hh);
              }
            }
            search_result_panel.updateUI();
          }
        }else {
          int count = search_result_panel.getComponentCount();
          for(int i=0;i<count;i++) {
            search_result_panel.remove(0);
          }
          search_result_panel.add(search_selected_empty);
          search_result_panel.updateUI();
        }
      }

    });
  }

  //用户点击右边叉叉，退出搜索模式
  void exitSearch() {
    searchBackground_label.setIcon(new ImageIcon(MainBody.class.getResource("/image/MainBody_search.png")));
    search_closeButton.setVisible(false);
    minimize_Button.requestFocus();
    searchText.setText("");
    card.show(center_panel, "friendList");
    int count = search_result_panel.getComponentCount();
    for(int i=0;i<count;i++) {
      search_result_panel.remove(0);
    }
    search_result_panel.add(search_selected_empty);
    search_result_panel.updateUI();
  }

}
