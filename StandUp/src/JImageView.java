import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * <code>JLabel</code> ������Ʈ�� �̿��� �������� �����ϰ� �̹����� �߰��� �� �ֽ��ϴ�.
 * 
 * @author Kim SeongJae
 */
@SuppressWarnings("serial")
public class JImageView extends JLabel {

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

	JImageView(String p) {
		this.mPath = p;

		try {
			icon = new ImageIcon(getClass().getResource(p));
		} catch (Exception e) {
			icon = new ImageIcon(p);
		}

		image = icon.getImage();

		iWidth = image.getWidth(null);
		iHeight = image.getHeight(null);
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
		
		super.setIcon(getImage());
	}

	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		super.setIcon(getImage());
	}

	public void setAutoScale(boolean b) {
		this.autoScale = b;
		super.setIcon(getImage());
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
		
		super.setIcon(getImage());
	}

	private ImageIcon getImage() {

		if (autoScale) {
			this.width = iWidth;
			this.height = iHeight;
			super.setBounds(xPos, yPos, iWidth, iHeight);
		} else {
			super.setBounds(xPos, yPos, this.width, this.height);
		}

		return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));

	}
}
