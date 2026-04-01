package com.nudgebank.paymentbackend.BCyrptTest;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    @Test
    public void passwordPrint() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("1234"));
        System.out.println(encoder.encode("1234"));
        System.out.println(encoder.encode("1234"));
        System.out.println(encoder.encode("1234"));
    }
}
