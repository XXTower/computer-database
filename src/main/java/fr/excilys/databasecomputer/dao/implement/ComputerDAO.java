package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.entity.Computer;
import fr.excilys.databasecomputer.exception.FailSaveComputer;
import fr.excilys.databasecomputer.exception.SQLExceptionComputerNotFound;

@Repository
public class ComputerDAO {

	@PersistenceContext
	private EntityManager em;

	public Computer find(int id) throws SQLExceptionComputerNotFound {
		Computer computer = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Computer> criteriaQuery = builder.createQuery(Computer.class);
			Root<Computer> root = criteriaQuery.from(Computer.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get("id"), id));
			TypedQuery<Computer> query = em.createQuery(criteriaQuery);
			computer = query.getSingleResult();
		} catch (NoResultException e) {
			throw new SQLExceptionComputerNotFound("Aucun ordinateur trouver a pour cette id");
		}
		return computer;
	}

	@Transactional
	public boolean update(Computer computer) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaUpdate<Computer> criteriaUpdate = builder.createCriteriaUpdate(Computer.class);
		Root<Computer> root = criteriaUpdate.from(Computer.class);
		criteriaUpdate.set("name", computer.getName()).set("introduced", computer.getIntroduced())
		.set("discontinued", computer.getDiscontinued()).set("company_id", computer.getCompany().getId());
		criteriaUpdate.where(builder.equal(root.get("id"), computer.getId()));
		Query update = em.createQuery(criteriaUpdate);
		int result = update.executeUpdate();
		return result == 1;
	}

	@Transactional
	public boolean delete(int id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = builder.createCriteriaDelete(Computer.class);
		Root<Computer> root = criteriaDelete.from(Computer.class);
		criteriaDelete.where(builder.equal(root.get("id"), id));
		Query computer = em.createQuery(criteriaDelete);
		int result = computer.executeUpdate();
		return result != 0;
	}

	public List<Computer> findAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = builder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);
		TypedQuery<Computer> computers = em.createQuery(criteriaQuery);
		return computers.getResultList();
	}

	@Transactional
	public void addComputer(Computer computer) throws FailSaveComputer {
		try {
			em.persist(computer);
		} catch (Exception e) {
			throw new FailSaveComputer("Errors whith the save");
		}
	}

	public long nbComputer() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(builder.countDistinct(root));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	public List<Computer> findAll(int limite, int offset, String order) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = builder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		if (order.equals("ASC")) {
			criteriaQuery.select(root).orderBy(builder.asc(root.get("name")));
		} else {
			criteriaQuery.select(root).orderBy(builder.desc(root.get("name")));
		}
		TypedQuery<Computer> computers = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limite);
		return computers.getResultList();
	}

	@Transactional
	public boolean deleteComputerByCompanyName(String companyName) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaDelete<Computer> criteriaDelete = builder.createCriteriaDelete(Computer.class);
		Root<Computer> root = criteriaDelete.from(Computer.class);
		Join<Computer, Company> company = root.join("company", JoinType.LEFT);
		criteriaDelete.where(builder.like(company.get("name"), companyName));
		Query computer = em.createQuery(criteriaDelete);
		int result = computer.executeUpdate();
		return result != 0;
	}

	public List<Computer> findComputerByName(String name, int limite, int offset, String order) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = builder.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Join<Computer, Company> company = root.join("company", JoinType.LEFT);
		Predicate nameComputer = builder.like(root.get("name"), "%" + name + "%");
		Predicate nameCompany = builder.like(company.get("name"), "%" + name + "%");
		criteriaQuery.select(root).where(builder.or(nameComputer, nameCompany));
		if (order.equals("ASC")) {
			criteriaQuery.orderBy(builder.asc(root.get("name")));
		} else {
			criteriaQuery.orderBy(builder.desc(root.get("name")));
		}
		TypedQuery<Computer> computers = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limite);
		return computers.getResultList();
	}

	public long nbComputerFindByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Join<Computer, Company> company = root.join("company", JoinType.LEFT);
		Predicate nameComputer = builder.like(root.get("name"), "%" + name + "%");
		Predicate nameCompany = builder.like(company.get("name"), "%" + name + "%");
		criteriaQuery.select(builder.countDistinct(root)).where(builder.or(nameComputer, nameCompany));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
}
