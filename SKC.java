import com.sleepycat.db.*;
    
class SKC implements SecondaryKeyCreator {
    public boolean createSecondaryKey(SecondaryDatabase secondary,
                                      DatabaseEntry key,
                                      DatabaseEntry data,
                                      DatabaseEntry result)
        throws DatabaseException {

        result.setData(data.getData());
        
        return true;
    }
}
