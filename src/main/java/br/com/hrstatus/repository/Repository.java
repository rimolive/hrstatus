package br.com.hrstatus.repository;

import br.com.hrstatus.model.Setup;
import br.com.hrstatus.model.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by fspolti on 9/9/16.
 */
public interface Repository {

    /*
    * Import the initial configuration in the database if it is a fresh database.
    */
    void initialImport();

    /*
    * load all configurations
    */
    Setup loadConfiguration();

    /*
    * Return the mail jndi
    */
    String mailJndi();

    /*
    * Return the mail from
    */
    String mailFrom();

    /*
    * Get the welcome message, it will be displayed in the login page
    */
    String welcomeMessage();

    /*
    * Get the installation date
    */
    LocalDateTime installationDate();

    /*
    * Persists the given Object in the database
    */
    <T, O> T register (O object);

    /*
    * Delete the given object
    * @param Object
    */
    <T, Object> void delete(Object object);

    /*
    * List all persisted objects on the database
    */
    <T, Clazz> List<T> list(Clazz clazz);

    /*
    * Search objects based on a query parameter
    */
    <T, Clazz> T search(Clazz clazz, String parameterName, Object parameterValue);

    /*
    * Update the given Object
    */
    Object update(Object object);

    /*
    * Get the locked users
    */
    List<User> getLockedUsers();

}