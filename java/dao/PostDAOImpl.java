package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Post;

public class PostDAOImpl implements PostDAO {

	private static Connection con;
	public static int j = 0;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			String q = "select * from post1";
			Statement st = con.createStatement();

			ResultSet i = st.executeQuery(q);

			while (i.next()) {

				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Post insert(Post post) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("insert into post1 (id,body) values(?,?)");
			pst.setString(2, post.getBody());
			pst.setInt(1, post.getId());
			res = pst.executeUpdate();

			if (res == 1) {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID();");
				if (rs.next()) {
					post.setId(rs.getInt(1));
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public int update(Post post) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("update post1 set body = ? where id = ?");

			pst.setString(1, post.getBody());
			pst.setInt(2, post.getId());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int delete(int id) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("delete from post1 where id = ?");
			pst.setInt(1, id);
			res = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Post> view() {

		ArrayList<Post> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from post1");
			while (rs.next()) {
				list.add(new Post(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Post view(int id) {
		Post post = new Post();
		try {
			PreparedStatement pst = con.prepareStatement("select * from post1 where id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				post = new Post(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}

}