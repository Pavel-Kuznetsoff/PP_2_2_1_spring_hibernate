package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String carModel, int carSeries) {
       User user = new User();
       Query query = sessionFactory.getCurrentSession()
               .createQuery("from User as u inner join fetch u.car as c  " +
                       "where c.model=:model and c.series=:series");
       query.setParameter("model", carModel);
       query.setParameter("series", carSeries);

       return (User) query.getSingleResult();
   }
}
