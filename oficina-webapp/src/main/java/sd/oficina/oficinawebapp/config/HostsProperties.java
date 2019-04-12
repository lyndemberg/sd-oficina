package sd.oficina.oficinawebapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HostsProperties {

//    ORDER
    @Value("${order1.host}")
    private String order1Host;
    @Value("${order1.port}")
    private int order1Port;
    @Value("${order2.host}")
    private String order2Host;
    @Value("${order2.port}")
    private int order2Port;
//    STORE
    @Value("${store1.host}")
    private String store1Host;
    @Value("${store1.port}")
    private int store1Port;
    @Value("${store2.host}")
    private String store2Host;
    @Value("${store2.port}")
    private int store2Port;

//    PERSON
    @Value("${person1.host}")
    private String person1Host;
    @Value("${person1.port}")
    private int person1Port;
    @Value("${person2.host}")
    private String person2Host;
    @Value("${person2.port}")
    private int person2Port;
    @Value("${person3.host}")
    private String person3Host;
    @Value("${person3.port}")
    private int person3Port;

//    CUSTOMER
    @Value("${customer1.host}")
    private String customer1Host;
    @Value("${customer1.port}")
    private int customer1Port;
    @Value("${customer2.host}")
    private String customer2Host;
    @Value("${customer2.port}")
    private int customer2Port;
    @Value("${customer3.host}")
    private String customer3Host;
    @Value("${customer3.port}")
    private int customer3Port;


    public String getOrder1Host() {
        return order1Host;
    }

    public void setOrder1Host(String order1Host) {
        this.order1Host = order1Host;
    }

    public int getOrder1Port() {
        return order1Port;
    }

    public void setOrder1Port(int order1Port) {
        this.order1Port = order1Port;
    }

    public String getOrder2Host() {
        return order2Host;
    }

    public void setOrder2Host(String order2Host) {
        this.order2Host = order2Host;
    }

    public int getOrder2Port() {
        return order2Port;
    }

    public void setOrder2Port(int order2Port) {
        this.order2Port = order2Port;
    }

    public String getStore1Host() {
        return store1Host;
    }

    public void setStore1Host(String store1Host) {
        this.store1Host = store1Host;
    }

    public int getStore1Port() {
        return store1Port;
    }

    public void setStore1Port(int store1Port) {
        this.store1Port = store1Port;
    }

    public String getStore2Host() {
        return store2Host;
    }

    public void setStore2Host(String store2Host) {
        this.store2Host = store2Host;
    }

    public int getStore2Port() {
        return store2Port;
    }

    public void setStore2Port(int store2Port) {
        this.store2Port = store2Port;
    }

    public String getPerson1Host() {
        return person1Host;
    }

    public void setPerson1Host(String person1Host) {
        this.person1Host = person1Host;
    }

    public int getPerson1Port() {
        return person1Port;
    }

    public void setPerson1Port(int person1Port) {
        this.person1Port = person1Port;
    }

    public String getPerson2Host() {
        return person2Host;
    }

    public void setPerson2Host(String person2Host) {
        this.person2Host = person2Host;
    }

    public int getPerson2Port() {
        return person2Port;
    }

    public void setPerson2Port(int person2Port) {
        this.person2Port = person2Port;
    }

    public String getPerson3Host() {
        return person3Host;
    }

    public void setPerson3Host(String person3Host) {
        this.person3Host = person3Host;
    }

    public int getPerson3Port() {
        return person3Port;
    }

    public void setPerson3Port(int person3Port) {
        this.person3Port = person3Port;
    }

    public String getCustomer1Host() {
        return customer1Host;
    }

    public void setCustomer1Host(String customer1Host) {
        this.customer1Host = customer1Host;
    }

    public int getCustomer1Port() {
        return customer1Port;
    }

    public void setCustomer1Port(int customer1Port) {
        this.customer1Port = customer1Port;
    }

    public String getCustomer2Host() {
        return customer2Host;
    }

    public void setCustomer2Host(String customer2Host) {
        this.customer2Host = customer2Host;
    }

    public int getCustomer2Port() {
        return customer2Port;
    }

    public void setCustomer2Port(int customer2Port) {
        this.customer2Port = customer2Port;
    }

    public String getCustomer3Host() {
        return customer3Host;
    }

    public void setCustomer3Host(String customer3Host) {
        this.customer3Host = customer3Host;
    }

    public int getCustomer3Port() {
        return customer3Port;
    }

    public void setCustomer3Port(int customer3Port) {
        this.customer3Port = customer3Port;
    }
}
