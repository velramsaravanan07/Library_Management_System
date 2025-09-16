package com.library.repoistory;

import com.library.dto.Branch;

import java.util.List;

public interface BranchRepository {
    void addBranch(Branch branch);
    Branch updateBranch(Branch branch);
    boolean removeBranch(long branchId);
    Branch findById(long branchId);
    List findAll();
}