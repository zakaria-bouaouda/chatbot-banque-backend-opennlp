package emi.ginfo.iql.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import emi.ginfo.iql.model.reponse;

@Repository
public interface reponseRepository extends MongoRepository<reponse,String>{

}
