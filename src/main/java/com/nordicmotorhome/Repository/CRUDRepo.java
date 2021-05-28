package com.nordicmotorhome.Repository;

import java.util.List;

public interface CRUDRepo<T> {
    List fetchAll();
    T findById(int id);
    void addNew(T obj);
    void delete(int id);
    void update(T obj, int id);
    boolean hasConstraint(int id);

}
