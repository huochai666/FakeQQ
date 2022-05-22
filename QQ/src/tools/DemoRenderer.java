package tools;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import view.*;

/**
 * 自定义CellRenderer
 * @author sf_dream
 * @date 2015年5月24日
 */
@SuppressWarnings("serial")
public class DemoRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//		// 根节点从0开始，依次往下
//		// 分组
//		if (node.getLevel() == 1) {
//			if (expanded) {
//				this.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_down.png")));
//			} else {
//				this.setIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow_left.png")));
//			}
//		}
//		// 好友
//		if (node.getLevel() == 2) {
//			this.setIcon(new ImageIcon(getClass().getClassLoader().getResource("qq_icon.png")));
//		}
//		this.setText(value.toString());
//		return this;

		// 大家这里注意，我为了节省时间，所以就没有写两个node类
		// 所有的代码写在了同一个类中，然后根据不同的节点来调用相应的方法
		DemoNode node = (DemoNode) value;
		if (node.getLevel() == 1) {
			// 是否展开
			if (expanded) {
				node.iconLabel.setIcon(new ImageIcon(Login.class.getResource("/image/arrow_down.png")));
			} else {
				node.iconLabel.setIcon(new ImageIcon(Login.class.getResource("/image/arrow_left.png")));
			}
			return node.getCateView();
		}
		if (node.getLevel() == 2) {
//			node.iconLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("qq_icon.png")));
			return node.getNodeView();
		}
		return this;
	}

}
