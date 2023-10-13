package edu.scnu.wiki;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
class WikiApplicationTests {

    @Test
    public void test01(){



            String str1 = "select SQL_CALC_FOUND_ROWS * from ebook \r\n"
                    ;
            String str2 = "SELECT *from doc";
//		String str3 = "select * from config;";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String dbURL = "jdbc:mysql://rm-7xv9h5685p98j9642mo.mysql.rds.aliyuncs.com:3306/wikidev?allowMultiQueries=true";
                String dbuser = "wikidev";
                String dbpassword = "ambitionZCL0";

                Connection theConnection = (Connection) DriverManager.getConnection(dbURL, dbuser, dbpassword);

                Statement theStatement = theConnection.createStatement();
                boolean hasMoreResultSets = theStatement.execute(str1 + str2);
                ResultSet theResult;
                int count = 0;
                while (hasMoreResultSets) { // 通过next来索引：判断是否有下一个记录

                    theResult = theStatement.getResultSet();
                    while (theResult.next()) {
                        if (count == 1) {
                            System.out.println(theResult.getString(1));
                            continue;
                        }
                        long id = theResult.getLong(1); // 方法：int java.sql.ResultSet.getInt(int columnIndex) throws
                        // SQLException
                        String name = theResult.getString(2);
                        String description = theResult.getString(3);
                        System.out.println("id=" + id + ",name=" + name + ",description=" + description);
                    }
                    count++;

                    hasMoreResultSets = theStatement.getMoreResults();
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
    }
}
