package DrawBoard;

import Client.GroupChatBack;
import Client.GroupChatLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorChooser extends JFrame implements ChangeListener {
	JColorChooser colorChooser = new JColorChooser();
	// ColorSelectionModel model = colorChooser.getSelectionModel();
	static Color color = Color.black;
	static boolean colorChange;
	GroupChatLayout groupChatLayout;
	GroupChatBack groupChatBack;

	public void setGroupChatLayout(GroupChatLayout groupChatLayout) {
		this.groupChatLayout = groupChatLayout;
	}
	
	public ColorChooser() {
		setTitle("색상");
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		colorChooser.getSelectionModel().addChangeListener(this);

		add(colorChooser);
		
		pack();
		setVisible(true);
	}
	
	@Override
	public synchronized void stateChanged(ChangeEvent e) {
		color = colorChooser.getColor();
		groupChatLayout.getBrush().setColor(color);
		colorChange = true;
		groupChatBack = groupChatLayout.getGroupChatBack();
		groupChatBack.sendMessage("!ColorChanged" + color.getRGB());
	}
}