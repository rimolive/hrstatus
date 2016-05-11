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

package br.com.hrstatus.dao.impl;

import br.com.hrstatus.dao.ServersInterface;
import br.com.hrstatus.model.Servidores;
import br.com.hrstatus.utils.UserInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Repository
@Transactional
public class ServersDAO implements ServersInterface {

    Logger log = Logger.getLogger(ServersDAO.class.getCanonicalName());

    private EntityManager entityManager;
    private UserInfo userInfo = new UserInfo();

    public ServersDAO() {
    }

    @PersistenceContext(unitName = "pu-hr")
    protected final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session() {
        return ((Session) entityManager.getDelegate());
    }

    public int insert_server(Servidores server) {

        log.info("[ " + userInfo.getLoggedUsername() + " ] insert_server(Server server)");
        log.fine("Server: " + server.getHostname());
        log.fine("IP: " + server.getIp());
        log.fine("User: " + server.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + server.getPort());
        log.fine("OS: " + server.getSO());
        log.fine("Status: " + server.getStatus());
        log.fine("Logs directory: " + server.getLogDir());
        log.fine("Su command: " + server.getSuCommand());
        log.fine("Verify? -> " + server.getVerify());

        try {

            final Criteria hostname = session().createCriteria(Servidores.class).add(Restrictions.eq("hostname",
                    new String(server.getHostname()))).setProjection(Projections.property("hostname"));

            if (hostname.uniqueResult() == null) {
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_server -> Server " + server.getHostname() + " not found.");
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_server -> Saving data");
                session().save(server);
                return 0;
            } else {
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_server -> Server " + server.getHostname() + " already exists, server not registered.");
                return 1;
            }

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] insert_server -> Error: " + e);
            return 1;
        }

    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getHostnames() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getHostnames()");

