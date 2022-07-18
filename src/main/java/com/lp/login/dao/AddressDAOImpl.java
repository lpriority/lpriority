package com.lp.login.dao;

import org.springframework.stereotype.Repository;

import com.lp.model.Address;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("addressDAO")
public class AddressDAOImpl extends CustomHibernateDaoSupport implements
		AddressDAO {
	@Override
	public void saveAddress(Address address) {
		super.saveOrUpdate(address);
	}

}
