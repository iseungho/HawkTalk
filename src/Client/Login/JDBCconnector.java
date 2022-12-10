package Client.Login;

import java.sql.*;
import java.util.*;

public class JDBCconnector {
    public Connection conn;
    public Statement stmt = null;
    public ResultSet srs = null;

    public JDBCconnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usersDB", "root", "admin");
            System.out.println("DB 연결 성공");
            stmt = conn.createStatement(); // SQL문 처리용 Statement 객체 생성
            srs = stmt.executeQuery("select * from usertable;");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e) {
            System.out.println("DB 연결 에러");
        }
    }

    // 레코드의 각 열의 값 화면에 출력
    public void printTable(Statement stmt) throws SQLException {
        ResultSet srs = stmt.executeQuery("select * from usertable");
        while (srs.next()) {
            System.out.print(srs.getString("Name"));
            System.out.print("\t|\t" + srs.getString("ClassNumber"));
            System.out.print("\t|\t" + srs.getString("NickName"));
            System.out.print("\t|\t" + srs.getString("ID"));
            System.out.println("\t|\t" + srs.getString("Pwd"));
        }
    }

    private StringBuilder sb;
    private String sql;

    public int isValid(Statement stmt, List<String> userData) throws SQLException {
        // srs = stmt.executeQuery("select * from usertable"); // 테이블의 모든 데이터 검색
        sb = new StringBuilder();
        sql = sb.append("select * from usertable where Name='").append(userData.get(0))
                .append("' AND ClassNumber='").append(userData.get(1).toUpperCase()).append("';").toString();
        if (stmt.executeQuery(sql).next()) return 1;

        sb = new StringBuilder();
        sql = sb.append("select * from usertable where NickName='").append(userData.get(2)).append("';").toString();
        if (stmt.executeQuery(sql).next()) return 2;

        sb = new StringBuilder();
        sql = sb.append("select * from usertable where ID='").append(userData.get(3)).append("';").toString();
        if (stmt.executeQuery(sql).next()) return 3;

        return 0;
    }

    public static void main(String[] args) {
        new JDBCconnector();
    }
}