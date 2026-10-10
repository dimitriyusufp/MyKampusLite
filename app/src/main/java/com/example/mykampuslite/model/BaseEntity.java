package com.example.mykampuslite.model;

public abstract class BaseEntity {
    protected long id;
    protected long createdAt;
    protected long updatedAt;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}