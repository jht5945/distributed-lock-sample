package me.hatter.commos.tool;

/**
 * Distributed lock factory
 *
 * @author hatterjiang
 */
public interface DistributedLockFactory {

    /**
     * Create distributed lock
     * @param lockId lock id
     * @return Distributed lock
     */
    DistributedLock createDistributedLock(String lockId);
}
