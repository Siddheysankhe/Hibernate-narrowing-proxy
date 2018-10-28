package com.siddhey.DemoHibernateProxy.Entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("groupsavinguser")
public class GroupSavingUser extends User{
  
  protected GroupSavingUser(){}
  
  public GroupSavingUser(String name){
    super(name);
  }

  @Override
  public String getIdentity() {
    return "I am GroupSavingUser";
  }

}
