package com.siddhey.DemoHibernateProxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.siddhey.DemoHibernateProxy.Entities.ChitAdvisor;
import com.siddhey.DemoHibernateProxy.Entities.ChitFundUser;
import com.siddhey.DemoHibernateProxy.Entities.User;


@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoTests {

  private Logger logger = LoggerFactory.getLogger(DemoTests.class);

  @PersistenceContext
  private EntityManager entityManager;

  private UUID pankajId;
  private UUID siddheyId;

  @Before
  public void before() {

    ChitAdvisor pankaj = new ChitAdvisor("Pankaj");
    ChitFundUser siddhey = new ChitFundUser("Siddhey");
    entityManager.persist(siddhey);
    pankaj.addUser(siddhey);
    entityManager.persist(pankaj);
    pankajId = pankaj.getId();
    siddheyId = pankaj.getUsers().get(0).getId();

    entityManager.flush();
    entityManager.clear();

    logger.info("----- TEST DATA CREATED -----");

  }

  @Test
  public void demo_proxy() {

    // This will return a hibernate proxy which is not initialized and will be
    // instance of User and Not ChitFundUser
    User user = entityManager.getReference(User.class, siddheyId);

    assertThat(user).isInstanceOf(User.class)
        .isNotInstanceOf(ChitFundUser.class);

    // this will initialize the proxy but still will be instance of User
    // now hibernate knows our User is ChitFundUser but proxy continues to be
    // User Proxy
    logger.info("User is:" + user.getIdentity());

    // Now if we think that we try to load user again since the proxy is
    // initialized hibernate will return the instance of
    // Chitfunduser but that's not true as hibernate prefers returning
    // entity from first level cache rather than loading
    // it again from db
    User user2 = entityManager.getReference(User.class, siddheyId);
    logger.info("User is:" + user2.getIdentity());

    assertThat(user2).isInstanceOf(User.class).isSameAs(user);
    
    logger.info("---------ToString of every User------------------------------------------");
    logger.info(user.toString());
    logger.info(user2.toString());
    logger.info(user.getClass().getName());
    logger.info(user2.getClass().getName());

    // HHH000179: Narrowing proxy to class
    // com.example.hibernateproxydemo.entities.ChitFundUser - this operation
    // breaks ==
    User user3 = entityManager.getReference(ChitFundUser.class, siddheyId);
    logger.info(user3.getClass().getName());
    assertThat(user3).isInstanceOf(ChitFundUser.class);
    
    //narrowed instance
    User user4 = entityManager.getReference(User.class, siddheyId);
    
    assertThat(user4).isSameAs(user3);

    if (user2 == user4) {
      logger.info("User2 and User4 are equal");
    } else {
      logger.info("User2 and User4 are not equal");
    }
    
    if (user2.equals(user4)) {
      logger.info("User2 and User4 are equal");
    } else {
      logger.info("User2 and User4 are not equal");
    }
    
    logger.info("---------ToString of every User------------------------------------------");
    logger.info(user.toString());
    logger.info(user2.toString());
    logger.info(user3.toString());
    logger.info(user4.toString());
    logger.info(user.getClass().getName());
    logger.info(user2.getClass().getName());
    logger.info(user3.getClass().getName());
    logger.info(user4.getClass().getName());
    logger.info("-------------------------------------------------------------------------");

    ChitAdvisor advisor =
        entityManager.getReference(ChitAdvisor.class, pankajId);

    List<User> users = advisor.getUsers();
    
    assertThat(users.contains(user)).isTrue();
    
    assertThat(users.contains(user3)).isTrue();
  }
}
