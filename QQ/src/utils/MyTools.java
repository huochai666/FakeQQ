package utils;

import model.Message;
import model.QQUser;
import tools.Toast;
import view.EditDataJFrame;
import view.Login;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MyTools {
  public enum Flag implements Serializable {
    Login,//登录
    Login_Result,//登录返回的结果
    Login_Success_inPhone,//使用手机登录成功
    Login_Success_inNumber,//使用QQ号登录成功
    Login_Failed,//登录失败

    Register,//注册
    Register_Result,//注册返回结果
    Register_Success,//注册成功
    Register_Failed_Phone_exists,//注册失败，手机号已存在
    Register_Failed,//注册失败

    UpdateUserData,//更新用户资料
    UpdateUserProfile,//更新用户头像

    GetNewPersonData,//获取最新个人资料
    QueryQQID,//根据手机号获取QQ
    QueryFriendList,//根据QQ号获取好友列表以及备注

    QueryUserFuzzySearch,//提供QQ号/昵称，模糊查找用户

    SendMessage,//发送消息
    ReceiveMessages,//接受消息

    CheckUnreadMessage,//根据QQ查看未读消息

    AddFriend,  //双向加好友

    QueryChatHistory,//根据QQ号查询与之有关的聊天信息
    QueryTwoPersonChat,//查询两个qq号之间的聊天记录

    Release;//告诉服务器释放Socket
  }

  /*
  复制消息文本进去可以看到跟哪些人进行过聊天
   */
  public static List<String> howManyPerson(List<Message> list,String qq_id){
    List<String> person = new ArrayList<>();
    for(Message message:list){
      if(message.getSender().equals(qq_id)){
        person.add(message.getAccepter());
      }else if(message.getAccepter().equals(qq_id)){
        person.add(message.getSender());
      }
    }
    person  = new ArrayList<String>(new HashSet<String>(person));//ArrayList去重复
    return person;
  }

  /**
   * 把文本设置到剪贴板（复制）
   */
  public static void setClipboardString(String text) {
    // 获取系统剪贴板
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    // 封装文本内容
    Transferable trans = new StringSelection(text);
    // 把文本内容设置到系统剪贴板
    clipboard.setContents(trans, null);
  }

  //获取Toast提示框
  public static void getToast(JFrame jFrame, String text){
    new Toast(jFrame, text, 3000, Toast.msg).start();
  }

  //获取提示框（登录时账号或密码为空
  public static void getJWindow(String str,int x,int y){
    Thread th = new Thread(new Runnable() {

      @Override
      public void run() {
        JWindow jw = new JWindow();
        JPanel contentPane = null;
        jw.setAlwaysOnTop(true);
        jw.setBounds(x, y, 165, 62);

        jw.setBackground(new Color(0,0,0,0));


        contentPane = new JPanel();
        contentPane.setBorder(null);
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
        jw.setContentPane(contentPane);

        JLabel textIsNullText_label = new JLabel(str);
        textIsNullText_label.setFont(new Font("宋体", Font.PLAIN, 14));
        textIsNullText_label.setBounds(12, 38, 146, 18);
        contentPane.add(textIsNullText_label);

        JLabel textIsNullBackground_label = new JLabel();
        textIsNullBackground_label.setIcon(new ImageIcon(Login.class.getResource("/image/textIsNull.png")));
        textIsNullBackground_label.setSize(166, 62);
        contentPane.add(textIsNullBackground_label, BorderLayout.CENTER);
        jw.setVisible(true);
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        jw.setVisible(false);
        //关闭JWindow
        jw.dispose();
      }

    });
    th.start();
  }

  /*
    根据日期返回生日
     */
  //将日期返回值设置为yyyy-MM-dd格式
  public static Date parse(String strDate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.parse(strDate);
  }

  //根据日期返回年龄　　int  age = getAge(parse("1990-09-27"));           //由出生日期获得年龄***
  public static int getAge(Date birthDay) throws Exception {
    Calendar cal = Calendar.getInstance();
    if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
      throw new IllegalArgumentException(
          "The birthDay is before Now.It's unbelievable!");
    }
    int yearNow = cal.get(Calendar.YEAR);  //当前年份
    int monthNow = cal.get(Calendar.MONTH);  //当前月份
    int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
    cal.setTime(birthDay==null?new Date():birthDay);
    int yearBirth = cal.get(Calendar.YEAR);
    int monthBirth = cal.get(Calendar.MONTH);
    int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
    int age = yearNow - yearBirth;   //计算整岁数
    if (monthNow <= monthBirth) {
      if (monthNow == monthBirth) {
        if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
      }else{
        age--;//当前月份在生日之前，年龄减一

      }
    }
    return age;
  }

  //根据日期返回星座
  public static String constellation(Date birthdayDate) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Mdd");
    String format = simpleDateFormat.format(birthdayDate==null?new Date():birthdayDate);
    int date = Integer.parseInt(format);
    if (date >= 121 && date <= 219) {
      return "水瓶座";
    } else if (date >= 220 && date <= 320) {
      return "双鱼座";
    } else if (date >= 321 && date <= 420) {
      return "白羊座";
    } else if (date >= 421 && date <= 521) {
      return "金牛座";
    } else if (date >= 522 && date <= 621) {
      return "双子座";
    } else if (date >= 622 && date <= 722) {
      return "巨蟹座";
    } else if (date >= 723 && date <= 823) {
      return "狮子座";
    } else if (date >= 824 && date <= 923) {
      return "处女座";
    } else if (date >= 924 && date <= 1023) {
      return "天秤座";
    } else if (date >= 1024 && date <= 1122) {
      return "天蝎座";
    } else if (date >= 1123 && date <= 1221) {
      return "射手座";
    } else {
      return "魔蝎座";
    }
  }

  //图片裁剪工具（输入路径和大小获得一个ImageIcon）
  public static ImageIcon getImage(String path,int width,int height) {
    ImageIcon image;
    image = new ImageIcon(path);
    Image img = image.getImage();
    img = img.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    image.setImage(img);
    return image;
  }

  public static void cut(File fileIn,String qqid){
    try {
      //图片的本地地址
      Image src = ImageIO.read(fileIn);
      BufferedImage url = (BufferedImage) src;
      //处理图片将其压缩成正方形的小图
      BufferedImage  convertImage= scaleByPercentage(url, 100,100);
      //裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）
      convertImage = convertCircular(url);
      //生成的图片位置
      String imagePath= "C:\\miniQQHead\\"+qqid+".png";
      File file =new File("C:\\miniQQHead");
      //如果文件夹不存在则创建
      if  (!file .exists()  && !file .isDirectory())
      {
        System.out.println("//不存在");
        file .mkdir();
      } else
      {
        System.out.println("//目录存在");
      }

      ImageIO.write(convertImage, imagePath.substring(imagePath.lastIndexOf(".") + 1), new File(imagePath));
      System.out.println("裁剪完成ok");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
   *
   * @param inputImage
   * @param
   *            ：压缩后宽度
   * @param
   *            ：压缩后高度
   * @throws java.io.IOException
   *             return
   */
  public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
    // 获取原始图像透明度类型
    int type = inputImage.getColorModel().getTransparency();
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    // 开启抗锯齿
    RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // 使用高质量压缩
    renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    BufferedImage img = new BufferedImage(newWidth, newHeight, type);
    Graphics2D graphics2d = img.createGraphics();
    graphics2d.setRenderingHints(renderingHints);
    graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
    graphics2d.dispose();
    return img;
  }

  /**
   * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
   *
   * @param
   *
   * @return
   * @throws IOException
   */
  public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {

//		BufferedImage bi1 = ImageIO.read(new File(url));

    // 这种是黑色底的
//		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_RGB);

    // 透明底的图片
    BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
    Graphics2D g2 = bi2.createGraphics();
    g2.setClip(shape);
    // 使用 setRenderingHint 设置抗锯齿
    g2.drawImage(bi1, 0, 0, null);
    // 设置颜色
    g2.setBackground(Color.green);
    g2.dispose();
    return bi2;
  }

  //读图片到字节数组
  public static byte[] fileToByteArray(String filePath) {
    // 1、创建源与目的地
    File src = new File(filePath);
    byte[] dest = null;
    // 2、选择流
    InputStream is = null;
    // 有新增方法不能发生多态
    ByteArrayOutputStream baos = null;
    try {
      is = new FileInputStream(src);
      baos = new ByteArrayOutputStream();
      // 3、操作(分段读取)
      byte[] flush = new byte[1024 * 10];// 缓冲容器
      int len = -1;// 接收长度
      try {
        while ((len = is.read(flush)) != -1) {
          // 写出到字节数组中
          baos.write(flush,0,len);
        }
        baos.flush();
        // 返回回来，上面调用时就有了
        return baos.toByteArray();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      // 4、释放资源
      try {
        if (null != is) {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();

      }
    }
    return null;
  }

  //吧头像载入到本地，头像要是过小则自动加载狗子
  public static void loadProfile(QQUser user,JLabel jLabel){
    if(user.getQq_profile()==null){
      byteArrayToFile(fileToByteArray("C:\\miniQQHead\\default.png"),"C:\\miniQQHead\\"+user.getQq_id()+"_after.png");
    }else if(user.getQq_profile().length<=3){
      byteArrayToFile(fileToByteArray("C:\\miniQQHead\\default.png"),"C:\\miniQQHead\\"+user.getQq_id()+"_after.png");
    }else {
      byteArrayToFile(user.getQq_profile(),"C:\\miniQQHead\\"+user.getQq_id()+"_after.png");
    }
    jLabel.setIcon(getImage("C:\\miniQQHead\\"+user.getQq_id()+"_after.png",jLabel.getWidth(),jLabel.getHeight()));
  }

  //把字节数组还原成图片
  public static void byteArrayToFile(byte[] src,String filePath) {
    // 1、创建源
    File dest = new File(filePath);
    // 2、选择流
    InputStream is = null;
    OutputStream os = null;
    try {
      is = new ByteArrayInputStream(src);
      os = new FileOutputStream(dest);
      // 3、操作(分段读取)
      byte[] flush = new byte[5];// 缓冲容器
      int len = -1;// 接收长度
      while ((len = is.read(flush)) != -1) {
        os.write(flush,0,len);
      }
      os.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 释放资源
      try {
        if (null != os) {
          os.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

}
