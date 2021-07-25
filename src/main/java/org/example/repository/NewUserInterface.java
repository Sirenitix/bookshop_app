package org.example.repository;

import java.util.List;

public interface NewUserInterface<T> {


    void store(T user);

    List<T> retreiveAll();

    void setAuth(T auth);
}
