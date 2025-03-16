package edu.uca.sdc.schedulebuilder.DAOs;

import java.util.*;

public interface DAO<T> {
    Optional<T> get(String id);
    List<T> getAll();
    int save(T t);
    void update(T t);
    void delete(String id);
}