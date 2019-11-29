package fr.excilys.databasecomputer.dao.implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.databasecomputer.entity.Company;
import fr.excilys.databasecomputer.exception.FailSaveComputer;

@Repository
public class CompanyDAO {

	@PersistenceContext
	private EntityManager em;

	public List<Company> findAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		TypedQuery<Company> companys = em.createQuery(criteriaQuery);
		return companys.getResultList();
	}

	public List<Company> findAll(int limite, int offset) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		TypedQuery<Company> companys = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limite);
		return companys.getResultList();
	}

	public long nbCompany() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(builder.countDistinct(root));
		TypedQuery<Long> query = em.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	@Transactional
	public boolean deleteCompany(String companyName) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaDelete<Company> criteriaDelete = builder.createCriteriaDelete(Company.class);
		Root<Company> root = criteriaDelete.from(Company.class);
		criteriaDelete.where(builder.like(root.get("name"), companyName));
		Query computer = em.createQuery(criteriaDelete);
		int result = computer.executeUpdate();
		return result != 0;
	}

	@Transactional
	public void addCompany(Company company) throws FailSaveComputer {
		try {
			em.persist(company);
		} catch (Exception e) {
			throw new FailSaveComputer("Errors whith the save");
		}
	}

	@Transactional
	public void update(Company company) throws FailSaveComputer {
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaUpdate<Company> criteriaUpdate = builder.createCriteriaUpdate(Company.class);
			Root<Company> root = criteriaUpdate.from(Company.class);
			criteriaUpdate.set("name", company.getName());
			criteriaUpdate.where(builder.equal(root.get("id"), company.getId()));
			Query update = em.createQuery(criteriaUpdate);
			update.executeUpdate();
		} catch (Exception e) {
			throw new FailSaveComputer("Errors whith the save");
		}
	}
}
