package tools;
import utils.ServerConnec;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class CloseButton extends JButton{
  public CloseButton(JFrame frame) {
    setIcon(new ImageIcon(this.getClass().getResource("/image/closeDefault_button.png")));
    setPressedIcon(new ImageIcon(this.getClass().getResource("/image/closePressedIcon_button.png")));
    setRolloverIcon(new ImageIcon(this.getClass().getResource("/image/closeRolloverIcon_button.png")));
    setContentAreaFilled(false);
    //是否填充区域，如果你的自定义图片不是矩形或存在空白边距，可以设为 false 使按钮看起来透明。
    setBorderPainted(false);
    //是否绘制边框
    setFocusPainted(false);
    //是否绘制焦点（例如浅色虚线框或者加粗的边框表明按钮当前有焦点）。

    //关闭的时候有个渐变
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        float i=1;
        int height = 0;
        while(i>0) {
          frame.setOpacity(i);
          frame.setSize(frame.getWidth(), frame.getHeight()-height);
          i=i-0.02f;
          height +=1*(1+i);
          try {
            Thread.sleep(5);
          } catch (InterruptedException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
          }
        }
        ServerConnec.release();
        System.exit(0);
      }
    });
  }
}
