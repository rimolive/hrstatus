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

import br.com.hrstatus.dao.LockInterface;
import br.com.hrstatus.model.Lock;
import br.com.hrstatus.utils.UserInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

/*
 * @author spolti
 */

@Repository
@Transactional
public class LockDAO implements LockInterface {

    Logger log = Logger.getLogger(LockDAO.class.getCanonicalName());

    private EntityManager entityManager;
    private UserInfo userInfo = new UserInfo();

    public LockDAO() {
    }

    @PersistenceContext(unitName = "pu-hr")
    protected final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session() {
        return ((Session) entityManager.getDelegate());
    }

    public void insertLock(Lock lock) {

        log.info("[ " + userInfo.getLoggedUsername() + " ] Generating lock to resource " + lock.getRecurso() + " requested by the user " + lock.getUsername());
        session().save(lock);
    }

    public void insertLockScheduler(Lock lock, String schedulerName) {

        log.info("[ " + schedulerName + " ] Generating lock to resource " + lock.getRecurso() + " requested by the user " + lock.getUsername());
        session().save(lock);
    }

    public void removeLock(Lock lock) {

        log.info("[ " + userInfo.getLoggedUsername() + " ] Removing the lock for tor the resource  " + lock.getRecurso() + " requested by the user " + lock.getUsername());
        session().refresh(lock);
        session().delete(lock);
    }

    public void removeLockScheduler(Lock lock, String schedulerName) {

        log.info("[ " + schedulerName + " ] Removing the lock for tor the resource  " + lock.getRecurso() + " requested by the user " + lock.getUsername());
        session().refresh(lock);
        session().delete(lock);
    }

    @SuppressWarnings("unchecked")
    public List<Lock> listLockedServices(String recurso) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] Verifying if any resource is locked.");
        final Criteria criteria = session().createCriteria(Lock.class);
        criteria.add(Restrictions.eq("recurso", recurso));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Lock> listLockedServicesScheduler(String recurso, String schedulerName) {

        log.fine("[ " + schedulerName + " ] Verifying if any resource is locked.");
        final Criteria criteria = session().createCriteria(Lock.class);
        criteria.add(Restrictions.eq("recurso", recurso));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Lock> ListAllLocks() {

        log.fine("ListAllLocks().");
        return session().createCriteria(Lock.class).list();
    }

    public Lock getLockByID(int id) {

        log.fine("[ " + userInfo.getLoggedUsername() + " ] getLockByID(int " + id + ")");
        final Lock lock = (Lock) session().createCriteria(Lock.class).add(Restrictions.eq("id", id)).uniqueResult();
        if (lock != null) {
            return lock;
        } else {
            log.severe("Lock ID " + id + " not found.");
            return lock;
        }
    }

}