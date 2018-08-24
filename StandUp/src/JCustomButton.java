import javax.swing.*;

public class JCustomButton extends JButton {

	JCustomButton() {
		init();
	}
	
	JCustomButton(String text) {
		super(text);
		init();
	}
	
	public void init() {
		super.setUI(new StyleButtonUI());
	}
	
	public void setEnabled(boolean b) {
		super.setEnabled(b);
	}
}
