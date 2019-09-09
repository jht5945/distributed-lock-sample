package me.hatter.commos.tool.impl.factory;

import me.hatter.commos.tool.DistributedLock;
import me.hatter.commos.tool.DistributedLockException;
import me.hatter.commos.tool.DistributedLockFactory;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 * XMemcached distributed lock factory implemention
 *
 * @author hatterjiang
 */
public class XMemcachedDistributedLockFactory implements DistributedLockFactory {
    private static final String XMEMCACHED_DISTRIBUTED_LOCK_FACTORY_PREFIX = "__xmemcached_distributed_lock_factory_prefix:";
    private static final String XMEMCACHED_DISTRIBUTED_LOCK_FACTORY_DEFAULT_VALUE = "x";

    private int lockExpireInSeconds = 10 * 60;
    private MemcachedClient client;

    public XMemcachedDistributedLockFactory(MemcachedClient client) {
        this.client = client;
    }

    public XMemcachedDistributedLockFactory(MemcachedClient client, int lockExpireInSeconds) {
        this.client = client;
        this.lockExpireInSeconds = lockExpireInSeconds;
    }

    @Override
    public DistributedLock createDistributedLock(String lockId) {
        final String theLockId = XMEMCACHED_DISTRIBUTED_LOCK_FACTORY_PREFIX + lockId;
        return new DistributedLock() {
            @Override
            public boolean tryLock() throws DistributedLockException {
                try {
                    return client.add(theLockId, lockExpireInSeconds, XMEMCACHED_DISTRIBUTED_LOCK_FACTORY_DEFAULT_VALUE);
                } catch (Exception e) {
                    throw new DistributedLockException(e);
                }
            }

            @Override
            public void unLock() {
                try {
                    client.delete(theLockId);
                } catch (Exception e) {
                    // JUST DO NOTHING? Lock will release in {@code lockExpireInSeconds} seconds
                }
            }
        };
    }
}
