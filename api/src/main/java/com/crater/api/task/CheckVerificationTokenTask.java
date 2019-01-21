package com.crater.api.task;

import com.crater.api.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckVerificationTokenTask {

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public CheckVerificationTokenTask(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }
    public void deleteUnverifiedAccounts() {
        verificationTokenService.clearOldVerificationTokensAndAccounts();
    }
}
