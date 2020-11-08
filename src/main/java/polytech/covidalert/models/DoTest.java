package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="do_test")
@Access(AccessType.FIELD)
public class DoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long do_test_id;
    private long user_id;
    private long test_id;
    private Date test_date;
    private String test_result;



    public long getDo_test_id() {
        return do_test_id;
    }

    public void setDo_test_id(long do_test_id) {
        this.do_test_id = do_test_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
    }

    public Date getTest_date() {
        return test_date;
    }

    public void setTest_date(Date test_date) {
        this.test_date = test_date;
    }

    public String getTest_result() {
        return test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

}
