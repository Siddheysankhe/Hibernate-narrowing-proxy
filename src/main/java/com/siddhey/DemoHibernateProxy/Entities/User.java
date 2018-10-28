package com.siddhey.DemoHibernateProxy.Entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User extends AbstractBaseEntity{
  
  @Column
  private String name;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private ChitAdvisor chitAdvisor;
  
  public abstract String getIdentity();
  
  public User(String name){
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public ChitAdvisor getChitAdvisor() {
    return chitAdvisor;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setChitAdvisor(ChitAdvisor chitAdvisor) {
    this.chitAdvisor = chitAdvisor;
  }

  protected User(){}
}
