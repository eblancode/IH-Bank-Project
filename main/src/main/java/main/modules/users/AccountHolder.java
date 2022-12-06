package main.modules.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.accounts.Account;
import main.modules.embedded.Address;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class AccountHolder extends User {
    private LocalDate birthDate;
    @Embedded
    private Address address;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country")),
    })
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> primaryAccountList;
    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> secondaryAccountList;

    public AccountHolder(String name, LocalDate birthDate, Address address/*, Address mailingAddress*/) {
        super(name);
        this.birthDate = birthDate;
//      this.mailingAddress = mailingAddress;
        this.address = address;
    }

}
