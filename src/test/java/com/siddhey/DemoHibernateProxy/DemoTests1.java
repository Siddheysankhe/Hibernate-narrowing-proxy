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
import com.siddhey.DemoHibernateProxy.Entities.GroupSavingUser;
import com.siddhey.DemoHibernateProxy.Entities.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoTests1 {
  
  private static Logger logger = LoggerFactory.getLogger(DemoTests1.class);

  @PersistenceContext
  private EntityManager entityManager;

  private UUID pankajId;
  private UUID siddheyId;

  @Before
  public void before() {

    ChitAdvisor pankaj = new ChitAdvisor("Pankaj");
    ChitFundUser siddhey = new ChitFundUser("Siddhey");
    entityManager.persist(siddhey);
    GroupSavingUser akshat = new GroupSavingUser("Akshat");
    entityManager.persist(akshat);
    pankaj.addUser(siddhey);
    pankaj.addUser(akshat);
    entityManager.persist(pankaj);
    pankajId = pankaj.getId();
    siddheyId = pankaj.getUsers().get(0).getId();

    entityManager.flush();
    entityManager.clear();

    logger.info("----- TEST DATA CREATED -----");
  }
  
  @Test
  public void demo_proxy1() {
    ChitAdvisor advisor =
        entityManager.getReference(ChitAdvisor.class, pankajId);

    assertThat(advisor).isInstanceOf(ChitAdvisor.class);

    logger.info(
        "Chit Advisor " + advisor.getName() + " is managing following users:");
    List<User> users = advisor.getUsers();
    for (User u : users) {
      logger.info("Name:" + u.getName() + "\tIdentity:" + u.getIdentity());
    }

  }
  
}
