package emi.ginfo.iql.Repository;
import org.springframework.stereotype.Repository;

import emi.ginfo.iql.model.historique;

import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface historiqueRepository extends MongoRepository<historique,Long>{
	
}
