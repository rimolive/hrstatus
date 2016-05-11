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

package br.com.hrstatus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import java.util.List;

/*
 * @author spolti
 */

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "authority")
    private String authority;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "firstLogin")
    private boolean firstLogin;

    @Column(name = "lastLogin")
    private String lastLogin;

    @JsonIgnore //to avoid the loop when call a rest method to list users
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_SERVER", joinColumns = {@JoinColumn(name = "username")}, inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Servidores> server;

    @Transient
    private String confirmPass;


    public List<Servidores> getServer() {
        return server;
    }

    public void setServer(List<Servidores> server) {
        this.server = server;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        if (authority == null) {
            authority = "ROLE_USER";
        } else {
            this.authority = authority;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

}