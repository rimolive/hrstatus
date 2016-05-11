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

package br.com.hrstatus.scheduler;

import br.com.hrstatus.dao.UsersInterface;
import br.com.hrstatus.model.PassExpire;
import br.com.hrstatus.model.Users;
import br.com.hrstatus.utils.date.DateParser;
import br.com.hrstatus.utils.date.DateUtils;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Service
public class PassExpireScheduler {

    private Logger log = Logger.getLogger(PassExpireScheduler.class.getName());

    @Autowired
    private UsersInterface userDAO;
    private DateUtils time = new DateUtils();
    private DateParser parse = new DateParser();

    public PassExpireScheduler() {
    }

    @Scheduled(cron = "0 0/5 * * * *") //5 in 5 minutes
    public void passExpire() throws ParseException, JSchException, IOException {

        log.fine("[ System ] Invoking passExpire() at " + new Date());

        final List<PassExpire> list = this.userDAO.getExpireTime();

        final Date timeNow = parse.parser(time.getTime());

        for (PassExpire passExpire : list) {
            if (timeNow.compareTo(parse.parser(passExpire.getExpireTime())) > 0) {

                log.fine("[ System ] Temporary password generated to " + passExpire.getUsername() + " expired, Rolling the password back..");
                final Users user = this.userDAO.getUserByIDNotLogged(passExpire.getUsername());

                user.setPassword(passExpire.getOldPwd());
                user.setFirstLogin(false);

                // Saving the changes
                this.userDAO.updateUserNotLogged(user);

                // Removing the user from temporary table
                this.userDAO.delUserExpireTimeNotLogged(passExpire);

            } else {
                log.fine("[ System ] The password generated for the user " + passExpire.getUsername() + " not expire yet.");
            }
        }
    }
}