package com.baosight.iwater.define;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.ApplicationContext;

import com.baosight.iwater.define.SerializeUtil;


/*
 * 使用第三方缓存服务器，处理二级缓存
 */
public class RedisCache implements Cache {

    //private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
    @Resource 
    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;
    private static final int DB_INDEX = 1;
    private final String COMMON_CACHE_KEY = "COM:";
    private static final String UTF8 = "utf-8";
    
    private ApplicationContext context;

    /**
     * 按照一定规则标识key
     */
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(COMMON_CACHE_KEY);
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        //System.out.println(key+"------------");
        //System.out.println(DigestUtils.md5Hex(String.valueOf(key))+"------------");
        //namespace名字+"."+方法名+“-”+角色名
        String[] arry=key.toString().split(":");
        String returnKey="";
        for(int i=0;i<arry.length;i++){
        	if(arry[i].contains(this.id)){
        		returnKey=arry[i].replaceAll("FlushCache","");
        	}
        }
        return returnKey+"-"+"rolename";
    }

    /**
     * redis key规则前缀
     */
    private String getKeys() {
        return COMMON_CACHE_KEY + this.id + ":*";
    }

    public RedisCache() {

    }

    public RedisCache(final String id) { 
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }

    public String getId() {
        return this.id;
    } 

    public int getSize() {
    	CacheManager cacheManager=new CacheManager();
        int result = 0;
        boolean borrowOrOprSuccess = true;
        try {
        	cacheManager.jedis.select(DB_INDEX);
            Set<byte[]> keys = cacheManager.jedis.keys(getKeys().getBytes(UTF8));
            if (null != keys && !keys.isEmpty()) {
                result = keys.size();
            }
            System.out.println(this.id + "---->>>>总缓存数:" + result);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (cacheManager.jedis != null)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
                
        } finally {
            if (borrowOrOprSuccess)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        }
        return result;
    }

    
    public void putObject(Object key, Object value) {
        boolean borrowOrOprSuccess = true;
        CacheManager cacheManager=new CacheManager();
        try {
        	cacheManager.jedis.select(DB_INDEX);
            byte[] keys = getKey(key).getBytes(UTF8);
            cacheManager.jedis.set(keys, SerializeUtil.serialize(value));
            System.out.println("添加缓存--------" + getKey(key));
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (cacheManager.jedis != null)
                cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        } finally {
            if (borrowOrOprSuccess)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        }
    }

    
    public Object getObject(Object key) {
        Object value = null;
        CacheManager cacheManager=new CacheManager();
        boolean borrowOrOprSuccess = true;
        try {
        	cacheManager.jedis.select(DB_INDEX);
        	if(cacheManager.jedis.get(getKey(key).getBytes(UTF8))==null){
        		System.out.println("缓存中不存在-----"+ getKey(key));
        	}else{
        		System.out.println("从缓存中获取-----" + getKey(key));
        		value = SerializeUtil.unserialize(cacheManager.jedis.get(getKey(key).getBytes(UTF8)));
        	}
        	
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (cacheManager.jedis != null)
                cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        } finally {
            if (borrowOrOprSuccess)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        }
        return value;
    }

    
    public Object removeObject(Object key) {
        Object value = null;
        CacheManager cacheManager=new CacheManager();
        boolean borrowOrOprSuccess = true;
        try {
        	cacheManager.jedis.select(DB_INDEX);
            value = cacheManager.jedis.del(getKey(key).getBytes(UTF8));
            System.out.println("LRU算法从缓存中移除-----" + this.id);
            // getSize();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (cacheManager.jedis != null)
                cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        } finally {
            if (borrowOrOprSuccess)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        }
        return value;
    }

    
    public void clear() {
        boolean borrowOrOprSuccess = true;
        CacheManager cacheManager=new CacheManager();
        try {
        	cacheManager.jedis.select(DB_INDEX);
            // 如果有删除操作，会影响到整个表中的数据，因此要清空一个mapper的缓存（一个mapper的不同数据操作对应不同的key）
            Set<byte[]> keys = cacheManager.jedis.keys(getKeys().getBytes(UTF8));
            System.out.println("出现CUD操作，清空对应Mapper缓存======>" + keys.size());
            for (byte[] key : keys) {
            	cacheManager.jedis.del(key);
            }
            // 下面是网上流传的方法，极大的降低系统性能，没起到加入缓存应有的作用，这是不可取的。
            // jedis.flushDB();
            // jedis.flushAll();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (cacheManager.jedis != null)
                cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        } finally {
            if (borrowOrOprSuccess)
            	cacheManager.returnResource(cacheManager.jedisConnection,cacheManager.jedis);
        }
    }

    
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
