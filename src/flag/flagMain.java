package flag;

import javax.swing.*;
import java.awt.*;

public class flagMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Flag flag = new Flag();
		JFrame frame = new JFrame();
		frame.setSize(flag.width, flag.height);
		frame.setTitle("American Flag");
		frame.setMinimumSize(new Dimension(380, 222)); // 19*20, 10*20 + 22
														// (JFrame
														// has a 22 pixel offset
														// on the top)

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(flag);
		frame.setVisible(true);

		flag.setVisible(true);
	}

}
