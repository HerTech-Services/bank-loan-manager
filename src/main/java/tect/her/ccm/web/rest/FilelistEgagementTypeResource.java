package tect.her.ccm.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tect.her.ccm.service.FilelistEgagementTypeService;
import tect.her.ccm.service.dto.FilelistEgagementTypeDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.FilelistEgagementType}.
 */
@RestController
@RequestMapping("/api")
public class FilelistEgagementTypeResource {
    private final Logger log = LoggerFactory.getLogger(FilelistEgagementTypeResource.class);

    private static final String ENTITY_NAME = "filelistEgagementType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilelistEgagementTypeService filelistEgagementTypeService;

    public FilelistEgagementTypeResource(FilelistEgagementTypeService filelistEgagementTypeService) {
        this.filelistEgagementTypeService = filelistEgagementTypeService;
    }

    /**
     * {@code POST  /filelist-egagement-types} : Create a new filelistEgagementType.
     *
     * @param filelistEgagementTypeDTO the filelistEgagementTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filelistEgagementTypeDTO, or with status {@code 400 (Bad Request)} if the filelistEgagementType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filelist-egagement-types")
    public ResponseEntity<FilelistEgagementTypeDTO> createFilelistEgagementType(
        @RequestBody FilelistEgagementTypeDTO filelistEgagementTypeDTO
    )
        throws URISyntaxException {
        log.debug("REST request to save FilelistEgagementType : {}", filelistEgagementTypeDTO);
        if (filelistEgagementTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new filelistEgagementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilelistEgagementTypeDTO result = filelistEgagementTypeService.save(filelistEgagementTypeDTO);
        return ResponseEntity
            .created(new URI("/api/filelist-egagement-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filelist-egagement-types} : Updates an existing filelistEgagementType.
     *
     * @param filelistEgagementTypeDTO the filelistEgagementTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filelistEgagementTypeDTO,
     * or with status {@code 400 (Bad Request)} if the filelistEgagementTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filelistEgagementTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filelist-egagement-types")
    public ResponseEntity<FilelistEgagementTypeDTO> updateFilelistEgagementType(
        @RequestBody FilelistEgagementTypeDTO filelistEgagementTypeDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update FilelistEgagementType : {}", filelistEgagementTypeDTO);
        if (filelistEgagementTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FilelistEgagementTypeDTO result = filelistEgagementTypeService.save(filelistEgagementTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filelistEgagementTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /filelist-egagement-types} : get all the filelistEgagementTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filelistEgagementTypes in body.
     */
    @GetMapping("/filelist-egagement-types")
    public ResponseEntity<List<FilelistEgagementTypeDTO>> getAllFilelistEgagementTypes(Pageable pageable) {
        log.debug("REST request to get a page of FilelistEgagementTypes");
        Page<FilelistEgagementTypeDTO> page = filelistEgagementTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /filelist-egagement-types/:id} : get the "id" filelistEgagementType.
     *
     * @param id the id of the filelistEgagementTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filelistEgagementTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filelist-egagement-types/{id}")
    public ResponseEntity<FilelistEgagementTypeDTO> getFilelistEgagementType(@PathVariable Long id) {
        log.debug("REST request to get FilelistEgagementType : {}", id);
        Optional<FilelistEgagementTypeDTO> filelistEgagementTypeDTO = filelistEgagementTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filelistEgagementTypeDTO);
    }

    /**
     * {@code DELETE  /filelist-egagement-types/:id} : delete the "id" filelistEgagementType.
     *
     * @param id the id of the filelistEgagementTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filelist-egagement-types/{id}")
    public ResponseEntity<Void> deleteFilelistEgagementType(@PathVariable Long id) {
        log.debug("REST request to delete FilelistEgagementType : {}", id);
        filelistEgagementTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
