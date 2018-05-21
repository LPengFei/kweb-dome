package kbase;

import org.junit.After;
import org.junit.BeforeClass;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * @author ilgqh
 * JFinal的Model测试用例
 *
 */
public class JFinalModelCase  {

    protected static DruidPlugin dp;
    protected static ActiveRecordPlugin activeRecord;

    /**
     * 数据连接地址
     */
    private static final String  URL = "jdbc:mysql://192.168.31.3/kbase?useUnicode=true&characterEncoding=UTF-8";

    /**
     * 数据库账号
     */
    private static final String  USERNAME = "root";

    /**
     * 数据库密码
     */
    private static final String  PASSWORD = "cnksi.com";

    /**
     * 数据库驱动
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 数据库类型（如mysql，oracle）
     */
    private static final String DATABASE_TYPE = "mysql";


    /**
     * @throws java.lang.Exception
     */
    public static void main(String[] args)  {
        dp=new DruidPlugin(URL, USERNAME,PASSWORD,DRIVER);

        dp.addFilter(new StatFilter());

        dp.setInitialSize(1);
        dp.setMinIdle(1);
        dp.setMaxActive(1);
        dp.setMaxWait(60000);
        dp.setTimeBetweenEvictionRunsMillis(120000);
        dp.setMinEvictableIdleTimeMillis(120000);

        WallFilter wall = new WallFilter();
        wall.setDbType(DATABASE_TYPE);
        dp.addFilter(wall);

        dp.getDataSource();
        dp.start();

        activeRecord = new ActiveRecordPlugin(dp);
        activeRecord.setDialect(new MysqlDialect()).setDevMode(true).setShowSql(true); //是否打印sql语句


        //映射数据库的表和继承与model的实体
        //只有做完该映射后，才能进行junit测试
        activeRecord.addMapping("king_project","proid", Project.class);

        activeRecord.start();

        Project p = new Project();
        p.setEnabled(true);

        p.save();


        activeRecord.stop();
        dp.stop();


    }


}