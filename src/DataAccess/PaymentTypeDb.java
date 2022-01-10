package DataAccess;

import Model.PaymentType;
import Utils.SQLDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentTypeDb {
    private static final ObservableList<PaymentType> paymentTypes = FXCollections.observableArrayList();

    public static void setPaymentTypes() throws SQLException {
        Statement statement = SQLDatabase.getConnection().createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM payment_type");
        while (results.next()){
            int id = results.getInt("payment_type_id");
            String name = results.getString("name");
            paymentTypes.add(new PaymentType(id, name));
        }
    }
    public static ObservableList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }
}