import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * <code>JLabel</code> 컴포넌트를 이용해 스윙에서 간단하게 이미지를 추가할 수 있습니다.
 * 
 * @author Kim SeongJae
 */
@SuppressWarnings("serial")
public class JImageView2 extends JComponent {

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

	private String mPath;

	ImageIcon icon;
	Image image;

	JImageView2(String p) {
		setImage(p);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(image, 0, 0, width, height, this);
		g2.dispose();
		
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(width, height);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
	    
	    BufferedImage img = bilinearScaleOp.filter(bi, new BufferedImage(width, height, bi.getType()));
	    
		g.drawImage(img, 0, 0, width, height, this);

	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;

		if (autoScale) {
			this.width = iWidth;
			this.height = iHeight;
		}

		super.setBounds(x, y, width, height);
		// image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// repaint();
	}

	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		repaint();
	}

	public void setAutoScale(boolean b) {
		this.autoScale = b;
	}

	/**
	 * 표시 될 이미지를 정의합니다. 만약 이미지가 없다면 아무 것도 표시되지 않습니다.
	 * <p>
	 * 라이브러리 안에 있는 이미지 또는 그렇지 않은 이미지를 알아서 불러옵니다.
	 * <p>
	 * 
	 * @param path 이미지의 경로를 정의합니다.
	 */
	public void setImage(String p) {
		this.mPath = p;

		try {
			icon = new ImageIcon(getClass().getResource(p));
		} catch (Exception e) {
			icon = new ImageIcon(p);
		}

		image = icon.getImage();

		iWidth = image.getWidth(null);
		iHeight = image.getHeight(null);

		repaint();

	}
}
