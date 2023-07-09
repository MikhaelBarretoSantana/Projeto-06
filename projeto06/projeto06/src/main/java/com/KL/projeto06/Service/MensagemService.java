package com.KL.projeto06.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Profile("production")
public class MensagemService {
    
    @Value("${application.name}")
    private String appName;

}
