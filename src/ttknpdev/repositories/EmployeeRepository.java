package ttknpdev.repositories;

import java.util.List;

public interface EmployeeRepository <T> {
    List<T> reads () ;
    T read(String eid);
    Integer delete (String eid);
    Integer create (T obj);
    Integer update (T obj);
    void closeConnect ();
}
