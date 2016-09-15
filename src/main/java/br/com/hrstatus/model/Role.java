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

import br.com.hrstatus.model.support.SupportedDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:spoltin@hrstatus.com.br">Filippe Spolti</a>
 */
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique=false)
    private String username;

    @Column(name = "role", nullable = false, unique=false)
    private String role;

    public void setUsername(String username) {
        this.username = username;
    }

    public void addRole(String role) {
        this.role = "ROLE_" + role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

}