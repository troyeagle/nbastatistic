package njuse.ffff.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * @author Mebleyev.G.Longinus
 *
 */
public class DatabaseUtility implements DataSourceFactory {

    private static final ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static SqlSessionFactory sqlSessionFactory;

    public static void init() {
        try {
            InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection conn() throws SQLException {
        return cpds.getConnection();
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    @Override
    public void setProperties(Properties props) {
    }

    @Override
    public DataSource getDataSource() {
        return cpds;
    }
}
