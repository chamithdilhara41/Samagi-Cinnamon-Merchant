package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static List<String> searchSupplierNmae() throws SQLException {
        String sql = "SELECT name  FROM supplier WHERE deletes = 'Active'";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            idList.add(name);
        }
        return idList;
    }

    public static List<String> nameSearch(String name) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, name);
        List<String> stringList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String name1 = resultSet.getString(2);
            String contact = resultSet.getString(4);


            stringList.add(name1);
            stringList.add(contact);

            return stringList;
        }
        return null;
    }

    public static List<String> SuplierSearchByContact(String contact) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE contact = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, contact);
        List<String> stringList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String name1 = resultSet.getString(2);
            String contact1 = resultSet.getString(4);


            stringList.add(name1);
            stringList.add(contact1);

            return stringList;
        }

        return null ;
    }

    public static List<String> searchSupplierContact() throws SQLException {
        String sql = "SELECT contact FROM supplier WHERE deletes = 'Active'";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String contact = resultSet.getString(1);
            idList.add(contact);
        }
        return idList;
    }

    public static boolean saveSupplierTemp(String name, String contact) throws SQLException {
        String sql = "INSERT INTO supplier(name,contact) VALUES(?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,name);
        pstm.setObject(2,contact);

        return pstm.executeUpdate() > 0;
    }

    public static String searchSupplierId(String contact) throws SQLException {
        String sql = "SELECT supplierId FROM supplier WHERE contact = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, contact);


        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString(1);

            return id;
        }
        return null;
    }

    public static List<String> SupplierSearchByContactforPendinngPayments(String selectedContact) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE contact = ? ";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, selectedContact);
        List<String> stringList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String id1 = resultSet.getString(1);
            String name1 = resultSet.getString(2);
            String address1 = resultSet.getString(3);
            String contact1 = resultSet.getString(4);

            stringList.add(id1);
            stringList.add(name1);
            stringList.add(address1);
            stringList.add(contact1);

            return stringList;
        }

        return null ;
    }

    public static String searchSupplierContactForPaymentInfo(String supID) throws SQLException {

        String sql = "SELECT contact FROM supplier WHERE supplierId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, supID);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("contact");
        }
        return null;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier where deletes='Active'";
        Connection connection = DbConnection.getInstance().getConnection();

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString("supplierId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("contact"),
                    resultSet.getString("deletes")
            ));
        }
        return data;
    }

    public static boolean addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier(name,address,contact,deletes) VALUES(?, ?, ?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, supplier.getName());
        pstm.setObject(2, supplier.getAddress());
        pstm.setObject(3, supplier.getContact());
        pstm.setObject(4,supplier.getStatus());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteSupplier(String supplierId) throws SQLException {

        String sql = "UPDATE supplier set deletes='Inactive' WHERE supplierId = ? ";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplierId);
        return pstm.executeUpdate() >0;
    }

    public static boolean updateSupplier(String supplierId, String name, String address, String contact) throws SQLException {

        String sql = "UPDATE supplier set name = ?, address = ?, contact = ? WHERE supplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, name);
        pstm.setObject(2, address);
        pstm.setObject(3, contact);
        pstm.setObject(4, supplierId);

        return pstm.executeUpdate() > 0;
    }
}
