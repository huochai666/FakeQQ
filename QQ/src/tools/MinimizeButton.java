package tools;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class MinimizeButton extends JButton{
  JFrame frame ;
  private static int x;
  private static int y;

  public JFrame getFrame() {
    return frame;
  }

  public void setFrame(JFrame frame) {
    this.frame = frame;
    frame.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
      }
    });
    // 监听鼠标拖动时触发的事件（标题栏移动
    frame.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        int left = frame.getLocation().x;
        int top = frame.getLocation().y;
        frame.setLocation(left + e.getX() - x, top + e.getY() - y);
      }
    });
  }

  public MinimizeButton() {
    setIcon(new ImageIcon(this.getClass().getResource("/image/minimizeDefault_button.png")));
    setPressedIcon(new ImageIcon(this.getClass().getResource("/image/minimizePressedIcon_button.png")));
    setRolloverIcon(new ImageIcon(this.getClass().getResource("/image/minimizeRolloverIcon_button.png")));
    setContentAreaFilled(false);
    //是否填充区域，如果你的自定义图片不是矩形或存在空白边距，可以设为 false 使按钮看起来透明。
    setBorderPainted(false);
    //是否绘制边框
    setFocusPainted(false);
    //是否绘制焦点（例如浅色虚线框或者加粗的边框表明按钮当前有焦点）。
    addActionListener(new ActionListener(){
      @Override public void actionPerformed(ActionEvent e){
        frame.setExtendedState(JFrame.ICONIFIED);
      }
    });


  }
}

