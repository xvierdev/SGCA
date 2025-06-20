package dao;

import java.sql.SQLException;

public class TestDao {

    public static void main(String[] args) {
        try {
            TesteCurso ts = new TesteCurso();
//            System.out.println(ts.AdicionarCurso());
            System.out.println(ts.EditarCurso());
//            System.out.println(ts.RemoverCurso());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
