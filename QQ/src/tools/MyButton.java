package tools;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MyButton extends JButton{
  private Color foregroundColor = new Color(166,166,166);

  public Color getForegroundColor() {
    return foregroundColor;
  }

  public void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
    setForeground(foregroundColor);
  }

  public MyButton() {
    DefultSet();
  }

  public MyButton(String str) {
    DefultSet();
    setText(str);
  }

  private void DefultSet() {
    setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    setContentAreaFilled(false);//是否绘制填充区
    setFocusPainted(false);
    setFont(new Font("微软雅黑", Font.PLAIN, 15));
    setForeground(foregroundColor);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        setForeground(new Color(38,189,246));
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        setForeground(new Color(131,131,131));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setForeground(foregroundColor);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
          setForeground(new Color(131,131,131));
        }
    });

  }
}
