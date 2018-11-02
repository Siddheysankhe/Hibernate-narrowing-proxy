package com.siddhey.DemoHibernateProxy.Entities;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@DiscriminatorValue("chitfunduser")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChitFundUser extends User{
  
  protected ChitFundUser(){}
  
  public ChitFundUser(String name){
    super(name);
  }

  @Override
  public String getIdentity() {
    return "I am ChitFundUser";
  }
}
