package com.nfs.democours.dao;

import com.nfs.democours.beans.Pony;

import java.util.List;

public interface PonyDao {
    public List<Pony> getAll();
    public Pony get(int id);
    public void update(Pony pony);
    public int addPony(Pony pony);
}
