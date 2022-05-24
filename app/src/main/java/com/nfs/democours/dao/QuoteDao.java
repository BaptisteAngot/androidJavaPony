package com.nfs.democours.dao;

import com.nfs.democours.beans.Quote;

import java.util.List;

public interface QuoteDao {

    public List<Quote> getAll();
    public Quote get(int id);
    public void update(Quote quote);
    public int addQuote(Quote quote);

}
