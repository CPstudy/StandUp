import java.awt.*;
import javax.swing.*;

public class JImageView3 extends JComponent {
	
	private int xPos = 0;
	private int yPos = 0;

	/**
	 * ImageView의 크기
	 */
	private int width = 0;
	private int height = 0;

	/**
	 * 원본 이미지 크기
	 */
	private int iWidth = 0;
	private int iHeight = 0;

	private boolean autoScale = false;

	private String path;

	ImageIcon icon;
	Image image;
	
	JImageView3(String path) {
		this.path = path;
		
		try {
			icon = new ImageIcon(getClass().getResource(path));
		} catch (Exception e) {
			icon = new ImageIcon(path);
		}

		image = icon.getImage();

		iWidth = image.getWidth(null);
		iHeight = image.getHeight(null);
	}
	
	@Override
	public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.drawRect(10, 10, 50, 50);
        g.drawRect(50, 50, 50, 50);
        g.drawRect(90, 90, 50, 50);
        g.drawImage(image, 0, 0, 300, 300, this);
    }
}
