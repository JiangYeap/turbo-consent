package com.turboconsulting.DAO;

import com.turboconsulting.Entity.Experiment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("sqlExperimentData")
public interface MySqlExperimentDao extends CrudRepository<Experiment, Integer>{
}
