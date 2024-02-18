package log.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.protocol.Resultset;

public class DataAccess {

	/*
	 * @Dave calling out the key for the DB login to avoid repetition
	 * 
	 */
	private static MysqlDataSource dataSource;

	public static Connection getConnection() throws FileNotFoundException, IOException, SQLException {

		if (dataSource == null) {
			dataSource = new MysqlDataSource();
		}

		Properties prop = new Properties();

		prop.load(new FileInputStream("jdbc.properties"));

		dataSource.setUrl((String) prop.get("url"));
		dataSource.setUser((String) prop.get("username"));
		dataSource.setPassword((String) prop.get("password"));
		dataSource.setServerTimezone("UTC");

		return dataSource.getConnection();

	}

	public static void main(String[] args) {

	}

}
