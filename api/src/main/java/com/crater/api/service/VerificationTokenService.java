package com.crater.api.service;

import com.crater.api.entity.VerificationToken;
import com.crater.api.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final OwnerService ownerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenService.class);

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, OwnerService ownerService) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.ownerService = ownerService;
    }

    @Transactional(readOnly = true)
    public VerificationToken findVerificationTokenByToken(String token) {
        Optional<VerificationToken> optional = verificationTokenRepository.findByToken(token);
      //  VerificationToken verificationToken = optional.orElseThrow(() ->new RuntimeException(""));
    return null;
    }

    @Transactional
    public void clearOldVerificationTokensAndAccounts() {
        long deletedCount = 0;
        List<VerificationToken> verificationTokenList = verificationTokenRepository.findAllByExpiryTimeAfter(new Date());
        Date currentDate = new Date();
        for (VerificationToken verificationToken : verificationTokenList) {
            // check to see if we can delete user
            if ((currentDate.getTime() - verificationToken.getCreationDate().getTime()) > VerificationToken.MAX_EXISTANCE) {
                // delete and probably send an email
                deletedCount++;
            }
        }
        LOGGER.debug("Deleted {} expired verification tokens and users", deletedCount);
    }
}
