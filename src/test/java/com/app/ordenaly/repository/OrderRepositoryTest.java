package com.app.ordenaly.repository;

import com.app.ordenaly.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void testGenerateOrder() {
    Ticket ticket = entityManager.find(Ticket.class, 8);
    User waiter = entityManager.find(User.class, 1);

    Order order = new Order();
    order.setTicket(ticket);
    order.setUser(waiter);
    order.setOrderStatus(OrderStatus.SIN_ATENDER);
    order.setPaymentStatus(PaymentStatus.PENDIENTE);

    Order saveOrder = orderRepository.save(order);

    //Se asocia el id de la orden con el ticket
    ticket.setOrder(order);

    assertTrue(saveOrder.getId() > 0);
  }

  @Test
  void testAddItemToOrder() {
    Order order = entityManager.find(Order.class, 17);
    Product product = entityManager.find(Product.class, 3);

    Item item = new Item(product, 1);
    Item saveNewItem = itemRepository.save(item);

    order.addItem(item);

    orderRepository.save(order);
  }

  @Test
  void testDeleteItem() {
    //Arroja error porque esta ligada a la entidad producto
    Item item = entityManager.find(Item.class, 6);

    itemRepository.deleteById(item.getId());
  }

  @Test
  void testUpdateOrderStatus() {
    Order order = entityManager.find(Order.class, 53);

    order.setOrderStatus(OrderStatus.ATENDIDA);
    order.setPaymentStatus(PaymentStatus.PAGADA);

    orderRepository.save(order);

    assertTrue( order.getOrderStatus() == OrderStatus.ATENDIDA );
    assertTrue( order.getPaymentStatus() == PaymentStatus.PAGADA);
  }

  @Test
  void testDeleteOrder() {
    Order order = entityManager.find(Order.class, 35);

    orderRepository.deleteById(order.getId());

    assertTrue( order.getId() == 35 );
  }

}