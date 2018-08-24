import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame implements ActionListener {

	public static final int WIDTH = 860;
	public static final int HEIGHT = 530;
	public static final int FRAME_MARGIN = 6;
	public static final int CARD_WIDTH = 100;
	public static final int CARD_HEIGHT = 156;
	public static final int CARD_MARGIN = 10;

	JFrame frame;

	String ip;
	static final int port = 9999;

	Socket socket = null; // 통신용 소켓

	PrintWriter out; // 데이터 전송용
	BufferedReader in; // 데이터 수신용

	String sendData; // 전송 데이터
	String receiveData; // 수신 데이터

	String userID; // ID(대화명)
	String playerID; // 상대방 ID

	int turn = 0;
	int lose = 0;
	int betting = 0;
	int total = 0;

	ClientReceiveThread rcvThread;
	boolean endFlag = false;

	Jokbo jokbo;
	
	PlayerPanel pPanel1, pPanel2, pPanel3, pPanel4;

	JImageView imgBG, imgCursor, imgBGJokbo;
	JLabel lblTitle;
	JImageView imgCard1, imgCard2, imgCard3, imgCard4;
	JLabel2D lblPlayer1;
	JLabel2D lblPlayer2;
	JLabel2D lblTotal;
	JLabel2D lblBetting;
	JLabel2D lblJokbo1;
	JLabel2D lblJokbo2;
	JLabel2D lblMoney1;
	JLabel2D lblMoney2;
	JPanel panelMenu;
	JButton btnCall, btnDie, btnPlus, btnExit;
	JTextField txtPlus;
	JPanel panelTitle;

	int posX = 0, posY = 0;

	String[] cardSet = { "img/card1_1.png", "img/card1_2.png", "img/card2_1.png", "img/card2_2.png", "img/card3_1.png",
			"img/card3_2.png", "img/card4_1.png", "img/card4_2.png", "img/card5_1.png", "img/card5_2.png",
			"img/card6_1.png", "img/card6_2.png", "img/card7_1.png", "img/card7_2.png", "img/card8_1.png",
			"img/card8_2.png", "img/card9_1.png", "img/card9_2.png", "img/card10_1.png", "img/card10_2.png" };

	int[] cards = new int[4];
	int cardType = 0;
	int scoreUser = 0;
	int scorePlayer = 0;

	public MainFrame(String userID, String ip) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int axisX = (int) (width / 2) - (WIDTH / 2);
		int axisY = (int) (height / 2) - (HEIGHT / 2);
		this.userID = userID;
		this.ip = ip;

		frame = this;

		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setLocation(axisX, axisY);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		getContentPane().setBackground(new Color(11, 121, 3, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		Font bigFont = new Font("궁서체", Font.BOLD, 12);

		imgCursor = new JImageView("img/cursor2.png");
		imgCursor.setBounds(0, 0, 32, 32);
		add(imgCursor);

		panelTitle = new JPanel();
		panelTitle.setLayout(null);
		panelTitle.setBounds(FRAME_MARGIN, FRAME_MARGIN, WIDTH - FRAME_MARGIN * 2, 30);
		panelTitle.setBackground(new Color(0, 0, 0, 0));
		panelTitle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
			}
		});
		add(panelTitle);
		lblTitle = new JLabel("일어섯다");
		lblTitle.setBounds(FRAME_MARGIN + 6, (30 / 2) - 16, 50, 30);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		panelTitle.add(lblTitle);
		
		pPanel1 = new PlayerPanel();
		pPanel1.setBounds(40, 40, 240, 165);
		add(pPanel1);
		
		pPanel2 = new PlayerPanel();
		pPanel2.setBounds(pPanel1.getX(), pPanel1.getY() + 155, 240, 165);
		add(pPanel2);
		
		pPanel3 = new PlayerPanel();
		pPanel3.setBounds(480, pPanel1.getY(), 240, 165);
		add(pPanel3);
		
		pPanel4 = new PlayerPanel();
		pPanel4.setBounds(pPanel3.getX(), pPanel2.getY(), 240, 165);
		add(pPanel4);

		imgCard1 = new JImageView("img/back.png");
		imgCard1.setBounds(160, 40, CARD_WIDTH, CARD_HEIGHT);
		imgCard1.setVisible(false);
		add(imgCard1);

		imgCard2 = new JImageView("img/back.png");
		imgCard2.setBounds(imgCard1.getX() + CARD_WIDTH + CARD_MARGIN, imgCard1.getY(), CARD_WIDTH, CARD_HEIGHT);
		imgCard2.setVisible(false);
		add(imgCard2);

		lblJokbo1 = new JLabel2D();
		lblJokbo1.setBounds(imgCard2.getX() + CARD_WIDTH + CARD_MARGIN + CARD_MARGIN,
				imgCard2.getY() + CARD_HEIGHT - 30, 50, 30);
		lblJokbo1.setFont(new Font("궁서체", Font.BOLD, 16));
		add(lblJokbo1);

		imgCard3 = new JImageView("img/back.png");
		imgCard3.setBounds(imgCard1.getX(), 340, CARD_WIDTH, CARD_HEIGHT);
		add(imgCard3);

		imgCard4 = new JImageView("img/back.png");
		imgCard4.setBounds(imgCard1.getX() + CARD_WIDTH + CARD_MARGIN, imgCard3.getY(), CARD_WIDTH, CARD_HEIGHT);
		add(imgCard4);

		lblJokbo2 = new JLabel2D("족보");
		lblJokbo2.setBounds(imgCard4.getX() + CARD_WIDTH + CARD_MARGIN + CARD_MARGIN,
				imgCard4.getY() + CARD_HEIGHT - 30, 50, 30);
		lblJokbo2.setFont(new Font("궁서체", Font.BOLD, 16));
		add(lblJokbo2);
		
		imgBGJokbo = new JImageView("img/bg_jokbo.png");
		imgBGJokbo.setAutoScale(true);
		imgBGJokbo.setBounds(imgCard4.getX() + CARD_WIDTH + 40, imgCard3.getY() + 30, 110, 320);
		add(imgBGJokbo);

		panelMenu = new JPanel(null);
		panelMenu.setBackground(new Color(40, 73, 38));
		panelMenu.setBounds(WIDTH - 147, 30, 130, HEIGHT - 40);
		add(panelMenu);

		btnCall = new JButton("콜");
		btnCall.setBounds(0, 10, 130, 50);
		btnCall.setFont(new Font("궁서체", Font.BOLD, 16));
		btnCall.addActionListener(this);
		btnCall.setBackground(new Color(255, 187, 0));
		btnCall.setUI(new StyleButtonUI());
		btnCall.setEnabled(false);
		panelMenu.add(btnCall);

		btnDie = new JButton("다이");
		btnDie.setBounds(0, 70, 130, 50);
		btnDie.setFont(new Font("궁서체", Font.BOLD, 16));
		btnDie.addActionListener(this);
		btnDie.setBackground(new Color(204, 61, 61));
		btnDie.setUI(new StyleButtonUI());
		btnDie.setEnabled(false);
		panelMenu.add(btnDie);

		txtPlus = new JTextField();
		txtPlus.setBounds(0, 150, 130, 40);
		txtPlus.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlus.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		txtPlus.setEnabled(false);
		panelMenu.add(txtPlus);

		btnPlus = new JButton("추가");
		btnPlus.setBounds(0, 190, 130, 50);
		btnPlus.setFont(new Font("궁서체", Font.BOLD, 16));
		btnPlus.setEnabled(false);
		btnPlus.addActionListener(this);
		btnPlus.setBackground(new Color(67, 116, 217));
		btnPlus.setUI(new StyleButtonUI());
		panelMenu.add(btnPlus);

		btnExit = new JButton("탈주");
		btnExit.setBounds(0, HEIGHT - 100, 130, 50);
		btnExit.setFont(new Font("궁서체", Font.BOLD, 16));
		btnExit.addActionListener(this);
		btnExit.setBackground(new Color(65, 175, 57));
		btnExit.setUI(new StyleButtonUI());
		panelMenu.add(btnExit);

		lblPlayer1 = new JLabel2D("플레이어1");
		lblPlayer1.setBounds(10, 250, 130, 50);
		lblPlayer1.setFont(bigFont);
		panelMenu.add(lblPlayer1);

		lblMoney1 = new JLabel2D("0원");
		lblMoney1.setBounds(10, lblPlayer1.getY() + 30, 130, 30);
		lblMoney1.setFont(bigFont);
		panelMenu.add(lblMoney1);

		lblPlayer2 = new JLabel2D("플레이어2");
		lblPlayer2.setBounds(10, lblMoney1.getY() + 50, 130, 50);
		lblPlayer2.setFont(bigFont);
		lblPlayer2.setText(userID);
		panelMenu.add(lblPlayer2);

		lblMoney2 = new JLabel2D("0원");
		lblMoney2.setBounds(10, lblPlayer2.getY() + 30, 130, 30);
		lblMoney2.setFont(bigFont);
		panelMenu.add(lblMoney2);

		lblTotal = new JLabel2D("전체: 0원");
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setBounds(((WIDTH - 150) / 2) - 80, 240, 140, 30);
		lblTotal.setFont(bigFont);
		add(lblTotal);

		lblBetting = new JLabel2D("배팅: 0원");
		lblBetting.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetting.setBounds(((WIDTH - 150) / 2) - 80, lblTotal.getY() + 30, 140, 30);
		lblBetting.setFont(bigFont);
		add(lblBetting);

		imgBG = new JImageView("img/window_frame.png");
		imgBG.setBounds(0, 0, WIDTH, HEIGHT);
		add(imgBG);

		customCursor();
		set2DFont(lblPlayer1, lblPlayer2, lblMoney1, lblMoney2, lblTotal, lblBetting, lblJokbo1, lblJokbo2);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startConnection();
			}
		});
		thread.start();
		
		setVisible(true);
	}
	
	public void set2DFont(JLabel2D...jLabel2Ds) {
		for(JLabel2D label : jLabel2Ds) {
			label.setForeground(Color.white);
			label.setOutlineColor(Color.black);
			label.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		}
	}

	public void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("img/cursor.png");
		Point hotspot = new Point(2, 2);
		Cursor cursor = toolkit.createCustomCursor(image, hotspot, "cursor");
		setCursor(cursor);
	}

	public void startConnection() {

		try {
			System.out.println("*****************클라이언트*******************");

			jokbo = new Jokbo();

			// 소켓 생성: 연결
			socket = new Socket(ip, port);
			System.out.println("연결 성공!");

			// 입력한 내용 읽어오기
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// 데이터 수신
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream());
			out.println(userID);
			out.flush();

			// 클라이언트 스레드 생성
			rcvThread = new ClientReceiveThread(socket, in);
			Thread thread = new Thread(rcvThread);
			thread.start();

			Thread threadPos = new Thread(new Runnable() {
				Point b;
				int x;
				int y;

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(100);
							b = getMousePosition();
							if (b != null) {
								x = (int) b.x;
								y = (int) b.y;
							}

							out.println("/mouse " + x + " " + y);
						} catch (Exception e) {

						}
					}
				}
			});

			if (!userID.equals("admin")) {
				threadPos.start();
			}

			Thread playThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						out.flush();

						if (userID.equals("admin")) {
							imgCursor.setVisible(true);
							imgCursor.setLocation(rcvThread.xPos, rcvThread.yPos);
						} else {
							imgCursor.setVisible(false);
						}

						if (rcvThread.exit()) {
							JOptionPane.showMessageDialog(MainFrame.this, "쫓겨났습니다.");
							System.exit(0);
						}

						if (rcvThread.closeFlag) {
							imgCard1.setImage("img/back.png");
							imgCard2.setImage("img/back.png");
							lblJokbo1.setText("");
							rcvThread.closeFlag = false;
						}

						if (rcvThread.playFlag) {
							if (rcvThread.getPlayer().split(" ")[0].equals(userID)) {
								playerID = rcvThread.getPlayer().split(" ")[1];
								cardType = 0;
							} else {
								playerID = rcvThread.getPlayer().split(" ")[0];
								cardType = 1;
							}
						}

						if (rcvThread.card()) {
							cards[0] = Integer.parseInt(rcvThread.cardString().split(" ")[1]);
							cards[1] = Integer.parseInt(rcvThread.cardString().split(" ")[2]);
							cards[2] = Integer.parseInt(rcvThread.cardString().split(" ")[3]);
							cards[3] = Integer.parseInt(rcvThread.cardString().split(" ")[4]);

							if (cardType == 0) {
								if (userID.equals("admin")) {
									pPanel1.setCard(cardSet[cards[2]], cardSet[cards[3]]);
									lblJokbo1.setText(jokbo.getJokboText(cards[2], cards[3]));
								}
								imgCard3.setImage(cardSet[cards[0]]);
								imgCard4.setImage(cardSet[cards[1]]);
								scoreUser = jokbo.getJokbo(cards[0], cards[1]);
								scorePlayer = jokbo.getJokbo(cards[2], cards[3]);
								lblJokbo2.setText(jokbo.getJokboText(cards[0], cards[1]));
								turn = 1;

								if (scoreUser > scorePlayer) {
									lose = 2;
								} else if (scoreUser < scorePlayer) {
									lose = 1;
								} else {
									lose = 0;
								}

								if (scoreUser == 114) {
									if (scorePlayer >= 10000 && scorePlayer <= 90000) {
										lose = 2;
									} else if (scoreUser >= 10000 && scoreUser <= 90000) {
										lose = 1;
									}
								}

							} else {
								if (userID.equals("admin")) {
									pPanel1.setCard(cardSet[cards[0]], cardSet[cards[1]]);
									lblJokbo1.setText(jokbo.getJokboText(cards[0], cards[1]));
								}
								imgCard3.setImage(cardSet[cards[2]]);
								imgCard4.setImage(cardSet[cards[3]]);
								scoreUser = jokbo.getJokbo(cards[2], cards[3]);
								scorePlayer = jokbo.getJokbo(cards[0], cards[1]);
								lblJokbo2.setText(jokbo.getJokboText(cards[2], cards[3]));
								turn = 2;

								if (scoreUser > scorePlayer) {
									lose = 1;
								} else if (scoreUser < scorePlayer) {
									lose = 2;
								} else {
									lose = 0;
								}

								if (scoreUser == 114) {
									if (scorePlayer >= 10000 && scorePlayer <= 90000) {
										lose = 1;
									} else if (scoreUser >= 10000 && scoreUser <= 90000) {
										lose = 2;
									}
								}

							}

							if (scoreUser == 111 || scoreUser == 112 || scorePlayer == 111 || scorePlayer == 112) {
								lose = 0;
							}

						}

						if (rcvThread.turn == turn) {

							betting = rcvThread.betting;
							total = rcvThread.total;
							lblBetting.setText(betting + "원");
							lblTotal.setText(total + "원");

							if (rcvThread.buttonFlag) {
								if (rcvThread.firstFlag) {
									btnCall.setEnabled(false);
								} else {
									btnCall.setEnabled(true);
								}
								btnDie.setEnabled(true);
								txtPlus.setEnabled(true);
								btnPlus.setEnabled(true);
							}
						} else {
							betting = rcvThread.betting;
							total = rcvThread.total;
							lblBetting.setText(betting + "원");
							lblTotal.setText(total + "원");
							btnCall.setEnabled(false);
							btnDie.setEnabled(false);
							txtPlus.setEnabled(false);
							btnPlus.setEnabled(false);
						}

						if (rcvThread.resultFalg) {
							System.out.println("Client lose = " + rcvThread.lose);
							betting = rcvThread.betting;
							total = rcvThread.total;
							lblBetting.setText(betting + "원");
							lblTotal.setText(total + "원");

							if (rcvThread.openFlag) {
								if (cardType == 0) {
									imgCard1.setIcon(getImage(cardSet[cards[2]]));
									imgCard2.setIcon(getImage(cardSet[cards[3]]));
									lblJokbo1.setText(jokbo.getJokboText(cards[2], cards[3]));
								} else {
									imgCard1.setIcon(getImage(cardSet[cards[0]]));
									imgCard2.setIcon(getImage(cardSet[cards[1]]));
									lblJokbo1.setText(jokbo.getJokboText(cards[0], cards[1]));
								}
							}

							if (rcvThread.lose == turn) {
								JOptionPane.showMessageDialog(MainFrame.this,
										"졌습니다. " + scoreUser + " / " + scorePlayer);
							} else if (rcvThread.lose == 0) {
								JOptionPane.showMessageDialog(MainFrame.this,
										"비겼습니다. " + scoreUser + " / " + scorePlayer);
							} else {
								JOptionPane.showMessageDialog(MainFrame.this,
										"이겼습니다. " + scoreUser + " / " + scorePlayer);
							}
							out.flush();

							btnCall.setEnabled(false);
							btnDie.setEnabled(false);
							txtPlus.setEnabled(false);
							btnPlus.setEnabled(false);
							rcvThread.endFlag = false;
							rcvThread.resultFalg = false;
							rcvThread.openFlag = false;
						}

						lblPlayer1.setText(playerID);

						if (cardType == 0) {
							lblMoney2.setText(rcvThread.money1 + "원");
							lblMoney1.setText(rcvThread.money2 + "원");
						} else {
							lblMoney1.setText(rcvThread.money1 + "원");
							lblMoney2.setText(rcvThread.money2 + "원");
						}

					}
				}
			});
			playThread.start();

			while (true) {
				sendData = reader.readLine(); // 입력한 내용을 한 줄씩 읽어서 저장

				out.println(sendData);
				out.flush();

				if (sendData.equals("/quit")) { // /quit를 입력하면 접속 종료
					endFlag = true;
					break;
				}
			}

			System.out.print("클라이언트의 접속을 종료합니다.");
			System.exit(0);
		} catch (

		UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainFrame.this, "서버가 닫혀있습니다.");
			System.exit(0);
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ImageIcon getImage(String path) {
		return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(CARD_WIDTH,
				CARD_HEIGHT, Image.SCALE_SMOOTH));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = e.getActionCommand();
		switch (buttonText) {
		case "콜":
			if (lose == turn) {
				out.println("/reset true");
			} else if (lose == 0) {
				out.println("/reset false");
			} else {
				out.println("/reset true");
			}
			out.println("/lose " + lose);
			out.println("/turn " + turn + " call " + betting);
			System.out.println("lose = " + lose);
			out.flush();

			break;

		case "다이":
			if (lose == turn) {
				out.println("/reset true");
			} else if (lose == 0) {
				out.println("/reset false");
			} else {
				out.println("/reset true");
			}
			System.out.println("lose = " + turn);
			out.println("/lose " + turn);
			out.println("/turn " + turn + " die");
			out.flush();

			break;

		case "추가":
			try {
				int money = Integer.parseInt(txtPlus.getText());
				if (money > betting) {
					out.println("/turn " + turn + " plus " + money);
					out.flush();
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "배팅 금액보다 커야합니다.");
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(MainFrame.this, "배팅 금액이 이상합니다.");
			}
			break;

		case "탈주":
			System.exit(0);
		}
	}

}
