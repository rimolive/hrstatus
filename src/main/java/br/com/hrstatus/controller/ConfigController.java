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

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.hrstatus.dao.UsersInterface;
import br.com.hrstatus.dao.ServersInterface;
import br.com.hrstatus.dao.BancoDadosInterface;
import br.com.hrstatus.dao.Configuration;
import br.com.hrstatus.dao.InstallProcessInterface;
import br.com.hrstatus.model.BancoDados;
import br.com.hrstatus.model.Configurations;
import br.com.hrstatus.model.Servidores;
import br.com.hrstatus.model.Users;
import br.com.hrstatus.utils.GetSystemInformation;
import br.com.hrstatus.utils.PropertiesLoaderImpl;
import br.com.hrstatus.utils.UserInfo;
import br.com.hrstatus.utils.mail.GetAvailableMailSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Resource
public class ConfigController {

    Logger log = Logger.getLogger(ConfigController.class.getCanonicalName());

    @Autowired
    private Result result;
    @Autowired
    private ServersInterface iteracoesDAO;
    @Autowired
    private Configuration configurationDAO;
    @Autowired
    private Validator validator;
    @Autowired
    private UsersInterface userDAO;
    @Autowired
    private BancoDadosInterface BancoDadosDAO;
    @Autowired
    private InstallProcessInterface ipi;
    private UserInfo userInfo = new UserInfo();
    private GetSystemInformation getSys = new GetSystemInformation();

    @SuppressWarnings("static-access")
    @Get("/configServer")
    public void configServer() throws Exception {

        //Sending information to "About" page
        final PropertiesLoaderImpl load = new PropertiesLoaderImpl();
        final String version = load.getValor("version");
        result.include("version", version);
        final List<String> info = getSys.SystemInformation();
        result.include("jvmName", info.get(2));
        result.include("jvmVendor", info.get(1));
        result.include("jvmVersion", info.get(0));
        result.include("osInfo", info.get(3));
        result.include("installDate", ipi.getInstallationDate());

        // Inserting HTML title in the result
        result.include("title", "Configurar Servidor");

        result.include("loggedUser", userInfo.getLoggedUsername());

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /configServer");

        final Configurations opts = this.configurationDAO.getConfigs();
        final int diff = opts.getDifference();

        final String mailFrom = opts.getMailFrom();
        final String subject = opts.getSubject();
        final String dests = opts.getDests();
        final String jndiMail = opts.getJndiMail();
        final String ntpServer = opts.getNtpServer();
        final boolean updateNtpIsActive = opts.isUpdateNtpIsActive();
        final boolean sendNotification = opts.isSendNotification();
        final GetAvailableMailSession getMS = new GetAvailableMailSession();
        final ArrayList<String> mailSessions = getMS.listAllMailSessions();
        final ArrayList<String> mailSessionstemp = new ArrayList<String>();

        log.fine("Difference time: " + diff);
        log.fine("Sender: " + mailFrom);
        log.fine("Send Notification: " + sendNotification);
        log.fine("Subject: " + subject);
        log.fine("Dests: " + dests);
        log.fine("JndiMail: " + jndiMail);
        log.fine("NtpServer: " + ntpServer);
        log.fine("updateNtpIsActive: " + updateNtpIsActive);

        result.include("difference", diff);
        result.include("mailFrom", mailFrom);

        if (sendNotification) {
            result.include("sendNotification", "ATIVO");
        } else {
            result.include("sendNotification", "INATIVO");
        }

        result.include("subject", subject);
        result.include("dests", dests);
        result.include("jndiMail", jndiMail);
        result.include("ntpServer", ntpServer);

        for (int i = 0; i < mailSessions.size(); i++) {
            mailSessionstemp.add(mailSessions.get(i).replace("%x3a", ":"));
            log.fine("********Mail Sessions available: [" + i + "] - " + mailSessionstemp.get(i));
        }

        result.include("mailSessions", mailSessionstemp);

        if (updateNtpIsActive) {
            result.include("updateNtpIsActive", "ATIVO");
        } else {
            result.include("updateNtpIsActive", "INATIVO");
        }
    }

