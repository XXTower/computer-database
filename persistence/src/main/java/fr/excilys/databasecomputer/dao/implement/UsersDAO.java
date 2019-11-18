package fr.excilys.databasecomputer.dao.implement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.excilys.databasecomputer.entity.Users;

@Repository
public class UsersDAO {

	@PersistenceContext
	private EntityManager em;

	public Users findUser(String name) {
		Users user = null;

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
		Root<Users> root = criteriaQuery.from(Users.class);
		criteriaQuery.select(root).where(builder.like(root.get("login"), name));
		TypedQuery<Users> query = em.createQuery(criteriaQuery);
		user = query.getSingleResult();

		return user;
	}
}
