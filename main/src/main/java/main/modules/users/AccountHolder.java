package main.modules.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.accounts.Account;
import main.modules.users.embedded.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class AccountHolder extends User {
    @JsonFormat(pattern = "dd-MM-yyyy")
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
    private List<Account> primaryAccountList = new ArrayList<>();
    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> secondaryAccountList = new ArrayList<>();

    public AccountHolder(String userName, String password, LocalDate birthDate, Address address, Address mailingAddress) {
        super(userName, password);
        this.birthDate = birthDate;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(LocalDate birthDate, Address address, Address mailingAddress) {
        this.birthDate = birthDate;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(LocalDate birthDate, Address address) {
        this.birthDate = birthDate;
        this.address = address;
    }

    public AccountHolder(String userName, String password, LocalDate birthDate, Address address) {
        super(userName, password);
        this.birthDate = birthDate;
        this.address = address;
    }

    public void addPrimaryAccountToList(Account primaryAccountList) {
        this.primaryAccountList.add(primaryAccountList);
    }

    public void addSecondaryAccountToList(Account secondaryAccountList) {
        this.secondaryAccountList.add(secondaryAccountList);
    }

}
