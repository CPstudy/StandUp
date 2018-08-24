import java.util.HashMap;

public class Jokbo {
	
	/*
	 * 0: 1岿(堡)
	 * 1: 1岿
	 * 2: 2岿
	 * 3: 2岿
	 * 4: 3岿(堡)
	 * 5: 3岿
	 * 6: 4岿(凯)
	 * 7: 4岿
	 * 8: 5岿(凯)
	 * 9: 5岿
	 * 10: 6岿
	 * 11: 6岿
	 * 12: 7岿
	 * 13: 7岿
	 * 14: 8岿(堡)
	 * 15: 8岿
	 * 16: 9岿(凯)
	 * 17: 9岿
	 * 18: 10岿
	 * 19: 10岿
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
			// 伙迫堡动
			score = 999999;
		} else if(a == 0 && b == 14) {
			// 老迫堡动
			score = 888888;
		} else if(a == 0 && b == 4) {
			// 老伙堡大
			score = 777777;
		} else if(a == 18 && b == 19) {
			// 厘动
			score = 100000;
		} else if(a == 16 && b == 17) {
			// 9动
			score = 90000;
		} else if(a == 14 && b == 15) {
			// 8动
			score = 80000;
		} else if(a == 12 && b == 13) {
			// 7动
			score = 70000;
		} else if(a == 10 && b == 11) {
			// 6动
			score = 60000;
		} else if(a == 8 && b == 9) {
			// 5动
			score = 50000;
		} else if(a == 6 && b == 7) {
			// 4动
			score = 40000;
		} else if(a == 4 && b == 5) {
			// 3动
			score = 30000;
		} else if(a == 2 && b == 3) {
			// 2动
			score = 20000;
		} else if(a == 0 && b == 1) {
			// 厘绘
			score = 10000;
		} else if((a == 0 || a == 1) && (b == 2 || b == 3)) {
			// 舅府
			score = 9000;
		} else if((a == 0 || a == 1) && (b == 6 || b == 7)) {
			// 刀荤
			score = 8000;
		} else if((a == 0 || a == 1) && (b == 16 || b == 17)) {
			// 备绘
			score = 7000;
		} else if((a == 0 || a == 1) && (b == 18 || b == 19)) {
			// 厘绘
			score = 6000;
		} else if((a == 6 || a == 7) && (b == 18 || b == 19)) {
			// 厘荤
			score = 5000;
		} else if((a == 6 || a == 7) && (b == 10 || b == 11)) {
			// 技氟
			score = 4000;
		} else if((a == 4 || a == 5) && (b == 12 || b == 13)) {
			// 动棱捞
			score = 114;
		} else if(a == 6 && b == 12) {
			// 鞠青绢荤
			score = 100;
		} else if((a == 6 || a == 7) && (b == 16 || b == 17)) {
			if(a == 6  &&  b == 16) {
				// 港胖备府 备荤
				score = 112;
			} else {
				// 备荤
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
			// 伙迫堡动
			jokbo = "伙迫堡动";
		} else if(a == 0 && b == 14) {
			// 老迫堡动
			jokbo = "老迫堡大";
		} else if(a == 0 && b == 4) {
			// 老伙堡大
			jokbo = "老伙堡动";
		} else if(a == 18 && b == 19) {
			// 厘动
			jokbo = "厘动";
		} else if(a == 16 && b == 17) {
			// 9动
			jokbo = "9动";
		} else if(a == 14 && b == 15) {
			// 8动
			jokbo = "8动";
		} else if(a == 12 && b == 13) {
			// 7动
			jokbo = "7动";
		} else if(a == 10 && b == 11) {
			// 6动
			jokbo = "6动";
		} else if(a == 8 && b == 9) {
			// 5动
			jokbo = "5动";
		} else if(a == 6 && b == 7) {
			// 4动
			jokbo = "4动";
		} else if(a == 4 && b == 5) {
			// 3动
			jokbo = "3动";
		} else if(a == 2 && b == 3) {
			// 2动
			jokbo = "2动";
		} else if(a == 0 && b == 1) {
			// 厘绘
			jokbo = "厘绘";
		} else if((a == 0 || a == 1) && (b == 2 || b == 3)) {
			// 舅府
			jokbo = "舅府";
		} else if((a == 0 || a == 1) && (b == 6 || b == 7)) {
			// 刀荤
			jokbo = "刀荤";
		} else if((a == 0 || a == 1) && (b == 16 || b == 17)) {
			// 备绘
			jokbo = "备绘";
		} else if((a == 0 || a == 1) && (b == 18 || b == 19)) {
			// 厘绘
			jokbo = "厘绘";
		} else if((a == 6 || a == 7) && (b == 18 || b == 19)) {
			// 厘荤
			jokbo = "厘荤";
		} else if((a == 6 || a == 7) && (b == 10 || b == 11)) {
			// 技氟
			jokbo = "技氟";
		} else if((a == 4 || a == 5) && (b == 12 || b == 13)) {
			// 动棱捞
			jokbo = "动棱捞";
		} else if(a == 6 && b == 12) {
			// 鞠青绢荤
			jokbo = "47 鞠青绢荤";
		} else if((a == 6 || a == 7) && (b == 16 || b == 17)) {
			if(a == 6  &&  b == 16) {
				// 港胖备府 备荤
				jokbo = "港胖备府 备荤";
			} else {
				// 备荤
				jokbo = "备荤";
			}
		} else {
			int c = (map.get(a) + map.get(b)) % 10;

			if(c == 0) {
				jokbo = "噶烹";
			} else {
				jokbo = c + "昌";
			}
		}
		
		return jokbo;
	}
}
