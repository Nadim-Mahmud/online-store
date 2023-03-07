package net.therap.onlinestore.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/4/23
 */
@Entity
@Table(name = "store_user")
@SQLDelete(sql = "UPDATE store_user SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByUserType", query = "SELECT u FROM User u WHERE u.type = :userType"),
        @NamedQuery(name = "User.isDuplicateEmail", query = "SELECT u FROM User u WHERE email = :email AND id != :id")

})
public class User extends Persistent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 1, max = 45, message = "{input.email}")
    @Email(message = "{input.email}")
    @Column(name = "email")
    private String email;

    @Column(name = "cell")
    private String cell;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "password")
    private String password;

    @Column(name = "password")
    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Address> addressList;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    public User() {
        orderList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }

    public boolean isNew() {
        return id == 0;
    }
}
