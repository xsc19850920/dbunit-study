package com.genpact.plugins.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	public static Connection getCon() {
		Connection con = null;

		try {
			Properties properties = new Properties();
			String propertyFilePath = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar + "application.properties";
			properties.load(new FileInputStream(new File(propertyFilePath)));
			String url = properties.getProperty("jdbc.url");
			String username = properties.getProperty("jdbc.username");
			String pass = properties.getProperty("jdbc.password");
			con = DriverManager.getConnection(url, username, pass);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return con;
	}
}
