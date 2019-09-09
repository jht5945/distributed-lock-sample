# distributed-lock-sample

Distributed lock using memcached/redis.

<br>

Install memcached on macOS:
```shell
$ brew install memcached
```

Run memcached:
```shell
$ memcached [-p <PORT>]
```

<br>

Code sample:

```java
MemcachedClient client = new XMemcachedClient("127.0.0.1", 11211);
DistributedLockFactory facotry = new XMemcachedDistributedLockFactory(client);

DistributedLock distributedLock = facotry.createDistributedLock("a");
if (distributedLock.tryLock()) {
    System.out.println("LOCK SUCCESS IN :" + System.currentTimeMillis());
    try {
        ThreadUtil.sleep(10L);
    } finally {
        System.out.println("LOCK SUCCESS OUT:" + System.currentTimeMillis());
        distributedLock.unLock();
    }
} else {
    System.out.println("LOCK FAIL:" + System.currentTimeMillis());
}
```


<br>

Libraries:
* https://github.com/killme2008/xmemcached
* https://github.com/dustin/java-memcached-client

