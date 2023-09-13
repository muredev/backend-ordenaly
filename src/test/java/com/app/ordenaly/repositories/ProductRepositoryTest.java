package com.app.ordenaly.repositories;

import com.app.ordenaly.models.Product;
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
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void testCreateNewProduct() {

    Product product1 = new Product();
    product1.setName("Pizza");
    product1.setDescription("Italiana y Napolitana");
    product1.setInStock(true);
    product1.setPrice(12900);

    Product saveNewProduct = productRepository.save(product1);

    assertTrue(saveNewProduct.getId() > 0);

  }

  @Test
  void testDeletedProductById() {

    Product productBeforeDeleted = entityManager.find(Product.class, 3);
    assertNotNull(productBeforeDeleted);

    productRepository.deleteById(productBeforeDeleted.getId());

    assertTrue(productBeforeDeleted.getId() == 3);

  }



}