package fr.excilys.databasecomputer.restclient;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import fr.excilys.databasecomputer.dtos.CompanyDTO;
import fr.excilys.databasecomputer.dtos.ComputerDTO;

public class RestClient {

	private static final String URL_COMPUTERS = "http://localhost:8080/computer-database-app/computers";
	private static final String URL_COMPANYS = "http://localhost:8080/computer-database-app/companys";
	private Client client = ClientBuilder.newClient();

	public ComputerDTO getComputerById(String id) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		return client.target(URL_COMPUTERS).path(id).register(feature).request(MediaType.APPLICATION_JSON).get()
				.readEntity(ComputerDTO.class);
	}

	public List<ComputerDTO> getAllComputers() {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		return client.target(URL_COMPUTERS).register(feature).request(MediaType.APPLICATION_JSON).get()
				.readEntity(new GenericType<List<ComputerDTO>>() { });
	}

	public boolean create(ComputerDTO computerDTO) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		Response reponse = client.target(URL_COMPUTERS).register(feature).request(MediaType.APPLICATION_JSON).post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
		if (reponse.getStatus() == Status.OK.getStatusCode()) {
			return true;
		}
		return false;
	}

	public List<CompanyDTO> getAllCompanys() {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		return client.target(URL_COMPANYS).register(feature).request(MediaType.APPLICATION_JSON).get()
				.readEntity(new GenericType<List<CompanyDTO>>() { });
	}

	public boolean update(ComputerDTO computerDTO) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		Response reponse = client.target(URL_COMPUTERS).register(feature).request(MediaType.APPLICATION_JSON).put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
		if (reponse.getStatus() == Status.OK.getStatusCode()) {
			return true;
		}
		return false;
	}

	public boolean delete(String id) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.digest("admin", "admin1");
		Response reponse = client.target(URL_COMPUTERS).path(id).register(feature).request(MediaType.APPLICATION_JSON).delete();
		if (reponse.getStatus() == Status.OK.getStatusCode()) {
			return true;
		}
		return false;
	}
}
