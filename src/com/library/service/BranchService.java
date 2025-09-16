package com.library.service;

import com.library.dto.Branch;
import com.library.repoistory.BranchRepository;
import com.library.repoistory.BookRepository;

import java.util.*;

public class BranchService {
    private final BranchRepository branchRepository;
    private final BookRepository bookRepository;

    public BranchService(BranchRepository branchRepository, BookRepository bookRepository) {
        this.branchRepository = branchRepository;
        this.bookRepository = bookRepository;
    }

    public void addBranch(Branch branch) {
        validateBranch(branch);
        branchRepository.addBranch(branch);
    }

    public Branch updateBranch(Branch branch) {
        validateBranch(branch);
        return branchRepository.updateBranch(branch);
    }

    public boolean removeBranch(long branchId) {
        List branchBooks = bookRepository.findByBranch(branchId);
        if (!branchBooks.isEmpty()) {
            throw new IllegalStateException("Cannot delete branch with books. Transfer books first.");
        }
        return branchRepository.removeBranch(branchId);
    }

    public Branch findById(long branchId) {
        return branchRepository.findById(branchId);
    }

    public List findAll() {
        return branchRepository.findAll();
    }

    public List getBranchBooks(long branchId) {
        return bookRepository.findByBranch(branchId);
    }

    private void validateBranch(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Branch cannot be null");
        }
        if (branch.getName() == null || branch.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch name cannot be empty");
        }
        if (branch.getAddress() == null || branch.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch address cannot be empty");
        }
    }
}