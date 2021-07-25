package com.example.demo;

import com.hw.demo.bean.Student;
import com.hw.demo.services.ISchool;
import com.hw.demo.services.impl.NettyServer;
import com.hw.demo.services.impl.ProcessServer;
import com.hw.demo.services.impl.SchoolConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@SpringBootApplication(scanBasePackages = "com.hw.demo.*")
@ContextConfiguration(locations = {"classpath:spring.xml"})
class DemoApplicationTests {

    @Resource(name = "school")
    private ISchool iSchool;

    @Autowired
    @Qualifier(value = "school")
    private ISchool iSchool2;

    @Autowired
    private JdbcHelper jdbcHelper;

    @Autowired
    private ProcessServer processServer;

    @Autowired
    private NettyServer nettyServer;

    /**
     * 必做2:装配注入
     */
    @Test
    void iocTest() {
        iSchool.offer();//按名称注入

        iSchool2.offer();//按类型注入

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");//xml读取注入
        ISchool iSchool3 = (ISchool) ctx.getBean("school3");
        iSchool3.offer();
    }

    /**
     * 必做10:jdbc测试
     * 选做5:单例方法
     */
    @Test
    void jdbcTest() {
        final String url = "jdbc:mysql://localhost:3306/redoordb?user=root&password=123456&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";

        //原生jdbc crud
        String delSql = "delete from user where name = 's1001'";
        int delRow = JdbcHelper.getInstanceDl().executeConnectionAndCom(url, delSql);//双锁
        System.out.println("del result:" + delRow);


        String addSql = "insert into user values (1,'s1001','s1001')";
        int addRow = JdbcHelper.getInstanceLazy().executeConnectionAndCom(url, addSql);//懒加载
        System.out.println("add result:" + addRow);

        String editSql = "update user set pwd = 's222' where name = 's1001'";
        int editRow = DemoApplicationTests.getInnerStaticInstance().executeConnectionAndCom(url, editSql);//静态内部类
        System.out.println("edit result:" + editRow);

        String querySql = "select * from user";
        String result = JdbcHelper.getInstanceHg().getConnectionAndResult(url, querySql);//饿汉
        System.out.println("query result:" + result);

        List<Student> students = new ArrayList<>();
        for (int k = 1; k < 11; k++) {
            students.add(new Student(k, "s1001", "******" + k));
        }

        //批处理
        int batchAddRow = DemoApplicationTests.getInnerStaticInstance().executeBatch(url, students, "ADD");
        System.out.println("batch add result:" + batchAddRow);

        //hikari链接池使用
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JdbcHelper.class);
        Connection hikariDataSource = (Connection) ctx.getBean("hikariDataSource");

