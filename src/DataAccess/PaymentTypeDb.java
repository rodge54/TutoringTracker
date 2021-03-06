package DataAccess;

import Model.PaymentType;
import Model.Subject;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentTypeDb extends AllDb{
    private static final ObservableList<PaymentType> paymentTypes = FXCollections.observableArrayList();

    public static void setPaymentTypes() throws SQLException {
        Statement statement = SQLDatabase.getConnection().createStatement();

//        ResultSet results = statement.executeQuery("SELECT * FROM "+schema+"payment_type");
        ResultSet results = statement.executeQuery("SELECT TOP (1000) * FROM [tutoring].[payment_type]");
        while (results.next()){
            int id = results.getInt("payment_type_id");
            String name = results.getString("name");
            paymentTypes.add(new PaymentType(id, name));
        }
    }
    public static ObservableList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public static PaymentType getPaymentTypeById(int paymentTypeId) throws SQLException {
        String query = "SELECT * FROM " + schema + "payment_type " +
                "WHERE payment_type_id = "+paymentTypeId+";";
        ResultSet rs = null;
        try {
            Statement statement = SQLDatabase.getConnection().createStatement();
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        PaymentType paymentType = null;
        while (rs.next()) {
            int id = rs.getInt("payment_type_id");
            String name = rs.getString("name");
            paymentType = new PaymentType(id, name);
        }
        return paymentType;
    }
}