        try {

            final Criteria criteriaHostname = session().createCriteria(Servidores.class);
            final ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("id"));
            proList.add(Projections.property("hostname"));
            criteriaHostname.setProjection(proList);
            return criteriaHostname.list();

        } catch (Exception e) {
            log.severe("Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    public Servidores getServerByID(int id) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServerByID(int " + id + ")");
        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Servidores) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> listServerByID(int id) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] listServerByID(int " + id + ")");
        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.add(Restrictions.eq("id", id)).list();
    }

    public void updateServer(Servidores server) {

        log.finest("[ " + userInfo.getLoggedUsername() + " ] updateServer(Server server)[" + server.getHostname() + "]");
        log.fine("Server: " + server.getHostname());
        log.fine("IP: " + server.getIp());
        log.fine("User: " + server.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + server.getPort());
        log.fine("OS: " + server.getSO());
        log.fine("Status: " + server.getStatus());
        log.fine("Logs directory: " + server.getLogDir());
        log.fine("Su command: " + server.getSuCommand());
        log.fine("Verify? -> " + server.getVerify());

        session().update(server);

    }

    public void updateServerScheduler(Servidores server, String SchedulerName) {

        log.finest("[ " + SchedulerName + " ] updateServerScheduler(Servidores server, String SchedulerName)[" + server.getHostname() + "]");
        log.fine("Server: " + server.getHostname());
        log.fine("IP: " + server.getIp());
        log.fine("User: " + server.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + server.getPort());
        log.fine("OS: " + server.getSO());
        log.fine("Status: " + server.getStatus());
        log.fine("Logs directory: " + server.getLogDir());
        log.fine("Su command: " + server.getSuCommand());
        log.fine("Verify? -> " + server.getVerify());

        session().update(server);

    }

    public boolean deleteServerByID(Servidores server) {

        log.info("[ " + userInfo.getLoggedUsername() + " ] deleteServerByID(Servidores server)[" + server.getHostname() + "]");
        try {

            session().refresh(server);
            session().delete(server);
            log.info("[ " + userInfo.getLoggedUsername() + " ] deleteServerByID -> Server " + server.getHostname() + " deleted.");
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public int countWindows() {

        log.info("[ " + userInfo.getLoggedUsername() + " ] countWindows()");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.add(Restrictions.eq("SO", "WINDOWS"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countWindows() -> Found " + count + " servers.");
        return count;
    }

    public int countUnix() {

        log.info("[ " + userInfo.getLoggedUsername() + " ] countUnix()");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.add(Restrictions.eq("SO", "UNIX"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countUnix() -> Found " + count + " servers.");
        return count;
    }

    public int countAllServers() {

        log.info("[ " + userInfo.getLoggedUsername() + " ] countAllServers()");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countAllServers() -> Found " + count + " servers.");
        return count;
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> listServers() {

        log.info("[ " + userInfo.getLoggedUsername() + " ] listServers()");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.addOrder(Order.asc("hostname"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> listServersVerActive() {

        log.info("[ " + userInfo.getLoggedUsername() + " ] listServersVerActive()");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.add(Restrictions.eq("verify", "SIM")).list();
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> listServersVerActiveScheduler(String schedulerName) {

        log.info("[ " + schedulerName + " ] listServersVerActiveScheduler(String schedulerName)");

        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.add(Restrictions.eq("verify", "SIM")).list();
    }


    @SuppressWarnings("unchecked")
    public List<Servidores> getServersOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServersOK()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.add(Restrictions.eq("status", "OK")).list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getServersNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServersNOK()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.or(Restrictions.eq("trClass", "error"), Restrictions.eq("status", "NOK")));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getServersNOKVerActive() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServersNOKVerActive()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK")));
            criteria.add(Restrictions.eq("verify", "SIM"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<Servidores>();
        }
    }


    public int countServersOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countServersOK()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.eq("status", "OK"));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countServersNOK() {

        log.fine("Invoking countServersNOK()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countUnixOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countUnixOK()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.and(Restrictions.eq("SO", "UNIX"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countUnixOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countUnixNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countUnixNOK()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.and(Restrictions.eq("SO", "UNIX"), (Restrictions.eq("trClass", "Error"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countUnixNOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countWindowsOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countWindowsOK()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.and(Restrictions.eq("SO", "WINDOWS"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countWindowsOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countWindowsNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countWindowsOK()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.and(Restrictions.eq("SO", "WINDOWS"), (Restrictions.eq("trClass", "Error"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countWindowsNOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("Error: " + e);
            return 0;
        }
    }

    public int countOtherOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countOtherOK()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.ne("SO", "Windows"));
            criteria.add(Restrictions.ne("SO", "Unix"));
            criteria.add(Restrictions.eq("status", "OK"));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countOtherOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getSOWindows() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getSOWindows()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.add(Restrictions.eq("SO", "WINDOWS")).list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getSOUnix() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getSOUnix()");

        try {

            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return criteria.add(Restrictions.eq("SO", "UNIX")).list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Servidores> getHostnamesWithLogDir() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getHostnamesWithLogDir()");

        try {

            final Criteria getHostnamesWithLogDir = session().createCriteria(Servidores.class);
            getHostnamesWithLogDir.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            getHostnamesWithLogDir.add(Restrictions.ne("logDir", ""));
            return getHostnamesWithLogDir.list();

        } catch (Exception e) {
            log.severe("Error: " + e);
            return new ArrayList<Servidores>();
        }
    }

    public Servidores getServerByHostname(String hostname) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServerByHostname(String " + hostname + (")"));
        return (Servidores) session().createCriteria(Servidores.class).add(Restrictions.eq("hostname", hostname)).uniqueResult();
    }

    public int countServerWithLog() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countServerWithLog()");

        try {
            final Criteria criteria = session().createCriteria(Servidores.class);
            criteria.add(Restrictions.not(Restrictions.eq("logDir", "")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countServerWithLog -> found: " + count);
            return count;
        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public Servidores getServerLogDir(String hostname) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getServerLogDir(String " + hostname + ")");
        final Criteria criteria = session().createCriteria(Servidores.class);
        criteria.add(Restrictions.eq("hostname", hostname));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Servidores) criteria.uniqueResult();

    }
}