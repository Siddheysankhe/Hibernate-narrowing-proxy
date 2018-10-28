package com.siddhey.DemoHibernateProxy.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class ChitAdvisor extends AbstractBaseEntity{
  
  private String name;
  
  @OneToMany(mappedBy = "chitAdvisor",fetch = FetchType.LAZY)
  private List<User> users = new ArrayList<>();
  
  protected ChitAdvisor(){}
  
  public ChitAdvisor(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
  
  public void addUser(User user){
    user.setChitAdvisor(this);
    users.add(user);
  }
}
