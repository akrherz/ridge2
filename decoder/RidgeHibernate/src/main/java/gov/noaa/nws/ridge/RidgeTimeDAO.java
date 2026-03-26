/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Jason.Burks
 */
public class RidgeTimeDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveRadarTime(RadarTimeIndex radar) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(radar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RadarTimeIndex> getRadarTimeOlderThan(Date date) throws java.lang.Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<RadarTimeIndex> query = session.createQuery(
            "FROM RadarTimeIndex WHERE datetime < :date", RadarTimeIndex.class);
        query.setParameter("date", date);
        List<RadarTimeIndex> radar = query.getResultList();
        if (radar != null && !radar.isEmpty()) {
            return radar;
        }
        throw new Exception("None found");
    }

    public void deleteRadarTime(RadarTimeIndex radar) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.contains(radar) ? radar : session.merge(radar));
    }

}
