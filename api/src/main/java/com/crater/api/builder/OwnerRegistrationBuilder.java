package com.crater.api.builder;

import com.crater.api.entity.Owner;
import com.crater.api.entity.VerificationToken;
import com.crater.api.utils.UUIDSupport;

import java.util.Calendar;
import java.util.Date;

public class OwnerRegistrationBuilder extends UUIDSupport {
    private String username;
    private String hashedPassword;

    public OwnerRegistrationBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public OwnerRegistrationBuilder setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public Owner build() {
        Owner owner = new Owner();
        VerificationToken verificationToken = new VerificationToken();

        owner.setVerificationToken(verificationToken);
        verificationToken.setOwner(owner);

        owner.setUsername(username);
        owner.setPassword(hashedPassword);
        owner.setActive(false);
        owner.setApplicationId(uuid());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        verificationToken.setExpiryTime(calendar.getTime());
        verificationToken.setToken(uuid());
        return owner;
    }
}
