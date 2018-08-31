import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login extends JFrame implements ActionListener {

	JLabel lblID, lblPW;
	JTextField txtID;
	JTextField txtPW;
	JButton btnLogin;
	JPanel panelLogin;
	JImageView imgTitle;
	
	public Login() {
		setLayout(null);
		setTitle("ÀÏ¾î¼¸´Ù");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(453, 134);

		panelLogin = new JPanel();
		panelLogin.setLayout(null);
		panelLogin.setBounds(45, 350, 330, 150);
		add(panelLogin);

		imgTitle = new JImageView("img/swing_title.png");
		imgTitle.setBounds(0, 30, 454, 294);
		add(imgTitle);

		lblID = new JLabel("ID", JLabel.RIGHT);
		lblID.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		lblID.setBounds(0, 5, 60, 20);
		panelLogin.add(lblID);

		lblPW = new JLabel("IP", JLabel.RIGHT);
		lblPW.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		lblPW.setBounds(0, 40, 60, 20);
		panelLogin.add(lblPW);

		txtID = new JTextField(10);
		txtID.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		txtID.setBounds(70, 0, 150, 30);
		panelLogin.add(txtID);

		txtPW = new JTextField(10);
		txtPW.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		txtPW.setBounds(70, 35, 150, 30);
		txtPW.setText("127.0.0.1");
		panelLogin.add(txtPW);

		btnLogin = new JButton("ÀÔÀå");
		btnLogin.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		btnLogin.setBounds(230, 00, 100, 65);
		btnLogin.addActionListener(this);
		btnLogin.setBackground(new Color(65, 175, 57));
		btnLogin.setUI(new StyleButtonUI());
		btnLogin.setEnabled(true);
		panelLogin.add(btnLogin);

		setSize(460, 500);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = e.getActionCommand();
		switch (buttonText) {
		case "ÀÔÀå":
			if (!txtID.getText().equals("") && !txtPW.getText().equals("")) {
				if (txtID.getText().contains(" ")) {
					JOptionPane.showMessageDialog(this, "°ø¹éÀº »ç¿ëÇÒ ¼ö ¾ø½À´Ï´Ù.");
				} else {
					MainFrame frame = new MainFrame(txtID.getText(), txtPW.getText());
					setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(this, "ID¿Í IP ¸ðµÎ Àû¾îÁÖ¼¼¿ä.");
			}
			break;
		}
	}

	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Login login = new Login();
	}

}
