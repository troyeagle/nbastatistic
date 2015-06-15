package njuse.ffff.util;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Nifury on 2014/12/1.
 */
public interface Mapper {
    public int insert(@Param("tableName") String tableName, @Param("data") Map<String, ?>
            data);
    public int insertWithKey(@Param("tableName") String tableName, @Param("data") Map<String, ?>
    data);

    public int delete(@Param("tableName") String tableName, @Param("filter") Map<String, ?>
            filter);

    public int update(@Param("tableName") String tableName, @Param("data") Map<String, ?>
            data, @Param("filter")
    Map<String, ?> filter);

    public int updateInc(@Param("tableName") String tableName, @Param("data") Map<String, ?>
            data, @Param("filter")
    Map<String, ?> filter);

    public Map<String, Object> selectOne(@Param("tableName") String tableName, @Param("target")
    List<String> target,
                                         @Param("filter") Map<String, ?> filter);

    public List<Map<String, Object>> selectList(@Param("tableName") String tableName, @Param
            ("target") List<String>
            target, @Param("filter") Map<String, ?> filter, @Param("vague") String vague);
    public List<Map<String, Object>> selectListFree(@Param("tableName") String tableName,@Param("target") List<String> target,
    		@Param("filter") String filter);
    /**
     * @param tableName
     * @param target
     * @param rangeKey  where rangeKey in
     * @param range     range
     * @param filter
     * @return
     */
    public List<Map<String,Object>> selectFree(@Param("statement") String statement);
    public List<Map<String, Object>> selectRangeList(@Param("tableName") String tableName, @Param
            ("target")
    List<String> target, @Param("rangeKey") String rangeKey, @Param("range") Collection<?> range,
                                                     @Param("filter")
                                                     Map<String, ?> filter);
}

