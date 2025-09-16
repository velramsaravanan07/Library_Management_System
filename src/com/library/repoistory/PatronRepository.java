package com.library.repoistory;

import com.library.dto.Patron;

import java.util.List;

public interface PatronRepository {

    public void addPatron(Patron book);

    public Patron updatePatron(Patron book);

    public String removePatron(long patronId);

    public Patron getPatronById(long patronId);

    public List<Patron> findAllPatrons();
}
