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

import br.com.hrstatus.dao.BancoDadosInterface;
import br.com.hrstatus.model.BancoDados;
import br.com.hrstatus.utils.UserInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
public class BancoDadosDAO implements BancoDadosInterface {

    Logger log = Logger.getLogger(BancoDadosDAO.class.getCanonicalName());

    private EntityManager entityManager;
    private UserInfo userInfo = new UserInfo();

    public BancoDadosDAO() {
    }

    @PersistenceContext(unitName = "pu-hr")
    protected final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session() {
        return ((Session) entityManager.getDelegate());
    }

    public int insert_dataBase(BancoDados dataBase) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] insert_dataBase(BancoDados dataBase)[" + dataBase.getHostname() + "]");

        log.fine("Server: " + dataBase.getHostname());
        log.fine("IP: " + dataBase.getIp());
        log.fine("User: " + dataBase.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + dataBase.getPort());
        log.fine("VENDOR: " + dataBase.getVendor());
        log.fine("Query: " + dataBase.getQueryDate());

        try {

            final Criteria hostname = session().createCriteria(BancoDados.class).add(Restrictions.eq("hostname",
                    new String(dataBase.getHostname()))).setProjection(Projections.property("hostname"));

            if (hostname.uniqueResult() == null) {
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_dataBase -> Database server " + dataBase.getHostname() + " not found.");
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_dataBase -> Saving data");
                session().save(dataBase);
                return 0;
            } else {
                log.info("[ " + userInfo.getLoggedUsername() + " ] insert_dataBase -> Database server " + dataBase.getHostname() + " already exists, ignoring.");
                return 1;
            }

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] insert_dataBase -> Errorr: " + e);
            return 1;
        }

    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> listDataBases() {
        log.fine("[ " + userInfo.getLoggedUsername() + " ] listDataBases()");
        return session().createCriteria(BancoDados.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> listDataBasesScheduler(String schedulerName) {
        log.fine("[ " + schedulerName + " ] listDataBasesScheduler()");
        return session().createCriteria(BancoDados.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> listDataBaseByID(int id) {
        log.fine("[ " + userInfo.getLoggedUsername() + " ] getDataBaseByID -> DataBase ID selected: " + id);
        return session().createCriteria(BancoDados.class).add(Restrictions.eq("id", id)).list();
    }

    public BancoDados getDataBaseByID(int id) {
        log.fine("[ " + userInfo.getLoggedUsername() + " ] getDataBaseByID -> DataBase ID selected: " + id);
        return (BancoDados) session().createCriteria(BancoDados.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public boolean deleteDataBase(BancoDados bancoDados) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] deleteDataBase(BancoDados bancoDados)[" + bancoDados.getHostname() + "]");

        try {
            session().refresh(bancoDados);
            session().delete(bancoDados);
            log.info("[ " + userInfo.getLoggedUsername() + " ] deleteDataBaseByID -> Data Base " + bancoDados.getHostname() + " deleted.");
            return true;
        } catch (Exception e) {
            log.info("[ " + userInfo.getLoggedUsername() + " ] deleteDataBaseByID -> Data Base " + bancoDados.getHostname() + " Delete Operation failed.");
            return false;
        }
    }

    public void updateDataBase(BancoDados dataBase) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] updateDataBase(BancoDados dataBase)[" + dataBase.getHostname() + "]");

        log.info("[ " + userInfo.getLoggedUsername() + " ] updateDataBase -> Retrieving parameters");
        log.fine("Server: " + dataBase.getHostname());
        log.fine("IP: " + dataBase.getIp());
        log.fine("User: " + dataBase.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + dataBase.getPort());
        log.fine("VENDOR: " + dataBase.getVendor());
        log.fine("Query: " + dataBase.getQueryDate());

        session().update(dataBase);
    }

    public void updateDataBaseScheduler(BancoDados dataBase, String schedulerName) {

        log.fine("[ " + schedulerName + " ] updateDataBaseScheduler(BancoDados dataBase, String schedulerName)[" + dataBase.getHostname() + "]");

        log.info("[ " + schedulerName + " ] updateDataBase -> Retrieving parameters");
        log.fine("Server: " + dataBase.getHostname());
        log.fine("IP: " + dataBase.getIp());
        log.fine("User: " + dataBase.getUser());
        log.fine("Pass: gotcha!");
        log.fine("Port: " + dataBase.getPort());
        log.fine("VENDOR: " + dataBase.getVendor());
        log.fine("Query: " + dataBase.getQueryDate());

        session().update(dataBase);
    }

    public int countMysql() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countMysql()");

        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.add(Restrictions.eq("vendor", "MYSQL"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countMysql() -> Found " + count + " Mysql Databases.");
        return count;
    }

    public int countOracle() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracle()");

        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.add(Restrictions.eq("vendor", "ORACLE"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracle() -> Found " + count + " Oracle Databases..");
        return count;
    }

    public int countPostgre() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgre()");

        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.add(Restrictions.eq("vendor", "POSTGRESQL"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgre() -> Found " + count + " Postgre Databases.");
        return count;
    }

    public int countSqlServer() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServer()");

        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.add(Restrictions.eq("vendor", "SQLSERVER"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServer() -> Found " + count + " SqlServer Databases.");
        return count;
    }

    public int countDB2() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2()");

        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.add(Restrictions.eq("vendor", "DB2"));
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2() -> Found " + count + " DB2 Databases.");
        return count;
    }

    public int countAllDataBases() {
        final Criteria criteria = session().createCriteria(BancoDados.class);
        criteria.setProjection(Projections.rowCount());
        final int count = ((Long) criteria.uniqueResult()).intValue();
        log.fine("[ " + userInfo.getLoggedUsername() + " ] countAllDataBases() -> Found " + count + " Databases.");
        return count;
    }

    public int countDataBasesOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDataBasesOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.eq("status", "OK"));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] Data Bases OK: " + count);
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countDataBasesNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDataBasesNOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] Data Bases not NOK: " + count);
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countMySQLOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countMySQLOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "MYSQL"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countMySQLOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countMySQLNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countMySQLNOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "MYSQL"),
                    Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countMySQLNOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countOracleOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracleOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "ORACLE"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracleOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countOracleNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracleNOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "ORACLE"),
                    Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countOracleNOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countPostgreOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgreOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "POSTGRESQL"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgreOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countPostgreNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgreNOK");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "POSTGRESQL"),
                    Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countPostgreNOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("Error: " + e);
            return 0;
        }
    }

    public int countSqlServerOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServerOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "SQLSERVER"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServerOK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countSqlServerNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServerNOK");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "SQLSERVER"),
                    Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countSqlServerNOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("Error: " + e);
            return 0;
        }
    }

    public int countDB2OK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2OK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "DB2"), Restrictions.eq("status", "OK")));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2OK -> " + count + " found.");
            return count;

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return 0;
        }
    }

    public int countDB2NOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2NOK");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.and(Restrictions.eq("vendor", "DB2"),
                    Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK"))));
            criteria.setProjection(Projections.rowCount());
            final int count = ((Long) criteria.uniqueResult()).intValue();
            log.fine("[ " + userInfo.getLoggedUsername() + " ] countDB2NOK() -> " + count + " found.");
            return count;

        } catch (Exception e) {
            log.severe("Error: " + e);
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> getdataBasesOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getdataBasesOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            return criteria.add(Restrictions.eq("status", "OK")).list();

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<BancoDados>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> getdataBasesNOK() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getdataBasesNOK()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            criteria.add(Restrictions.or(Restrictions.eq("trClass", "Error"), Restrictions.eq("status", "NOK")));
            return criteria.list();

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<BancoDados>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> getVendorMysql() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getvendorMysql()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            return criteria.add(Restrictions.eq("vendor", "MYSQL")).list();

        } catch (Exception e) {
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<BancoDados>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> getVendorOracle() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getVendorOracle()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            return criteria.add(Restrictions.eq("vendor", "ORACLE")).list();

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<BancoDados>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<BancoDados> getVendorPostgre() {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getVendorPostgre()");

        try {
            final Criteria criteria = session().createCriteria(BancoDados.class);
            return criteria.add(Restrictions.eq("vendor", "POSTGRESQL")).list();

        } catch (Exception e) {
            System.out.println(e);
            log.severe("[ " + userInfo.getLoggedUsername() + " ] Error: " + e);
            return new ArrayList<BancoDados>();
        }
    }
}