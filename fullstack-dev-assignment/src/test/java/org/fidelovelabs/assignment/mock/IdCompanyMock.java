package org.fidelovelabs.assignment.mock;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;

public class IdCompanyMock implements IAtomicLong {

	private long id;

	public IdCompanyMock() {
		id = 0;
	}

	public long getAndIncrement() {
		return id++;
	}

	@Override
	public String getPartitionKey() {
		return null;
	}

	@Override
	public String getServiceName() {
		return null;
	}

	@Override
	public void destroy() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public long addAndGet(long delta) {
		return 0;
	}

	@Override
	public boolean compareAndSet(long expect, long update) {
		return false;
	}

	@Override
	public long decrementAndGet() {
		return 0;
	}

	@Override
	public long get() {
		return 0;
	}

	@Override
	public long getAndAdd(long delta) {
		return 0;
	}

	@Override
	public long getAndSet(long newValue) {
		return 0;
	}

	@Override
	public long incrementAndGet() {
		return 0;
	}

	@Override
	public void set(long newValue) {
	}

	@Override
	public void alter(IFunction<Long, Long> function) {
	}

	@Override
	public long alterAndGet(IFunction<Long, Long> function) {
		return 0;
	}

	@Override
	public long getAndAlter(IFunction<Long, Long> function) {
		return 0;
	}

	@Override
	public <R> R apply(IFunction<Long, R> function) {
		return null;
	}
}