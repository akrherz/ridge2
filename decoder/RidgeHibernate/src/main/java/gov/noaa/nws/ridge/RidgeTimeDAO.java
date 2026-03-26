/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate6.support.HibernateDaoSupport;

/**
 *
 * @author Jason.Burks
 */
public class RidgeTimeDAO extends HibernateDaoSupport {

    public void saveRadarTime(RadarTimeIndex radar) {
        try {
        getHibernateTemplate().merge(radar);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    public  List<RadarTimeIndex> getRadarTimeOlderThan(Date date) throws java.lang.Exception {
        @SuppressWarnings("unchecked")
        List<RadarTimeIndex> radar = (List<RadarTimeIndex>) getHibernateTemplate().find("FROM RadarTimeIndex WHERE datetime < ?1", date);
        if (radar != null) {
            return(radar);
        }
       throw new Exception("None found");
    }

    public void deleteRadarTime(RadarTimeIndex radar) {
        getHibernateTemplate().delete(radar);
    }

}
