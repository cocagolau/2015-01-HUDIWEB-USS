package uss.dao;

public interface Dao<T> {

	void insert(T object);

	void update(T Object);

	void delete(Object... keys);

	T find(Object... keys);
}
