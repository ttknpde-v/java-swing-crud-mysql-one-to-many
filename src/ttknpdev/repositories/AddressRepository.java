package ttknpdev.repositories;

import ttknpdev.entities.Employee;

import java.util.List;

public interface AddressRepository <T>{
    List<T> reads () ;
    Employee readsByEid (String eid) ;
    T read (String aid) ;
    Integer delete (String aid );
    Integer update (T obj );

    Integer create (T obj);
    Integer createRelations (String eid , String aid);
    void closeConnect ();
}
