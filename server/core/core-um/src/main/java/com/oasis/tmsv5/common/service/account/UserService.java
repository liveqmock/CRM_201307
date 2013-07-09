package com.oasis.tmsv5.common.service.account;

import com.oasis.tmsv5.common.vo.security.UserVO;

public interface UserService {

    UserVO getAccountById(Long accId);

    boolean saveUser(UserVO user);

}
