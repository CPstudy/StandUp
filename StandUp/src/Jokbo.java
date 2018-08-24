import java.util.HashMap;

public class Jokbo {
	
	/*
	 * 0: 1��(��)
	 * 1: 1��
	 * 2: 2��
	 * 3: 2��
	 * 4: 3��(��)
	 * 5: 3��
	 * 6: 4��(��)
	 * 7: 4��
	 * 8: 5��(��)
	 * 9: 5��
	 * 10: 6��
	 * 11: 6��
	 * 12: 7��
	 * 13: 7��
	 * 14: 8��(��)
	 * 15: 8��
	 * 16: 9��(��)
	 * 17: 9��
	 * 18: 10��
	 * 19: 10��
	 */
	
	HashMap<Integer, Integer> map = new HashMap<>();
	
	Jokbo() {
		map.put(0, 1);
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 2);
		map.put(4, 3);
		map.put(5, 3);
		map.put(6, 4);
		map.put(7, 4);
		map.put(8, 5);
		map.put(9, 5);
		map.put(10, 6);
		map.put(11, 6);
		map.put(12, 7);
		map.put(13, 7);
		map.put(14, 8);
		map.put(15, 8);
		map.put(16, 9);
		map.put(17, 9);
		map.put(18, 10);
		map.put(19, 10);
	}
	
	public int getJokbo(int card1, int card2) {
		int score = 0;
		int a = card1;
		int b = card2;
		
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(a == 4 && b == 14) {
			// ���ȱ���
			score = 999999;
		} else if(a == 0 && b == 14) {
			// ���ȱ���
			score = 888888;
		} else if(a == 0 && b == 4) {
			// �ϻﱤ��
			score = 777777;
		} else if(a == 18 && b == 19) {
			// �嶯
			score = 100000;
		} else if(a == 16 && b == 17) {
			// 9��
			score = 90000;
		} else if(a == 14 && b == 15) {
			// 8��
			score = 80000;
		} else if(a == 12 && b == 13) {
			// 7��
			score = 70000;
		} else if(a == 10 && b == 11) {
			// 6��
			score = 60000;
		} else if(a == 8 && b == 9) {
			// 5��
			score = 50000;
		} else if(a == 6 && b == 7) {
			// 4��
			score = 40000;
		} else if(a == 4 && b == 5) {
			// 3��
			score = 30000;
		} else if(a == 2 && b == 3) {
			// 2��
			score = 20000;
		} else if(a == 0 && b == 1) {
			// ���
			score = 10000;
		} else if((a == 0 || a == 1) && (b == 2 || b == 3)) {
			// �˸�
			score = 9000;
		} else if((a == 0 || a == 1) && (b == 6 || b == 7)) {
			// ����
			score = 8000;
		} else if((a == 0 || a == 1) && (b == 16 || b == 17)) {
			// ����
			score = 7000;
		} else if((a == 0 || a == 1) && (b == 18 || b == 19)) {
			// ���
			score = 6000;
		} else if((a == 6 || a == 7) && (b == 18 || b == 19)) {
			// ���
			score = 5000;
		} else if((a == 6 || a == 7) && (b == 10 || b == 11)) {
			// ����
			score = 4000;
		} else if((a == 4 || a == 5) && (b == 12 || b == 13)) {
			// ������
			score = 114;
		} else if(a == 6 && b == 12) {
			// ������
			score = 100;
		} else if((a == 6 || a == 7) && (b == 16 || b == 17)) {
			if(a == 6  &&  b == 16) {
				// ���ֱ��� ����
				score = 112;
			} else {
				// ����
				score = 111;
			}
		} else {
			int c = (map.get(a) + map.get(b)) % 10;
			
			score = c;
		}
		
		return score;
	}
	
	public String getJokboText(int card1, int card2) {
		int a = card1;
		int b = card2;
		String jokbo = "";
		
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(a == 4 && b == 14) {
			// ���ȱ���
			jokbo = "���ȱ���";
		} else if(a == 0 && b == 14) {
			// ���ȱ���
			jokbo = "���ȱ���";
		} else if(a == 0 && b == 4) {
			// �ϻﱤ��
			jokbo = "�ϻﱤ��";
		} else if(a == 18 && b == 19) {
			// �嶯
			jokbo = "�嶯";
		} else if(a == 16 && b == 17) {
			// 9��
			jokbo = "9��";
		} else if(a == 14 && b == 15) {
			// 8��
			jokbo = "8��";
		} else if(a == 12 && b == 13) {
			// 7��
			jokbo = "7��";
		} else if(a == 10 && b == 11) {
			// 6��
			jokbo = "6��";
		} else if(a == 8 && b == 9) {
			// 5��
			jokbo = "5��";
		} else if(a == 6 && b == 7) {
			// 4��
			jokbo = "4��";
		} else if(a == 4 && b == 5) {
			// 3��
			jokbo = "3��";
		} else if(a == 2 && b == 3) {
			// 2��
			jokbo = "2��";
		} else if(a == 0 && b == 1) {
			// ���
			jokbo = "���";
		} else if((a == 0 || a == 1) && (b == 2 || b == 3)) {
			// �˸�
			jokbo = "�˸�";
		} else if((a == 0 || a == 1) && (b == 6 || b == 7)) {
			// ����
			jokbo = "����";
		} else if((a == 0 || a == 1) && (b == 16 || b == 17)) {
			// ����
			jokbo = "����";
		} else if((a == 0 || a == 1) && (b == 18 || b == 19)) {
			// ���
			jokbo = "���";
		} else if((a == 6 || a == 7) && (b == 18 || b == 19)) {
			// ���
			jokbo = "���";
		} else if((a == 6 || a == 7) && (b == 10 || b == 11)) {
			// ����
			jokbo = "����";
		} else if((a == 4 || a == 5) && (b == 12 || b == 13)) {
			// ������
			jokbo = "������";
		} else if(a == 6 && b == 12) {
			// ������
			jokbo = "47 ������";
		} else if((a == 6 || a == 7) && (b == 16 || b == 17)) {
			if(a == 6  &&  b == 16) {
				// ���ֱ��� ����
				jokbo = "���ֱ��� ����";
			} else {
				// ����
				jokbo = "����";
			}
		} else {
			int c = (map.get(a) + map.get(b)) % 10;

			if(c == 0) {
				jokbo = "����";
			} else {
				jokbo = c + "��";
			}
		}
		
		return jokbo;
	}
}
