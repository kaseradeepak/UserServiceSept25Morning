package com.scaler.userservicesept25morning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "tokens")
public class Token extends BaseModel {
    private String tokenValue;

    private Date expiryDate;

    @ManyToOne
    private User user;
}

// Token ---- User => M:1
