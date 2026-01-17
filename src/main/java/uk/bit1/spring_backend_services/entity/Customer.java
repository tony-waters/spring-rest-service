package uk.bit1.spring_backend_services.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lastName;
    private String firstName;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_info_id")
    private CustomerContactInfo customerContactInfo;

    public Customer() {}

    public Customer(String lastName, String firstName) {
        this.lastName= lastName;
        this.firstName = firstName;
    }

    public Customer(Long id, String lastName, String firstName) {
        this(lastName, firstName);
        this.id = id;
    }

//    public Customer(Long id, String lastName, String firstName, List<CustomerOrder> customerOrders) {
//        this(id, lastName, firstName);
//        this.customerOrders = customerOrders;
//    }

    public void addOrder(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
        customerOrder.setCustomer(this);
    }

    public void removeOrder(CustomerOrder customerOrder) {
        customerOrders.remove(customerOrder);
        customerOrder.setCustomer(null);
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<CustomerOrder> getOrders() { return customerOrders; }

    public CustomerContactInfo getCustomerContactInfo() {
        return customerContactInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCustomerContactInfo(CustomerContactInfo contactInfo) {
        this.customerContactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", firstName=" + firstName + ", lastName=" + lastName +
                ", orderCount=" + (customerOrders == null ? 0 : customerOrders.size()) + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
