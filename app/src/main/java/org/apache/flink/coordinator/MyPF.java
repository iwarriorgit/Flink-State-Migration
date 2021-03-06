package org.apache.flink.coordinator;

import org.apache.flink.api.common.functions.Partitioner;

import java.io.Serializable;
import java.util.HashMap;

public class MyPF<K> implements Serializable, Partitioner<K> {
	private MyConsistentHash<K> hb;
	private HashMap<K, Integer> hyperRoute;
	private int parallelism;
	MyPF(int parallelism) {
		this.parallelism=parallelism;
		hyperRoute=new HashMap<>();
		hb=new MyConsistentHash<>(parallelism);
	}
	public int partition(K key, int n) {
		int tmp=ha(key);
		if (tmp!=-1) return tmp;
		return hb.hash(key);
	}

	private int ha(K key) {
		return hyperRoute.getOrDefault(key, -1);
	}

	void setHb(MyConsistentHash<K> cs) {
		hb=cs;
	}
	MyConsistentHash<K> getHb() {
		return hb;
	}

	HashMap<K, Integer> getHyperRoute() {
		return hyperRoute;
	}

	void setHyperRoute(HashMap<K, Integer> route) {
		hyperRoute=route;
	}
}


