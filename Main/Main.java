import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static final void main(String args[]) {
		new Main().solve();
	}

	void solve() {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("SampleAI.java");
			System.out.flush();
			while (true) {
				System.out.print(think(sc));
				System.out.flush();
			}
		}
	}

	private static final int dx[] = { 0, 1, 0, -1 };
	private static final int dy[] = { 1, 0, -1, 0 };
	private static final String ds[] = { "L", "U", "R", "D" };
	private int[] trs , tcs;
	int point, map_row, map_col;
	boolean[][] map, itemMap;

	String think(Scanner sc) {
		StringBuilder res = new StringBuilder();
		long millitime = sc.nextLong();
		int skills = sc.nextInt();
		int skillCost[] = new int[skills];
		for (int i = 0; i < skillCost.length; ++i) {
			skillCost[i] = sc.nextInt();
		}
		{
			point = sc.nextInt(); //[ストックされている忍力]
			map_row = sc.nextInt();
			map_col = sc.nextInt();
			int n;
			map = new boolean[map_row][map_col];
			for (int r = 0; r < map_row; ++r) {
				String line = sc.next();
				for (int c = 0; c < map_col; ++c) {
					map[r][c] = line.charAt(c) == '_';
				}
			}

			// character
			n = sc.nextInt();
			int rows[] = new int[n];
			int cols[] = new int[n];
			trs = new int[n];
			tcs = new int[n];
			
			for (int i = 0; i < n; ++i) {
				int id = sc.nextInt(), row = sc.nextInt(), col = sc.nextInt();
				rows[i] = row;
				cols[i] = col;
			}
			// zomble
			n = sc.nextInt();
			for (int i = 0; i < n; ++i) {
				int id = sc.nextInt(), row = sc.nextInt(), col = sc.nextInt();
			}
			// item
			itemMap = new boolean[map_row][map_col];
			n = sc.nextInt();
			for (int i = 0; i < n; ++i) {
				int row = sc.nextInt(), col = sc.nextInt();
				itemMap[row][col] = true;
			}
			int useSkill[] = new int[skills];
			for (int i = 0; i < skills; ++i) {
				useSkill[i] = sc.nextInt();
			}
			res.append(rows.length).append("\n");
			for (int i = 0; i < rows.length; ++i) {
				res.append(order(i, rows[i], cols[i])).append("\n");
			}
		}
		{
			int point = sc.nextInt(), map_row = sc.nextInt(), map_col = sc.nextInt();
			boolean map[][] = new boolean[map_row][map_col];
			for (int r = 0; r < map_row; ++r) {
				String line = sc.next();
				for (int c = 0; c < map_col; ++c) {
					map[r][c] = line.charAt(c) == '_';
				}
			}
			for (int i = 0, n = sc.nextInt(); i < n; ++i) {
				int id = sc.nextInt(), row = sc.nextInt(), col = sc.nextInt();
			}
			for (int i = 0, n = sc.nextInt(); i < n; ++i) {
				int id = sc.nextInt(), row = sc.nextInt(), col = sc.nextInt();
			}
			for (int i = 0, n = sc.nextInt(); i < n; ++i) {
				int row = sc.nextInt(), col = sc.nextInt();
			}
			for (int i = 0; i < skills; ++i) {
				int use = sc.nextInt();
			}
		}
		return res.toString();
	}

	String order(int charNum, final int row, final int col) {
		//row, col characterの位置
		//map上の点
		int dist[][] = new int[map_row][map_col];
		//初期化
		for (int i = 0; i < dist.length; ++i)
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		//characterの位置の初期化
		dist[row][col] = 0;
		int qr[] = new int[map_row * map_col], qc[] = new int[map_row * map_col], qi = 0, qe = 1;
		qr[0] = row;
		qc[0] = col;
		while (qi < qe) {
			// 座標点の集合
			int r = qr[qi], c = qc[qi];
			++qi;
			for (int i = 0; i < 4; ++i) {
				int nr = r + dx[i], nc = c + dy[i];
				//地図上で進めるところ、かつまだ計算してないところを探して、道のりを入れる
				if (map[nr][nc] && dist[nr][nc] == Integer.MAX_VALUE) {
					dist[nr][nc] = dist[r][c] + 1;
					qr[qe] = nr;
					qc[qe] = nc;
					++qe;
				}
			}
		}

		// アイテムまで一番近いところを探す
		int tr = -1, tc = -1, tdist = Integer.MAX_VALUE;
		for (int r = 0; r < map_row; ++r) {
			for (int c = 0; c < map_col; ++c) {
				if (itemMap[r][c] && tdist > dist[r][c]) {
					if (charNum == 0 || checkOtherTarget(r, c)) {
						tdist = dist[r][c];
						tr = r;
						tc = c;
					}
				}
			}
		}

		//一番近い点までの道のりを探す
		StringBuilder res = new StringBuilder();
		
		if (tdist != Integer.MAX_VALUE) {
			while (dist[tr][tc] > 0) {
				trs[charNum] = tr;
				tcs[charNum] = tc;
				for (int i = 0; i < 4; ++i) {
					int nr = tr + dx[i], nc = tc + dy[i];
					if (dist[tr][tc] > dist[nr][nc]) {
						tr = nr;
						tc = nc;
						res.append(ds[i]);
						break;
					}
				}
			}
		}
		
		String s = res.reverse().toString();
		return s.length() <= 2 ? s : s.substring(0, 2);
	}
	
	private Map<String, String> getFirePoint() {
		Map<String, String> point = new HashMap<String, String>();
		return point;
	}
	
	private boolean checkOtherTarget(int tr, int tc){
		for (int i = 0; i < trs.length; i ++) {
			if (trs[i] == tr && tcs[i] == tc) {
				return false;
			}
		}
		return true;
	}
}
