package tools;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class MyCheckBox extends JCheckBox{
  public MyCheckBox() {
    defaultSet();
  }

  public MyCheckBox(String src) {
    defaultSet();
    setText(src);
  }

  //默认设置
  private void defaultSet() {
    setIcon(new ImageIcon(this.getClass().getResource("/image/checkDefault.png")));
    setRolloverSelectedIcon(new ImageIcon(this.getClass().getResource("/image/checkRolloverSelected.png")));
    setRolloverIcon(new ImageIcon(this.getClass().getResource("/image/checkRollover.png")));
    setSelectedIcon(new ImageIcon(this.getClass().getResource("/image/checkSelected.png")));
    setFont(new Font("微软雅黑", Font.PLAIN, 15));

    setContentAreaFilled(false);
    setFocusPainted(false);
    setSelected(false);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        setPressedIcon(new ImageIcon(this.getClass().getResource(isSelected()?"/image/checkPressedSelected.png":"/image/checkPressed.png")));
      }
    });
  }
}
