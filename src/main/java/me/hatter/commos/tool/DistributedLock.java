package me.hatter.commos.tool;

/**
 * Distributed Lock Implemention
 *
 * @author hatterjiang
 */
public interface DistributedLock {

    /**
     * Try distributed lock, only returns true is get lock success
     * @return result
     * @throws DistributedLockException
     */
    boolean tryLock() throws DistributedLockException;

    /**
     * Release distributed lock
     */
    void unLock();
}
