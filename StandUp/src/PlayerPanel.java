import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class PlayerPanel extends JPanel {
	
	JImageView2 imgCard1, imgCard2;
	JImageView imgBGJokbo;
	JLabel2D txtJokbo;
	JLabel2D txtName;
	
	PlayerPanel() {
		setLayout(null);
		setOpaque(true);
		setBackground(new Color(0, 0, 0, 0));
		
		Font font = new Font("�ü�ü", Font.BOLD, 12);
		
		txtJokbo = new JLabel2D("���ȱ���", JLabel.CENTER);
		txtJokbo.setBounds(90, 92, 120, 18);
		txtJokbo.setFont(font);
		txtJokbo.setForeground(Color.white);
		txtJokbo.setOutlineColor(Color.black);
		txtJokbo.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		add(txtJokbo);
		
		txtName = new JLabel2D("", JLabel.CENTER);
		txtName.setBounds(10, 50, 120, 18);
		txtName.setFont(font);;
		txtName.setForeground(Color.white);
		txtName.setOutlineColor(Color.black);
		txtName.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		add(txtName);
		
		imgBGJokbo = new JImageView("img/bg_jokbo_text.png");
		imgBGJokbo.setBounds(90, 90, 120, 20);
		add(imgBGJokbo);
		
		imgCard1 = new JImageView2("img/back.png");
		imgCard1.setBounds(140, 10, 70, 108);
		add(imgCard1);
		
		imgCard2 = new JImageView2("img/back.png");
		imgCard2.setBounds(90, 10, 70, 108);
		add(imgCard2);
	}
	
	public void setCard(String path1, String path2) {
		imgCard1.setImage(path1);
		imgCard2.setImage(path2);
	}
	
	public void setCard1(String path) {
		imgCard1.setImage(path);
	}
	
	public void setCard2(String path) {
		imgCard2.setImage(path);
	}
	
	public void setName(String name) {
		txtName.setText(name);
	}
	
	public void setJokbo(String jokbo) {
		txtJokbo.setText(jokbo);
	}
}
