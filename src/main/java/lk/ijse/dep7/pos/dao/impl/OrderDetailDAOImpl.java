package lk.ijse.dep7.pos.dao.impl;

import lk.ijse.dep7.pos.entity.OrderDetail;
import lk.ijse.dep7.pos.entity.OrderDetailPK;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDAOImpl {

    private final Connection connection;

    public OrderDetailDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void saveOrderDetail(OrderDetail orderDetail) throws Exception {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO order_detail VALUES (?,?,?,?)");
        stm.setString(1, orderDetail.getOrderDetailPK().getOrderId());
        stm.setString(2, orderDetail.getOrderDetailPK().getItemCode());
        stm.setBigDecimal(3, orderDetail.getUnitPrice());
        stm.setInt(4, orderDetail.getQty());
        stm.executeUpdate();
    }

    public void updateOrderDetail(OrderDetail orderDetail) throws Exception {
        PreparedStatement stm = connection.prepareStatement("UPDATE order_detail SET unit_price=?, qty=? WHERE order_id = ? AND item_code =?");
        stm.setBigDecimal(1, orderDetail.getUnitPrice());
        stm.setInt(2, orderDetail.getQty());
        stm.setString(3, orderDetail.getOrderDetailPK().getOrderId());
        stm.setString(4, orderDetail.getOrderDetailPK().getItemCode());
        stm.executeUpdate();
    }

    public void deleteOrderDetailByPK(OrderDetailPK orderDetailPK) throws Exception {
        PreparedStatement stm = connection.prepareStatement("DELETE FROM order_detail WHERE order_id = ? AND item_code =?");
        stm.setString(1, orderDetailPK.getOrderId());
        stm.setString(2, orderDetailPK.getItemCode());
        stm.executeUpdate();
    }

    public Optional<OrderDetail> findOrderDetailByPK(OrderDetailPK orderDetailPK) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM order_detail WHERE order_id = ? AND item_code =?");
        stm.setString(1, orderDetailPK.getOrderId());
        stm.setString(2, orderDetailPK.getItemCode());
        ResultSet rst = stm.executeQuery();

        return (rst.next()) ? Optional.of(new OrderDetail(rst.getString("order_id"),
                rst.getString("item_code"),
                rst.getBigDecimal("unit_price"),
                rst.getInt("qty"))) : Optional.empty();
    }

    public List<OrderDetail> findAllOrderDetails() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM order_detail");
        ResultSet rst = stm.executeQuery();
        List<OrderDetail> orderDetails = new ArrayList<>();

        while (rst.next()) {
            orderDetails.add(new OrderDetail(rst.getString("order_id"),
                    rst.getString("item_code"),
                    rst.getBigDecimal("unit_price"),
                    rst.getInt("qty")));
        }
        return orderDetails;
    }

    public long countOrderDetails() throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM order_detail");
        ResultSet rst = stm.executeQuery();
        rst.next();
        return rst.getLong(1);
    }

    public boolean existsOrderDetailByPK(OrderDetailPK orderDetailPK) throws Exception {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM order_detail WHERE order_id = ? AND item_code =?");
        stm.setString(1, orderDetailPK.getOrderId());
        stm.setString(2, orderDetailPK.getItemCode());
        ResultSet rst = stm.executeQuery();
        return rst.next();
    }

    public Optional<BigDecimal> findOrderTotal(String orderId) throws Exception{
        PreparedStatement stm = connection.
                prepareStatement("SELECT order_id, SUM(unit_price * qty) as total FROM order_detail WHERE order_id=? GROUP BY order_id;");
        stm.setString(1, orderId);
        ResultSet rst = stm.executeQuery();
        return rst.next()? Optional.of(rst.getBigDecimal("total")): Optional.empty();
    }

    public List<OrderDetail> findOrderDetailsByOrderId(String orderId) throws Exception{
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM `order_detail` WHERE order_id =?");
        stm.setString(1, orderId);
        ResultSet rst = stm.executeQuery();
        List<OrderDetail> orderDetails = new ArrayList<>();

        while (rst.next()) {
            orderDetails.add(new OrderDetail(rst.getString("order_id"),
                    rst.getString("item_code"),
                    rst.getBigDecimal("unit_price"),
                    rst.getInt("qty")));
        }
        return orderDetails;
    }

}
