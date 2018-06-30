package jms.boot.app.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScriptTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void test1() throws SQLException {
        Connection connection = dataSource.getConnection();
        assertNotNull(connection);
        new PrepareStatementSql().executeSqlScript(connection, new File("sql/schema.sql"));

        connection.createStatement().execute("INSERT INTO ORDERS VALUES (1,'1','1',1);");
        connection.createStatement().execute("INSERT INTO ORDERS VALUES (2,'1','1',1);");
        ResultSet resultSet = connection.createStatement().executeQuery("select count(*) FROM ORDERS");

        resultSet.next();
        int rowCount = resultSet.getInt(1);
        System.out.println(rowCount);


        Assert.assertEquals(2, rowCount);
        resultSet.close();


    }
}
