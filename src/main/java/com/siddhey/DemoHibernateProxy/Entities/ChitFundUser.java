package com.siddhey.DemoHibernateProxy.Entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("chitfunduser")
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
