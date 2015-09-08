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

package br.com.hrstatus.action.databases.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import br.com.hrstatus.utils.UserInfo;

/*
 * @author spolti
 */

public class ConnPostgreSQL {

	static Logger log = Logger.getLogger(ConnPostgreSQL.class
			.getCanonicalName());
	public static boolean status = false;

	public ConnPostgreSQL() {
	}

	public static Connection getConexaoPSQL(String serverAddress,int port, String database, String username, String password) throws ClassNotFoundException, SQLException {

		UserInfo userInfo = new UserInfo();
		Connection connection = null;
		String driver = "org.postgresql.Driver";
		Class.forName(driver);
		String url = ("jdbc:postgresql://" + serverAddress + ":" + port + "/" + database);
		connection = DriverManager.getConnection(url, username, password);
		log.fine("POstgreSQL URL connection: " + url);

		// Testing if the connection was successfully obtained.
		if (connection != null) {
			status = (true);
			log.fine("[ " + userInfo.getLoggedUsername() + " ] PostgreSQL datbase connection status: " + status);
		} else {
			status = (false);
			log.fine("[ " + userInfo.getLoggedUsername() + " ] PostgreSQL datbase connection status: " + status);
		}
		return connection;

	}
}