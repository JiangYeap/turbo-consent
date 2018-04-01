package com.turboconsulting.DAO;

import com.turboconsulting.Entity.VisitorExperiment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("sqlVisitorExperimentData")
public interface VisitorExperimentDao extends CrudRepository<VisitorExperiment, Integer>{

    

}
