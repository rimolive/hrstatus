/*
    Copyright (C) 2012  Filippe Costa Spolti

	This file is part of Hrstatus.

    Hrstatus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.hrstatus.action.databases.db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;

import br.com.hrstatus.utils.UserInfo;

/*
 * @author spolti
 */

public class ConnDB2 {

	static Logger log = Logger.getLogger(ConnDB2.class.getCanonicalName());
	public static boolean status = false;

	public static Connection getConexaoDB2(String serverAddress, int port, String username, String password, String instance) throws ClassNotFoundException, SQLException {

		UserInfo userInfo = new UserInfo();
		Connection connection = null;
		String driver = "com.ibm.db2.jcc.DB2Driver";
		Class.forName(driver);
		String url = "jdbc:db2://" + serverAddress + ":" + port + "/" + instance;
		log.fine("DB2 URL connection: " + url);

		connection = DriverManager.getConnection(url, username, password);

		// Testing if the connection was successfully obtained.
		if (connection != null) {
			status = (true);
			log.fine("[ " + userInfo.getLoggedUsername() + " ] DB2 datbase connection status: " + status);
		} else {
			status = (false);
			log.fine("[ " + userInfo.getLoggedUsername() + " ] DB2 datbase connection status: " + status);
		}
		return connection;
	}
}