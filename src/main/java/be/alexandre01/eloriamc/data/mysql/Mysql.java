package be.alexandre01.eloriamc.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Mysql {

    public static void update(String qry) {
        try { Connection c = DatabaseManager.api.getDatabaseAccess().getConnection();
            PreparedStatement s = c.prepareStatement(qry);
            s.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Object query(String qry, Function<ResultSet, Object> consumer) {
        try { Connection c = DatabaseManager.api.getDatabaseAccess().getConnection();
            PreparedStatement s = c.prepareStatement(qry);
            ResultSet rs = s.executeQuery();
            return consumer.apply(rs);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static void query(String qry, Consumer<ResultSet> consumer){
        try ( Connection c = DatabaseManager.api.getDatabaseAccess().getConnection();
              PreparedStatement s = c.prepareStatement(qry);
              ResultSet rs = s.executeQuery()) {
            consumer.accept(rs);
            c.close();
        } catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }
}