    @SuppressWarnings("static-access")
    @Post("/updateConfig")
    public void updateConfig(String new_value, String campo) throws Exception {

        //Sending information to "About" page
        final PropertiesLoaderImpl load = new PropertiesLoaderImpl();
        final String version = load.getValor("version");
        result.include("version", version);
        final List<String> info = getSys.SystemInformation();
        result.include("jvmName", info.get(2));
        result.include("jvmVendor", info.get(1));
        result.include("jvmVersion", info.get(0));
        result.include("osInfo", info.get(3));
        result.include("installDate", ipi.getInstallationDate());

        // Inserting HTML title in the result
        result.include("title", "Configurar Servidor");

        result.include("loggedUser", userInfo.getLoggedUsername());

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /updateConfig");
        log.fine("[ " + userInfo.getLoggedUsername() + " ] Value received [" + campo + ": " + new_value + "].");

        final String regex_mail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        final Configurations config = this.configurationDAO.getConfigs();

        if ("".equals(new_value)) {
            validator.add(new ValidationMessage("Algum valor deve ser informado", "Erro"));
        } else if ("difference".equals(campo)) {
            try {
                final int secs = Integer.parseInt(new_value);
                config.setDifference(secs);

            } catch (Exception e) {
                validator.add(new ValidationMessage("" + e, "Erro"));
                validator.onErrorForwardTo(ConfigController.class).configServer();
            }

        } else if ("mailFrom".equals(campo)) {

            if (!new_value.matches(regex_mail)) {
                validator.add(new ValidationMessage("E-mail " + new_value + " não está correto", "Erro"));
            } else {
                config.setMailFrom(new_value);
            }
        } else if ("sendNotification".equals(campo)) {
            if ("ATIVO".equals(new_value.toUpperCase())) {
                config.setSendNotification(true);
            } else if ("INATIVO".equals(new_value.toUpperCase())) {
                config.setSendNotification(false);
            } else {
                validator.add(new ValidationMessage("O campo Ativar Ativar Notificação Via e-mail só permite os valores ATIVO ou INATIVO", "Erro"));
            }

        } else if ("subject".equals(campo)) {
            config.setSubject(new_value);
        } else if ("ntpServer".equals(campo)) {
            config.setNtpServer(new_value);
        } else if ("jndiMail".equals(campo)) {
            if (new_value.contains("%x3a")) {
                new_value.replace("%x3a", ":");
            }
            config.setJndiMail(new_value);
        } else if ("dests".equals(campo)) {
            final String[] mail_dests = new_value.split(",");

            for (int i = 0; i < mail_dests.length; i++) {
                log.fine("[ " + userInfo.getLoggedUsername() + " ] RCPT TO Emails: [ " + i + " ]: " + mail_dests[i]);
                if (!mail_dests[i].matches(regex_mail)) {
                    validator.add(new ValidationMessage("E-mail " + mail_dests[i] + " não está correto", "Erro"));
                } else {
                    config.setDests(new_value);
                }
            }

        } else if ("updateNtpIsActive".equals(campo)) {
            if ("ATIVO".equals(new_value.toUpperCase())) {
                config.setUpdateNtpIsActive(true);
            } else if ("INATIVO".equals(new_value.toUpperCase())) {
                config.setUpdateNtpIsActive(false);
            } else {
                validator.add(new ValidationMessage("O campo Ativar Atualização via NTP só permite os valores ATIVO ou INATIVO", "Erro"));
            }
        }

        validator.onErrorForwardTo(ConfigController.class).configServer();
        this.configurationDAO.updateConfig(config);
        result.forwardTo(ConfigController.class).configServer();
    }

    @SuppressWarnings("static-access")
    @Get("/configClients")
    public void configClients() {

        //Sending information to "About" page
        final PropertiesLoaderImpl load = new PropertiesLoaderImpl();
        final String version = load.getValor("version");
        result.include("version", version);
        final List<String> info = getSys.SystemInformation();
        result.include("jvmName", info.get(2));
        result.include("jvmVendor", info.get(1));
        result.include("jvmVersion", info.get(0));
        result.include("osInfo", info.get(3));
        result.include("installDate", ipi.getInstallationDate());

        // Inserting HTML title in the result
        result.include("title", "Configurar Clientes");
        result.include("loggedUser", userInfo.getLoggedUsername());
        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /configClients");
        final List<Servidores> list = this.iteracoesDAO.listServers();
        result.include("server", list);
    }

    @SuppressWarnings("static-access")
    @Get("/configDataBases")
    public void configDataBases() {

        //Sending information to "About" page
        final PropertiesLoaderImpl load = new PropertiesLoaderImpl();
        final String version = load.getValor("version");
        result.include("version", version);
        final List<String> info = getSys.SystemInformation();
        result.include("jvmName", info.get(2));
        result.include("jvmVendor", info.get(1));
        result.include("jvmVersion", info.get(0));
        result.include("osInfo", info.get(3));
        result.include("installDate", ipi.getInstallationDate());

        // Inserting HTML title in the result
        result.include("title", "Configurar Banco de Dados");
        result.include("loggedUser", userInfo.getLoggedUsername());
        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /configDataBases");
        final List<BancoDados> list = this.BancoDadosDAO.listDataBases();
        result.include("dataBase", list);
    }

    @SuppressWarnings("static-access")
    @Get("/configUser")
    public void configUser() {

        //Sending information to "About" page
        final PropertiesLoaderImpl load = new PropertiesLoaderImpl();
        final String version = load.getValor("version");
        result.include("version", version);
        final List<String> info = getSys.SystemInformation();
        result.include("jvmName", info.get(2));
        result.include("jvmVendor", info.get(1));
        result.include("jvmVersion", info.get(0));
        result.include("osInfo", info.get(3));
        result.include("installDate", ipi.getInstallationDate());

        // Inserting HTML title in the result
        result.include("title", "Configurar Usuário");
        result.include("loggedUser", userInfo.getLoggedUsername());
        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /configUser");
        final List<Users> list = this.userDAO.listUser();
        result.include("user", list);
    }
}