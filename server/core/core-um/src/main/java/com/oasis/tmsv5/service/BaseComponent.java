package com.oasis.tmsv5.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.oasis.tmsv5.util.helper.DozerHelper;

public abstract class BaseComponent {
    @Autowired
    protected DozerHelper dozer;

    protected DozerHelper getDozer() {
        return dozer;
    }
}
