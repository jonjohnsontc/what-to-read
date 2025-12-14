package io.github.jonjohnsontc.whattoread.repository;

import io.github.jonjohnsontc.whattoread.model.PaperDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing paper details.
 * This interface extends CRUD operations over the paper_details table.
 */
public interface PaperDetailsQ extends CrudRepository<PaperDetails, String> {
}
