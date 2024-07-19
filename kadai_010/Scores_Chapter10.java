package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Connection con = null;
		Statement sta = null;
		Scanner sca = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/challenge_java", "root", "Yuhei11-10");

			System.out.println("接続成功");

			sta = con.createStatement();
			String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";

			System.out.println("レコード更新:" + sta.toString());
			int rowCnt = sta.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが更新されました");

			sql = "SELECT * FROM scores ORDER BY score_math DESC,score_english DESC;";

			System.out.println("データ取得を実行：" + sql);
			ResultSet re = sta.executeQuery(sql);

			while (re.next()) {
				int id = re.getInt("id");
				String name = re.getString("name");
				int math = re.getInt("score_math");
				int english = re.getInt("score_english");
				System.out.println(re.getRow() + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + math + "／英語=" + english);
			}

		} catch (SQLException e) {
			System.out.println("エラー：" + e.getMessage());
		} finally {
			if (sca != null) {
				sca.close();
			}
			if (sta != null) {
				try {
					sta.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

}
