package io.github.jonjohnsontc.whattoread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Disabling DataSource / db configuration temporarily
@SpringBootApplication()
public class WhatToReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatToReadApplication.class, args);
    }

}
