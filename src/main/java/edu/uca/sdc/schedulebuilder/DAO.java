package edu.uca.sdc.schedulebuilder;

import java.util.*;

public interface DAO<T> {
    Optional<T> get(int crn_key);
    List<T> getAll();
    int save(T t);
    void update(T t);
    void delete(int crn_key);
}