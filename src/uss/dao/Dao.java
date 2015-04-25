package uss.dao;

public interface Dao<T> {

	void insert(T object);

	void update(T object);

	void delete(T object);

	T find(Object... keys);
}
