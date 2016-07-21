package com.itheima.service;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.dao.CustDao;
import com.itheima.domain.Cust;
import com.itheima.domain.Page;
import com.itheima.factory.BasicFactory;
import com.itheima.util.DaoUtils;

public class CustServiceImpl implements CustService {
	CustDao dao = BasicFactory.getFactory().getInstance(CustDao.class);

	public void addCust(Cust cust) {
		// 1.检查用户名是否已经存在
		if (dao.findUserByName(cust.getName()) != null) {
			throw new RuntimeException("用户名已经存在!!");
		}
		// 2.调用dao中的方法增加用户
		dao.addCust(cust);
	}

	public List<Cust> getAllCust() {
		return dao.getAllCust();
	}

	public Cust findCustById(String id) {
		return dao.findUserById(id);
	}

	public void updateCust(Cust cust) {
		dao.updateCust(cust);
	}

	public void delCustByID(String id) {
		dao.delCustByID(id);
	}

	/**
	 * 在创建QueryRunner对象时，不传递数据源给它，是为了保证这些条SQL在同一个事务中进行，若传了数据源 可能他们会从
	 * 连接池里取不同的connection 没法进行事务控制 我们在逻辑层手动获取数据库连接，然后让这几条SQL使用同一个数据库连接执行，但
	 * 在这获取连接 破坏了 解耦 性
	 * 
	 * 更优雅的处理是用 ThreadLocal http://www.cnblogs.com/xdp-gacl/p/4007225.html
	 */
	public void batchDel(String[] ids) {// 事务的控制属于 逻辑层service
		Connection conn = DaoUtils.getConn();
		if (ids == null)
			return;
		try {
			conn.setAutoCommit(false);

			for (String id : ids) {
				dao.delCustByIDWithTrans(conn, id);
			}
			DbUtils.commitAndCloseQuietly(conn);
		} catch (Exception e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Cust> findCustByCond(Cust cust) {

		return dao.findCustByCond(cust);
	}

	@Override
	public Page pageCust(int thispage, int rowperpage) {
		Page page = new Page();
		page.setThispage(thispage);
		page.setRowperpage(rowperpage);
		// 共多少行
		int rowcount = dao.getRowCount();
		page.setCountrow(rowcount);
		// 共多少页
		int pageCount = rowcount / rowperpage + (rowcount % rowperpage == 0 ? 0
				: 1);
		page.setCountpage(pageCount);
		// 首页
		page.setFirstpage(1);
		// 尾页
		page.setLastpage(pageCount);
		// 下一页
		page.setNextpage(thispage + 1 > pageCount ? pageCount : thispage + 1);//注意优先级
		// 上一页
		page.setPrepage(thispage - 1 < 1 ? 1 : thispage - 1);

		// 当前页 数据
		List<Cust> list = dao.getCustBypage((thispage - 1) * rowperpage,
				rowperpage);
		page.setList(list);
		return page;
	}
}
