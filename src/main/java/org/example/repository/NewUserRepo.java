package org.example.repository;

import org.apache.log4j.Logger;
import org.example.entity.Book;
import org.example.entity.RegisForm;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class NewUserRepo implements NewUserInterface<RegisForm>, ApplicationContextAware {
    private final NamedParameterJdbcTemplate jdbsTemplate;
    private List<RegisForm> srchrepp  = new ArrayList<>();
    Logger logger = Logger.getLogger(NewUserRepo.class);

    @Autowired
    public NewUserRepo(NamedParameterJdbcTemplate jdbsTemplate){
        this.jdbsTemplate = jdbsTemplate;
    }
    @Override
    public void store(RegisForm regisForm) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", regisForm.getUsername_reg());
        parameterSource.addValue("password", "{noop}"+regisForm.getPassword_first());
        parameterSource.addValue("enabled", true);
        jdbsTemplate.update("INSERT INTO users(username,password,enabled) VALUES(:username,:password,:enabled)", parameterSource);
        logger.info("add new user: " + regisForm.getUsername_reg());

    }

    @Override
    public List<RegisForm> retreiveAll() {
        List<RegisForm> regisForms = jdbsTemplate.query("SELECT * FROM users", (ResultSet rs, int rowNum) ->{
            RegisForm regisForm = new RegisForm();
            regisForm.setUsername_reg(rs.getString("username"));
            regisForm.setPassword_first(rs.getString("password"));
            return regisForm;
        });
        return new ArrayList<>(regisForms);
    }

    @Override
    public void setAuth(RegisForm auth) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", auth.getUsername_reg());
        parameterSource.addValue("authority", "ROLE_USER");
        jdbsTemplate.update("INSERT INTO authorities(username,authority) VALUES(:username,:authority)", parameterSource);
        logger.info("add new user: " + auth.getUsername_reg());
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {}
}
