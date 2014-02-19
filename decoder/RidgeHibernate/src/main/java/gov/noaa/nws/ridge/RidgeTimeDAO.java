/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Jason.Burks
 */
public class RidgeTimeDAO extends HibernateDaoSupport {

    public void saveRadarTime(RadarTimeIndex radar) {
        try {
        getHibernateTemplate().saveOrUpdate(radar);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    public  List<RadarTimeIndex> getRadarTimeOlderThan(Date date) throws java.lang.Exception {
        List<RadarTimeIndex> radar = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(RadarTimeIndex.class).add(Restrictions.lt("datetime", date)));
        if (radar != null) {
            return(radar);
        }
       throw new Exception("None found");
    }

    public void deleteRadarTime(RadarTimeIndex radar) {
        getHibernateTemplate().delete(radar);
    }

}
