package me.hatter.commons.tools;

import me.hatter.commos.tool.DistributedLock;
import me.hatter.commos.tool.impl.factory.XMemcachedDistributedLockFactory;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestXMemcachedDistributedLock {

    public static void main(String[] args) throws Exception {
        MemcachedClient client = new XMemcachedClient("127.0.0.1", 11211);
        XMemcachedDistributedLockFactory facotry = new XMemcachedDistributedLockFactory(client);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    DistributedLock distributedLock = facotry.createDistributedLock("a");
                    if (distributedLock.tryLock()) {
                        System.out.println("LOCK SUCCESS IN :" + System.currentTimeMillis());
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("LOCK SUCCESS OUT:" + System.currentTimeMillis());
                            distributedLock.unLock();
                        }
                    } else {
                        System.out.println("LOCK FAIL:" + System.currentTimeMillis());
                    }
                }
            });
        }

        executorService.shutdown();
    }
}