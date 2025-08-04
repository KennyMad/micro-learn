package org.pet.user.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document
public class User {
    @Id
    private Long id;
    private String fullname;
    private String city;
    private String email;
    private Date birthday;
    private BigDecimal balance;
}
