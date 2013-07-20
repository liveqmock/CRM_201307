/**
 * Project: com.hundsun.crm.biz.core.impl
 * 
 * File Created at 2013年7月16日
 * $Id$
 * 
 * Copyright 2008 6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 */
package com.beyond.common.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.beyond.common.utils.DozerHelper;

/**
 * 业务基类 
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月16日 下午8:08:30
 * @version v1.0
 */

public abstract class AbstractBaseService {

	@Autowired
    protected DozerHelper dozer;

    protected DozerHelper getDozer() {
        return dozer;
    }
	
}
