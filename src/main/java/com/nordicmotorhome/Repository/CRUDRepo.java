package com.nordicmotorhome.Repository;

import java.util.List;
import java.util.Objects;

public interface CRUDRepo<T> {
    public List fetchAll();
    public T findById(int id);
    public void addNew(T obj);
    public void delete(int id);
    public void update(int id, T obj);
    public boolean hasConstraint();

}
