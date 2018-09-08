import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * <code>JLabel</code> ������Ʈ�� �̿��� �������� �����ϰ� �̹����� �߰��� �� �ֽ��ϴ�.
 * 
 * @author Kim SeongJae
 */
@SuppressWarnings("serial")
public class JImageView2 extends JComponent {

	private int xPos = 0;
	private int yPos = 0;

	/**
	 * ImageView�� ũ��
	 */
	private int width = 0;
	private int height = 0;

	/**
	 * ���� �̹��� ũ��
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
	 * ǥ�� �� �̹����� �����մϴ�. ���� �̹����� ���ٸ� �ƹ� �͵� ǥ�õ��� �ʽ��ϴ�.
	 * <p>
	 * ���̺귯�� �ȿ� �ִ� �̹��� �Ǵ� �׷��� ���� �̹����� �˾Ƽ� �ҷ��ɴϴ�.
	 * <p>
	 * 
	 * @param path �̹����� ��θ� �����մϴ�.
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
