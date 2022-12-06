package com.solution.survey.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "USERS")
public class User extends BaseEntity{

    @Column(name = "LOGIN")
    @NotNull
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        return Objects.equals(this.getId(), user.getId()) && login.equals(user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), login);
    }

    @Override
    public String toString() {
        return "AnswerOption{" + "id=" + this.getId() + ", login=" + login  + '}';
    }

}
