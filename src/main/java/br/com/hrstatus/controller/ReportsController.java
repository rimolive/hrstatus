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

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.hrstatus.dao.BancoDadosInterface;
import br.com.hrstatus.dao.ServersInterface;
import br.com.hrstatus.model.BancoDados;
import br.com.hrstatus.model.Servidores;
import br.com.hrstatus.utils.UserInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Resource
public class ReportsController {

    private Logger log = Logger.getLogger(ReportsController.class.getName());

    @Autowired
    private ServersInterface iteracoesDAO;
    @Autowired
    private BancoDadosInterface bancoDadosDAO;
    @Autowired
    private HttpServletResponse response;
    private UserInfo userInfo = new UserInfo();
    private Map<String, Object> parametros = new HashMap<String, Object>();


    @SuppressWarnings("all")
    @Path("/reports/reportFull")
    public InputStream fullReport() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportFull");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportFull.jasper"));
        final List<Servidores> listServers = this.iteracoesDAO.listServers();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportFull.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportFull.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportServersOK")
    @SuppressWarnings("all")
    public InputStream serversOK() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/serversOK");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportServersOK.jasper"));
        final List<Servidores> listServers = iteracoesDAO.getServersOK();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportServersOK.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportServersOK.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportServersNOK")
    @SuppressWarnings("all")
    public InputStream reportServersNOK() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportServersNOK");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportServersNOK.jasper"));
        final List<Servidores> listServers = iteracoesDAO.getServersNOK();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportServersNOK.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportServersNOK.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Deprecated
    @Path("/reports/reportSOLinux")
    @SuppressWarnings("all")
    public InputStream soLinux() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportSOLinux");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportSOLinux.jasper"));
        final List<Servidores> listServers = iteracoesDAO.getSOUnix();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportSOLinux.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportSOLinux.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportSOWindows")
    @SuppressWarnings("all")
    public InputStream reportSOWindows() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportSOWindows");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportSOWindows.jasper"));
        final List<Servidores> listServers = iteracoesDAO.getSOWindows();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportSOWindows.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportSOWindows.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportSOUnix")
    @SuppressWarnings("all")
    public InputStream reportSOUnix() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportSOUnix");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportSOUnix.jasper"));
        final List<Servidores> listServers = iteracoesDAO.getSOUnix();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listServers, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportSOUnix.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportSOUnix.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBaseFull")
    @SuppressWarnings("all")
    public InputStream reportDataBaseFull() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBaseFull");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBaseFull.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.listDataBases();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBaseFull.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBaseFull.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBaseOK")
    @SuppressWarnings("all")
    public InputStream reportDataBaseOK() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBaseOK");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBaseOK.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.getdataBasesOK();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBaseOK.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBaseOK.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBaseNOK")
    @SuppressWarnings("all")
    public InputStream reportDataBaseNOK() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBaseNOK");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBaseNOK.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.getdataBasesNOK();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBaseNOK.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBaseNOK.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBaseMysql")
    @SuppressWarnings("all")
    public InputStream reportDataBaseMysql() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBaseMysql");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBaseMysql.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.getVendorMysql();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBaseMysql.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBaseMysql.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBaseOracle")
    @SuppressWarnings("all")
    public InputStream reportDataBaseOracle() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBaseOracle");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBaseOracle.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.getVendorOracle();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBaseOracle.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBaseOracle.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    @Path("/reports/reportDataBasePostgre")
    @SuppressWarnings("all")
    public InputStream reportDataBasePostgre() throws FileNotFoundException, JRException {

        log.info("[ " + userInfo.getLoggedUsername() + " ] URI Called: /reports/reportDataBasePostgre");
        final JasperReport jasperFile = (JasperReport) JRLoader.loadObject(ReportsController.class.getResourceAsStream("/jasper/reportDataBasePostgre.jasper"));
        final List<BancoDados> listDataBases = this.bancoDadosDAO.getVendorPostgre();
        final JasperReport jasperStream = jasperFile;
        final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listDataBases, false);

        try {
            final byte[] bytes = JasperRunManager.runReportToPdf(jasperStream, parametros, ds);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reportDataBasePostgre.pdf");
            log.info("[ " + userInfo.getLoggedUsername() + " ] Report reportDataBasePostgre.pdf successfully generated.");
            return new ByteArrayInputStream(bytes);
        } catch (JRException e) {
            log.severe(e.getMessage());
        }
        return null;
    }
}