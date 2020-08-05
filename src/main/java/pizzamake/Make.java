package pizzamake;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Make_table")
public class Make {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String pizzaname;
    private Long qty;
    private String status;

    @PostPersist
    public void onPostPersist() {
        Made made = new Made();
        BeanUtils.copyProperties(this, made);
        made.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){

        // hystrix 테스트용
        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Made made = new Made();
        BeanUtils.copyProperties(this, made);
        made.publishAfterCommit();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getPizzaname() {
        return pizzaname;
    }

    public void setPizzaname(String pizzaname) {
        this.pizzaname = pizzaname;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
