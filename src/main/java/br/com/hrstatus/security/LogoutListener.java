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

package br.com.hrstatus.security;

import java.util.logging.Logger;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/*
 *Spring Framework 
 *Customization, rewrite LoginSuccessHandler
 */

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

	Logger log =  Logger.getLogger(LogoutListener.class.getCanonicalName());
	
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
    	
        try{
    	 SecurityContext securityContext = (SecurityContext) event.getSecurityContexts();
    	 UserDetails ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
         log.info("Session expires or the user " + ud.getUsername() + "logouts");
         
    	}catch (Exception e){
    		 log.fine("There is no user in the session.");
    	}
    }
}