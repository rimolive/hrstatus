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

package br.com.hrstatus.utils;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

/*
 * @author spolti
 */

@SuppressWarnings("static-access")
public class VersionServlet  extends HttpServlet {

	Logger log =  Logger.getLogger(VersionServlet.class.getCanonicalName());
	
	private static final long serialVersionUID = 1L;

	public VersionServlet() throws IOException {
		loadVersionServlet();
	}
	
	private void loadVersionServlet() throws IOException {
		
		PropertiesLoaderImpl load = new PropertiesLoaderImpl();
	    String version = load.getValor("version");
	    log.info("[ System ] Hr Status version: " + version);
	}	
}