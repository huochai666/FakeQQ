package tools;

import view.Login;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.*;

public class Hint extends JLabel{

  public Hint() {
    setDefault();
  }

  public Hint(String str) {
    setText(str);
    setDefault();
  }

  public Hint(String str,int x,int y,int width,int height) {
    setBounds(x, y, width, height);
    setText(str);
    setDefault();
  }

  private void setDefault() {
    //设置字体颜色，设置字体，设置默认鼠标（I式）
    setForeground(new Color(127,127,127));
    setFont(new Font("微软雅黑", Font.PLAIN, 11));
    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
  }

  //绑定文本框以及背景（失去焦点时更换背景图片）
  public void addbindingText(JTextField jText,JLabel background,String name) {
    jText.addFocusListener(new FocusListener() {

      @Override
      public void focusLost(FocusEvent e) {
        //失去焦点事件
        if(jText.getText().equals(""))
          setVisible(true);
        background.setIcon(new ImageIcon(Login.class.getResource("/image/"+name+"TextBackgroundDefault.png")));
      }

      @Override
      public void focusGained(FocusEvent e) {
        //获取焦点事件
        setVisible(false);
        background.setIcon(new ImageIcon(Login.class.getResource("/image/"+name+"TextBackgroundSelect.png")));
      }

    });
  }

  //注册用的hint（输入东西的时候消失
  public void regBandingText(JTextField jText,JLabel error,String type) {

    jText.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        if(type.equals("phone")){
          char keyCh = e.getKeyChar();
          if ((keyCh < '0') || (keyCh > '9')) {
            error.setText("手机号只能为数字");
            error.setVisible(true);
            if (keyCh != '\n') // 回车字符
              e.setKeyChar('\0');
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if(jText.getText().equals("")) {
          setVisible(true);
        }else {
          setVisible(false);
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if(jText.getText().equals("")) {
          setVisible(true);
        }else {
          setVisible(false);
        }
      }

    });
  }

}
