package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.Gradeevents;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("gradeEventsDAO")
public class GradeEventsDAOImpl extends CustomHibernateDaoSupport implements
		GradeEventsDAO {
	static final Logger logger = Logger.getLogger(GradeEventsDAOImpl.class);

	@Override
	public Gradeevents getGradeEvent(long eventId) {
		Gradeevents aGradeEvents = new Gradeevents();
		try {
			aGradeEvents = (Gradeevents) super.find(Gradeevents.class, eventId);
		} catch (HibernateException e) {
			logger.error("Error in getGradeEvent() of Gradeevents" + e);
			e.printStackTrace();
		}
		return aGradeEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gradeevents> getGradeEventList() {
		List<Gradeevents> geList = new ArrayList<Gradeevents>();
		try {
			geList = (List<Gradeevents>) getHibernateTemplate().find(
					"from Gradeevents where isComposite='" + WebKeys.LP_TRUE
							+ "' order by eventId");
		} catch (DataAccessException e) {
			logger.error("Error in getGradeEventList() of Gradeevents" + e);
			e.printStackTrace();
		}
		return geList;
	}

	@Override
	public void deleteGradeEvent(long gradeEventId) {
		try {
			super.delete(getGradeEvent(gradeEventId));
		} catch (HibernateException e) {
			logger.error("Error in deleteGradeEvent() of Gradeevents" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveGradeEvent(Gradeevents gradeEvent) {
		try {
			super.saveOrUpdate(gradeEvent);
		} catch (HibernateException e) {
			logger.error("Error in saveGradeEvent() of Gradeevents" + e);
			e.printStackTrace();
		}
	}

}
