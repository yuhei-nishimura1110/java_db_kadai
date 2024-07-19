package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {
	public static void main(String[] args) {

		String[][] post_List = { { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" }, { "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" }, { "1002", "2023-02-10", "明日から連休ですね！", "20" } };
		Connection con = null;
		PreparedStatement pre = null;
		Statement sta = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/challenge_java", "root", "Yuhei11-10");
			System.out.println("接続成功");

			sta = con.createStatement();

			String sql = "INSERT INTO posts (user_id,posted_at,post_content,likes) VALUES (?, ?, ?, ?);";
			pre = con.prepareStatement(sql);

			int rowCnt = 0;
			for (int i = 0; i < post_List.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換え
				pre.setString(1, post_List[i][0]);
				pre.setString(2, post_List[i][1]);
				pre.setString(3, post_List[i][2]);
				pre.setString(4, post_List[i][3]);
				System.out.println("レコード追加:" + pre.toString());
				rowCnt = pre.executeUpdate();
				System.out.println(rowCnt + "件のレコードが追加されました");
			}

			sql = "SELECT * FROM posts WHERE user_id = 1002;";
			ResultSet result = sta.executeQuery(sql);
			while (result.next()) {
				Date postedAt = result.getDate("posted_at");
				String content = result.getString("post_content");
				int like = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "／いいね数=" + like);
			}

		} catch (SQLException e) {
			System.out.println("エラー：" + e.getMessage());
		} finally {
			if (sta != null) {
				try {
					sta.close();
				} catch (SQLException ignore) {
				}
			}
			if (pre != null) {
				try {
					pre.close();
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
