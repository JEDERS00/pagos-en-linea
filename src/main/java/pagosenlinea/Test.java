package pagosenlinea;


import connection.Conexion;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *Miriam Gomez
 * @author JEDERS
 */
public class Test {

    private Connection connection = null;
    private Conexion conexion;

    public Test() {
        this.conexion = new Conexion();
    }

    public boolean insertTransaccion(int etapa, String uuid) throws RemoteException {
        try {
            connection = conexion.getConnection();
            String query = "INSERT INTO transaccion (etapa, uuid) VALUES (?, ?)";
            int result;
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                int row = 1;
                ps.setInt(row++, etapa);
                ps.setString(row++, uuid);
                result = ps.executeUpdate();
            }
            return 0 != result;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RemoteException(sqle.getMessage());
        } catch (RemoteException re) {
            re.printStackTrace();
            throw new RemoteException(re.getMessage());
        } finally {
            conexion.close(connection);
        }
    }

    public static void main(String[] args) {
        try {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("UUID: " + uuidString);
            System.out.println("Done: " + new Test().insertTransaccion(1, uuidString));
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
