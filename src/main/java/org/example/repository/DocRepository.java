package org.example.repository;

import org.apache.log4j.Logger;
import org.example.entity.Doc;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

@Repository
public class DocRepository extends Component implements DocInterface<Doc>, ApplicationContextAware {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbsTemplate;
    private final Logger logger = Logger.getLogger(BookRepository.class);

    @Autowired
    public DocRepository(NamedParameterJdbcTemplate jdbsTemplate, DataSource dataSource) {
        this.jdbsTemplate = jdbsTemplate;
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }




    @Override
    public void saveFile(File file) {
        try (InputStream imageInStream = new FileInputStream(file)) {
            DefaultLobHandler lobHandler = new DefaultLobHandler();
            jdbcTemplate.execute(
                    "INSERT INTO docs ( fileName, file) VALUES ( ?, ?)",
                    new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
                        @Override
                        protected void setValues(PreparedStatement ps, org.springframework.jdbc.support.lob.LobCreator lobCreator) throws SQLException, DataAccessException {
                            ps.setString(1, file.getName());
                            lobCreator.setBlobAsBinaryStream(ps, 2, imageInStream, (int) file.length());
                        }
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doc> retreiveAll() {
        List<Doc> docs = jdbsTemplate.query("SELECT * FROM docs", (ResultSet rs, int rowNum) ->{
            Doc doc = new Doc();
            doc.setId(rs.getInt("id"));
            doc.setFileName(rs.getString("fileName"));
            doc.setFile(rs.getBytes("file"));
            logger.info("docs contain: " + doc.getFileName());
            return doc;
        });

        return new ArrayList<>(docs);
    }

    @Override
    public void findById(Integer docId) {
        List<Doc> docs = jdbsTemplate.query("SELECT * FROM docs", (ResultSet rs, int rowNum) ->{
            Doc doc = new Doc();
            doc.setId(rs.getInt("id"));
            if(doc.getId().equals(docId)){
                doc.setFile(rs.getBytes("file"));
                doc.setFileName(rs.getString("fileName"));
                byte[] bytes = doc.getFile();
                String name = doc.getFileName();

                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                dialog.add(jfc);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(false);
                int returnValue = jfc.showSaveDialog(dialog);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isDirectory()) {

                       logger.info("You selected the directory: " + jfc.getSelectedFile());
                    }
                }

                File dir = new File(String.valueOf(jfc.getSelectedFile()));
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + htmlUnescape(name));
                try {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return doc;
        });

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {}

}
