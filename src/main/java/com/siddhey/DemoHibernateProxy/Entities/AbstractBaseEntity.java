package com.siddhey.DemoHibernateProxy.Entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBaseEntity {

  @Id
  @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
  private UUID id;

  protected AbstractBaseEntity() {
    this.id = UUID.randomUUID();
  }

  protected AbstractBaseEntity(UUID id) {
    this.id = Objects.requireNonNull(id);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractBaseEntity)) return false;

    AbstractBaseEntity that = (AbstractBaseEntity) o;
    return getId().equals(that.getId());
  }
}
