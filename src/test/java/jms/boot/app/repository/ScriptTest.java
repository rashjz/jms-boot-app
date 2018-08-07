package jms.boot.app.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScriptTest {
    
    private static final boolean CONTINUE_ON_ERROR = true;
    private static final boolean IGNORE_FAILED_DROPS = true;
    private static final String COMMENT_PREFIX = "--";
    private static final String BLOCK_COMMENT_START_DELIMITER = "--";
    private static final String BLOCK_COMMENT_END_DELIMITER = "--";
    @Autowired
    private DataSource dataSource;

    @Test
    public void test1() throws SQLException {
      ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new EncodedResource(new ClassPathResource("schema.sql")),
                CONTINUE_ON_ERROR,
                IGNORE_FAILED_DROPS
                , COMMENT_PREFIX,
                ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
                BLOCK_COMMENT_START_DELIMITER,
                BLOCK_COMMENT_END_DELIMITER);
    }
}
