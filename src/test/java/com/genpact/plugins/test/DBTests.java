package com.genpact.plugins.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.genpact.plugins.utils.DBUtil;

public class DBTests {
	private final String baseDir = System.getProperty("user.dir") + File.separatorChar + "db" + File.separatorChar;
	@Test
	public void backup() throws DatabaseUnitException, SQLException, IOException {
		String fileName = "backup.xml";
		// 创建一个 DBunit 的 Connection，须要传入一个 jdbc 的 Connection
		IDatabaseConnection conn = new DatabaseConnection(DBUtil.getCon());
		// 用连接创建与数据库相关的数据集
		IDataSet ds = conn.createDataSet();
		// 将 ds 中的数据通过 FlatXmlDataSet 这个格式写到 xml 文件中
		File file = new File(baseDir + fileName);
		if (file.exists()) {
			file.delete();
		}

		// 备份特定的表
		// QueryDataSet backup = new QueryDataSet(conn);
		// // 添加 t_user 这张表作为备份的表
		// backup.addTable("t_user");
		// FlatXmlDataSet.write(backup, new FileWriter(file));
		FlatXmlDataSet.write(ds, new FileWriter(file));
	}

	@Test
	public void recover() throws DatabaseUnitException, SQLException, IOException {
		String fileName = "recover.xml";
		// 创建一个 DBunit 的 Connection，须要传入一个 jdbc 的 Connection
		IDatabaseConnection conn = new DatabaseConnection(DBUtil.getCon());
		// 根据备份文件创建 dataset
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(baseDir + fileName))));
		DatabaseOperation.CLEAN_INSERT.execute(conn, ds);
	}

	

}
