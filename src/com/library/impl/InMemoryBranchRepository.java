package com.library.impl;

import com.library.dto.Branch;
import com.library.repoistory.BranchRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryBranchRepository implements BranchRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryBranchRepository.class.getName());
    private final Map branchMap = new HashMap();
    private long nextId = 1;

    @Override
    public void addBranch(Branch branch) {
        branch.setBranchId(nextId);
        branchMap.put(nextId, branch);
        LOGGER.log(Level.INFO, "Branch added with ID: " + nextId);
        nextId++;
    }

    @Override
    public Branch updateBranch(Branch branch) {
        if (branchMap.containsKey(branch.getBranchId())) {
            branchMap.put(branch.getBranchId(), branch);
            LOGGER.log(Level.INFO, "Branch updated with ID: " + branch.getBranchId());
            return branch;
        }
        LOGGER.log(Level.WARNING, "Branch not found for update with ID: " + branch.getBranchId());
        return null;
    }

    @Override
    public boolean removeBranch(long branchId) {
        if (branchMap.containsKey(branchId)) {
            branchMap.remove(branchId);
            LOGGER.log(Level.INFO, "Branch removed with ID: " + branchId);
            return true;
        }
        LOGGER.log(Level.WARNING, "Branch not found for removal with ID: " + branchId);
        return false;
    }

    @Override
    public Branch findById(long branchId) {
        Branch branch = (Branch) branchMap.get(branchId);
        if (branch != null) {
            LOGGER.log(Level.INFO, "Retrieved branch with ID: " + branchId);
        } else {
            LOGGER.log(Level.WARNING, "Branch not found with ID: " + branchId);
        }
        return branch;
    }

    @Override
    public List findAll() {
        List branches = new ArrayList();
        for (Object branchObj : branchMap.values()) {
            branches.add(branchObj);
        }
        LOGGER.log(Level.INFO, "Retrieved " + branches.size() + " branches");
        return branches;
    }
}