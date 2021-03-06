import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientReceiveThread implements Runnable {

	Socket socket;
	BufferedReader in;
	String receiveData;
	String strCard;
	String user;
	String userID;

	String[] names;
	String[] indexes;
	String[] cards;
	
	String nextPlayer = "";

	int turn;
	int myTurn;
	int myIndex = 0;
	int lose;
	int betting = 0;
	int total = 0;
	int money1 = 0;
	int money2 = 0;

	int xPos = 0;
	int yPos = 0;

	boolean playFlag = false;
	boolean endFlag = false;
	boolean cardFlag = false;
	boolean firstFlag = false;
	boolean resultFalg = false;
	boolean openFlag = false;
	boolean closeFlag = false;
	boolean buttonFlag = false;
	boolean readyFlag = false;

	public ClientReceiveThread(Socket socket, BufferedReader in, String userID) {
		this.socket = socket;
		this.in = in;
		this.userID = userID;
	}

	public boolean exit() {
		return endFlag;
	}

	public boolean play() {
		return playFlag;
	}

	public boolean card() {
		return cardFlag;
	}

	public String cardString() {
		return strCard;
	}

	public String getPlayer() {
		return user;
	}

	@Override
	public void run() {
		try {

			while ((receiveData = in.readLine()) != null) {
				String tag = receiveData.split(" ")[0];
				
				if (!tag.equals("/mouse")) {
					System.out.println("Client Received = " + receiveData);
				}

				if (receiveData.equals("/quit")) {
					endFlag = true;
				}

				if (tag.equals("/mouse")) {
					xPos = Integer.parseInt(receiveData.split(" ")[1]);
					yPos = Integer.parseInt(receiveData.split(" ")[2]);
				}
				
				if (tag.equals("/player")) {
					String result = receiveData.substring(8, receiveData.length());
					System.out.println("result = " + result);
					names = result.split(" ");
				}
				
				if (tag.equals("/index")) {
					String result = receiveData.substring(7, receiveData.length());
					indexes = result.split(" ");
					
					for (int i = 0; i < names.length; i++) {
						if (names[i].equals(userID)) {
							myIndex = Integer.parseInt(indexes[i]);
						}
					}
				}

				if (tag.equals("/play")) {
					playFlag = true;
				}
				
				if (tag.equals("/ready")) {
					readyFlag = Boolean.parseBoolean(receiveData.split(" ")[1]);
				}

				if (tag.equals("/card")) {
					cardFlag = true;
					strCard = receiveData;
					
					String card = receiveData.substring(6, receiveData.length());
					cards = card.split(" ");
					System.out.println("card set = " + card);
				}

				if (tag.equals("/turn")) {
					//turn = Integer.parseInt(receiveData.split(" ")[1]);
					nextPlayer = receiveData.split(" ")[1];
					
					/*if (receiveData.split(" ")[2].equals("true")) {
						firstFlag = true;
					} else {
						firstFlag = false;
					}
					betting = Integer.parseInt(receiveData.split(" ")[3]);
					total = Integer.parseInt(receiveData.split(" ")[4]);*/
				}

				if (tag.equals("/result")) {
					lose = Integer.parseInt(receiveData.split(" ")[1]);
					resultFalg = true;
				}

				if (tag.equals("/money")) {
					money1 = Integer.parseInt(receiveData.split(" ")[1]);
					money2 = Integer.parseInt(receiveData.split(" ")[2]);
				}

				if (tag.equals("/open")) {
					openFlag = true;
				}

				if (tag.equals("/close")) {
					closeFlag = true;
				}

				if (tag.equals("/button")) {
					if (receiveData.split(" ")[1].equals("false")) {
						buttonFlag = false;
					} else {
						buttonFlag = true;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