        String hirResult = jdbcHelper.getConnectionAndResult4Hikari(querySql, hikariDataSource);
        System.out.println("query hirResult:" + hirResult);
    }

    /**
     * 必做8:自动配置starter
     */
    @Test
    void configTest() {
        String result = processServer.toPrintConfig();
        System.out.println("result:" + result);
    }

    /**
     * 挑战4.2:简单构建filter aop实现方式
     */
    @Test
    void aopTest(){
        nettyServer.nettyServicerHandler();
    }

    /**
     * 静态内部类单例（优点：调用效率高，可延时加载）
     *
     * @return
     */
    public static JdbcHelper getInnerStaticInstance() {
        return JdbcHelper.instanceInnerStatic;
    }

    static class DbBean<T> {
        private T obj;

        private List<T> list;

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DbBean{");
            sb.append("obj=").append(obj);
            sb.append(", list=").append(list);
            sb.append('}');
            return sb.toString();
        }
    }

    @Configuration
    static class JdbcHelper {

        private static JdbcHelper instanceHg = new JdbcHelper();

        private volatile static JdbcHelper instance;

        private static final JdbcHelper instanceInnerStatic = new JdbcHelper();

        public JdbcHelper() {
            /*私有化构造器更好些，这里为了@Configuration复用先不使用private使其正常初始化*/
        }

        @Bean(name = "hikariDataSource")
        public Connection hikariDataSource() throws SQLException {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/redoordb?user=root&password=123456&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
            config.setUsername("root");
            config.setPassword("123456");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            return new HikariDataSource(config).getConnection();
        }

        /**
         * 饿汉式（优点：无加载延迟 缺点：资源浪费）
         *
         * @return
         */
        public static JdbcHelper getInstanceHg() {
            return instanceHg;
        }

        /**
         * 懒汉式（优点：真正使用时才创建，可加载延迟 缺点：调用效率低）
         *
         * @return
         */
        public synchronized static JdbcHelper getInstanceLazy() {
            if (null == instance) {
                return new JdbcHelper();
            }

            return instance;
        }

        /**
         * 双锁锁对象本身(优点：线程安全 缺点：调用效率低)
         *
         * @return
         */
        public static JdbcHelper getInstanceDl() {
            if (null == instance) {
                synchronized (JdbcHelper.class) {
                    if (null == instance) {
                        instance = new JdbcHelper();
                    }
                }
            }
            return instance;
        }


        private int executeBatch(String url, List<Student> students/*DbBean<?> oriBean这里用泛型反射更具有通用性*/, String opFlag) {
            if (StringUtils.equals("ADD", opFlag)) {
                if (!CollectionUtils.isEmpty(students)) {
                    String sql = "insert into user values(?,?,?)";
                    Connection conn = null;
                    PreparedStatement preparedStatement = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
                        conn = DriverManager.getConnection(url);//获取数据库连接
                        conn.setAutoCommit(false);//设置不自动提交事务
                        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//隔离级别:读已提交

                        preparedStatement = conn.prepareStatement(sql);
                        for (Student student : students) {
                            preparedStatement.setInt(1, student.getId());
                            preparedStatement.setString(2, student.getName());
                            preparedStatement.setString(3, student.getPwd());
                            preparedStatement.addBatch();
                        }

                        int[] rows = preparedStatement.executeBatch();//批执行

                        conn.commit();//事务提交

                        return rows.length;
                    } catch (Exception e) {

                        try {
                            if (conn != null)
                                conn.rollback();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        e.printStackTrace();
                    } finally {
                        close(conn, preparedStatement);
                    }


                }
                return 0;
            }
            throw new RuntimeException("暂时只支持新增的批处理！");
        }

        private int executeConnectionAndCom(String url, String sql) {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
                conn = DriverManager.getConnection(url);//获取数据库连接
                stmt = conn.createStatement();//创建执行环境
                stmt.execute(sql);//执行SQL语句
                return stmt.getUpdateCount();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, stmt);
            }
            return 0;
        }

        private String getConnectionAndResult(String url, String sql) {
            StringBuilder builder = new StringBuilder();

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
                conn = DriverManager.getConnection(url);//获取数据库连接
                stmt = conn.createStatement();//创建执行环境
                stmt.execute(sql);
                rs = stmt.executeQuery(sql);//执行查询语句，返回结果数据集
                rs.last();
                rs.beforeFirst();
                while (rs.next()) { // 循环读取结果数据集中的所有记录
                    builder.append(rs.getString("name")).append("|");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, stmt, rs);
            }
            return builder.toString();
        }

        private String getConnectionAndResult4Hikari(String sql, Connection hikariDataSource) {
            StringBuilder builder = new StringBuilder();

            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
                stmt = hikariDataSource.createStatement();//创建执行环境
                stmt.execute(sql);
                rs = stmt.executeQuery(sql);//执行查询语句，返回结果数据集
                rs.last();
                rs.beforeFirst();
                while (rs.next()) {
                    builder.append(rs.getString("name")).append("|");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(stmt, rs);
            }
            return builder.toString();
        }

        private void close(Statement stmt, ResultSet rs) {
            try {
                if (stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void close(Connection conn, Statement stmt) {
            try {
                if (conn != null)
                    conn.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void close(Connection conn, Statement stmt, ResultSet rs) {
            try {
                close(conn, stmt);
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
