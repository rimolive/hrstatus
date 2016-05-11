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

package br.com.hrstatus.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.hrstatus.dao.BancoDadosInterface;
import br.com.hrstatus.dao.Configuration;
import br.com.hrstatus.model.BancoDados;
import br.com.hrstatus.utils.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Resource
public class ShowDataBaseByStatus {

    private Logger log = Logger.getLogger(ShowDataBaseByStatus.class.getName());

    @Autowired
    private Result result;
    @Autowired
    private BancoDadosInterface dbDAO;
    @Autowired
    private Configuration configurationDAO;
    @Autowired
    private Validator validator;
    private UserInfo userInfo = new UserInfo();

    @Get("/database/showByStatus/{status}")
    public void showByStatus(String status) {

        // Inserting HTML title in the result
        result.include("title", "Hr Status Home");
        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /database/showByStatus/" + status);

        if ("OK".equals(status)) {
            final List<BancoDados> listdb = this.dbDAO.getdataBasesOK();
            result.include("class", "activeBanco");
            result.include("bancoDados", listdb).forwardTo(HomeController.class).home("");

        } else if (!"OK".equals(status)) {
            final List<BancoDados> listdb = this.dbDAO.getdataBasesNOK();
            result.include("class", "activeBanco");
            result.include("bancoDados", listdb).forwardTo(HomeController.class).home("");

        } else {
            validator.onErrorUsePageOf(HomeController.class).home("");
        }
    }
}