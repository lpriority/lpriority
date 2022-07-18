package com.lp.login.dao;

import org.springframework.stereotype.Repository;

import com.lp.model.Invitations;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("invitationsDao")
public class InvitationsDAOImpl extends CustomHibernateDaoSupport implements
		InvitationsDAO {
	@Override
	public void saveInvitations(Invitations inv) {
		super.saveOrUpdate(inv);
	}

}
