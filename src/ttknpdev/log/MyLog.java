package ttknpdev.log;

import org.apache.log4j.Logger;

public class MyLog {
    public Logger log4j;
    public MyLog(Class c) {
        this.log4j = Logger.getLogger(c);
    }
}
