package org.example.repository;

import org.apache.log4j.Logger;
import org.example.entity.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookRepository implements BookInterface<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private List<Book> srchrepp  = new ArrayList<>();
    private final NamedParameterJdbcTemplate jdbsTemplate;
    final String DELETE_ID = "delete from BOOKS where id = :id";
    final String DELETE_TITLE = "delete from BOOKS where title = :title";
    final String DELETE_AUTHOR = "delete from BOOKS where author = :author";
    final String DELETE_SIZE = "delete from BOOKS where size = :size";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbsTemplate, DataSource dataSource) {
        this.jdbsTemplate = jdbsTemplate;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbsTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) ->{
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setSize(rs.getInt("size"));
        return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> retreiveSrch() {
        return new ArrayList<>(srchrepp);
    }

    @Override
    public void store(Book book) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbsTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);

    }

    @Override
    public void removeItemByTag(String bookTagToRemove) {


        SqlParameterSource namedParameters = new MapSqlParameterSource("title", bookTagToRemove);
        int status = jdbsTemplate.update(DELETE_TITLE, namedParameters);
        if (status != 0) {
            logger.info("Employee data deleted for TITLE " + bookTagToRemove);
        } else {
            logger.info("No Employee found with TITLE " + bookTagToRemove);
        }

        SqlParameterSource secondNamedParameters = new MapSqlParameterSource("author", bookTagToRemove);
        int secondStatus = jdbsTemplate.update(DELETE_AUTHOR, secondNamedParameters);
        if (secondStatus != 0) {
            logger.info("Employee data deleted for AUTHOR " + bookTagToRemove);
        } else {
            logger.info("No Employee found with AUTHOR " + bookTagToRemove);
        }

    }

    @Override
    public void removeItemByID(Integer bookIDToRemove) {

        SqlParameterSource namedParameters = new MapSqlParameterSource("id", bookIDToRemove);
        int status = jdbsTemplate.update(DELETE_ID, namedParameters);
        if (status != 0) {
            logger.info("Employee data deleted for ID " + bookIDToRemove);

        } else {
            logger.info("No Employee found with ID " + bookIDToRemove);
        }

        SqlParameterSource secondNamedParameters = new MapSqlParameterSource("size", bookIDToRemove);
        int secondStatus = jdbsTemplate.update(DELETE_SIZE, secondNamedParameters);
        if (secondStatus != 0) {
            logger.info("Employee data deleted for SIZE " + bookIDToRemove);
        } else {
            logger.info("No Employee found with SIZE " + bookIDToRemove);
        }

    }

    @Override
    public boolean searchItem(String findbook) {
        srchrepp = new ArrayList<>();
        String sql = "select id, AUTHOR, title, size from BOOKS" +
                " where AUTHOR = ?";

            List<Map<String, Object>> firstRows = jdbcTemplate.queryForList(sql,findbook);
            for (Map row : firstRows) {
                Book book = new Book();
                book.setId(((Integer) row.get("id")));
                book.setAuthor((String) row.get("author"));
                book.setTitle((String) row.get("title"));
                book.setSize(((Integer) row.get("size")));
                srchrepp.add(book);
            }

            String secondSql = "SELECT * FROM BOOKS"+
                    " where title = ?";

            List<Map<String, Object>> secondRows = jdbcTemplate.queryForList(secondSql,findbook);

            try {
                for (Map row : secondRows) {
                    Book book = new Book();
                    book.setId(((Integer) row.get("id")));
                    book.setAuthor((String) row.get("author"));
                    book.setTitle((String) row.get("title"));
                    book.setSize(((Integer) row.get("size")));
                    srchrepp.add(book);
                }

            } catch (EmptyResultDataAccessException e2) {
                srchrepp = new ArrayList<>();
                return true;
            }

        return true;
    }

    @Override
    public void searchItemByID(Integer findbook) {
        srchrepp = new ArrayList<>();
        String sql = "select author, title, size from BOOKS" +
                " where id = ?";
        RowMapper<Book> mapper = new RowMapper<Book>() {
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setAuthor(rs.getString("author"));
                book.setTitle(rs.getString("title"));
                book.setSize(rs.getInt("size"));
                return book;
            }

        };
        try {
            Book searchResult = jdbcTemplate.queryForObject(sql, new Object[] {findbook}, mapper);
            assert searchResult != null;
            if(!searchResult.getAuthor().isEmpty()) {
                searchResult.setId(findbook);

            }
            srchrepp.add(searchResult);
        } catch (EmptyResultDataAccessException e1) {

                String secondSql = "SELECT * FROM BOOKS"+
                    " where size = ?";

                srchrepp = new ArrayList<>();

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(secondSql,findbook);

                try {
                    for (Map row : rows) {
                        Book book = new Book();
                        book.setId(((Integer) row.get("id")));
                        book.setAuthor((String) row.get("author"));
                        book.setTitle((String) row.get("title"));
                        book.setSize(((Integer) row.get("size")));
                         srchrepp.add(book);
                }

            } catch (EmptyResultDataAccessException ignored) {

                }

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {}

}
