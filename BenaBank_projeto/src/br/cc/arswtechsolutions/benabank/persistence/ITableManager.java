package br.cc.arswtechsolutions.benabank.persistence;

import java.util.List;

public interface ITableManager<T> {
	public boolean save (T entity);
	public boolean update (T entity);
	public boolean removeById (long id);
	public boolean remove (T entity);
	public T find (long id);
	public List<T> list();
}
