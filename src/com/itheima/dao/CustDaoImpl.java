package com.itheima.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.domain.Cust;
import com.itheima.domain.Page;
import com.itheima.util.DaoUtils;

public class CustDaoImpl implements CustDao {

	public void addCust(Cust cust) {
		String sql = "insert into customer values (null,?,?,?,?,?,?,?,?)";
		try {
			 
			  
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql, cust.getName(), cust.getGender(),
					cust.getBirthday(), cust.getCellphone(), cust.getEmail(),
					cust.getPreference(), cust.getType(), cust.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public Cust findUserByName(String name) {
		String sql = "select * from customer where name = ?";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<Cust>(Cust.class), name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Cust> getAllCust() {
		String sql = "select * from customer";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<Cust>(Cust.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public Cust findUserById(String id) {
		String sql = "select * from customer where id = ?";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<Cust>(Cust.class), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void updateCust(Cust cust) {
		String sql = "update customer set name=? ,gender=?,birthday=?,cellphone=?,email=?,preference=?,type=?,description=? where id=?";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql, cust.getName(), cust.getGender(),
					cust.getBirthday(), cust.getCellphone(), cust.getEmail(),
					cust.getPreference(), cust.getType(),
					cust.getDescription(), cust.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void delCustByID(String id) {
		String sql = "delete from customer where id = ?";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 在创建QueryRunner对象时，不传递数据源给它，是为了保证这些条SQL在同一个事务中进行，若传了数据源 可能他们会从
	 * 连接池里取不同的connection 没法进行事务控制 我们在逻辑层手动获取数据库连接，然后让这几条SQL使用同一个数据库连接执行，但
	 * 在这获取连接 破坏了 解耦 性
	 * 
	 * 更优雅的处理是用 ThreadLocal http://www.cnblogs.com/xdp-gacl/p/4007225.html
	 * 
	 * 
	 * 注意 不要 捕获异常 要抛出去 交给逻辑层 事务回滚用
	 */
	public void delCustByIDWithTrans(Connection conn, String id)
			throws SQLException {
		String sql = "delete from customer where id = ?";
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, id);
	}

	/**
	 * 根据条件查询客户信息
	 * 
	 * @param cust
	 *            封装了查询条件的bean 用户名/性别/客户级别
	 * @return
	 */
	@Override
	public List<Cust> findCustByCond(Cust cust) {
		String sql = "select * from customer where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if (cust.getName() != null && !"".equals(cust.getName().trim())) {
			// sql += " and name like %?% "; //百分号要放到下面
			sql += " and name like ?";
			list.add("%" + cust.getName() + "%");
		}
		if (cust.getGender() != null && !"".equals(cust.getGender())) {
			sql += " and gender = ? ";
			list.add(cust.getGender());
		}
		if (cust.getType() != null && !"".equals(cust.getType())) {
			sql += " and type = ? ";
			list.add(cust.getType());
		}

		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			if (list.size() <= 0) {
				return runner.query(sql, new BeanListHandler<Cust>(Cust.class));
			} else {
				return runner.query(sql, new BeanListHandler<Cust>(Cust.class),
						list.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int getRowCount() {
		String sql = "select count(*) from customer";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());

			return Integer.parseInt(String.valueOf(((Long) runner.query(sql,
					new ScalarHandler()))));// 第一行第一列的数据 long型的 要转为 int
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Cust> getCustBypage(int from, int count) {
		String sql = "select * from customer limit ?,?";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());

			return runner.query(sql, new BeanListHandler<Cust>(Cust.class),from,
					count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
