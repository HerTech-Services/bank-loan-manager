package tect.her.ccm.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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
import tect.her.ccm.service.FilelistService;
import tect.her.ccm.service.dto.FilelistDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Filelist}.
 */
@RestController
@RequestMapping("/api")
public class FilelistResource {
    private final Logger log = LoggerFactory.getLogger(FilelistResource.class);

    private static final String ENTITY_NAME = "filelist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilelistService filelistService;

    public FilelistResource(FilelistService filelistService) {
        this.filelistService = filelistService;
    }

    /**
     * {@code POST  /filelists} : Create a new filelist.
     *
     * @param filelistDTO the filelistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filelistDTO, or with status {@code 400 (Bad Request)} if the filelist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filelists")
    public ResponseEntity<FilelistDTO> createFilelist(@Valid @RequestBody FilelistDTO filelistDTO) throws URISyntaxException {
        log.debug("REST request to save Filelist : {}", filelistDTO);
        if (filelistDTO.getId() != null) {
            throw new BadRequestAlertException("A new filelist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilelistDTO result = filelistService.save(filelistDTO);
        return ResponseEntity
            .created(new URI("/api/filelists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filelists} : Updates an existing filelist.
     *
     * @param filelistDTO the filelistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filelistDTO,
     * or with status {@code 400 (Bad Request)} if the filelistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filelistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filelists")
    public ResponseEntity<FilelistDTO> updateFilelist(@Valid @RequestBody FilelistDTO filelistDTO) throws URISyntaxException {
        log.debug("REST request to update Filelist : {}", filelistDTO);
        if (filelistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FilelistDTO result = filelistService.save(filelistDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filelistDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /filelists} : get all the filelists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filelists in body.
     */
    @GetMapping("/filelists")
    public ResponseEntity<List<FilelistDTO>> getAllFilelists(Pageable pageable) {
        log.debug("REST request to get a page of Filelists");
        Page<FilelistDTO> page = filelistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /filelists/:id} : get the "id" filelist.
     *
     * @param id the id of the filelistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filelistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filelists/{id}")
    public ResponseEntity<FilelistDTO> getFilelist(@PathVariable Long id) {
        log.debug("REST request to get Filelist : {}", id);
        Optional<FilelistDTO> filelistDTO = filelistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filelistDTO);
    }

    /**
     * {@code DELETE  /filelists/:id} : delete the "id" filelist.
     *
     * @param id the id of the filelistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filelists/{id}")
    public ResponseEntity<Void> deleteFilelist(@PathVariable Long id) {
        log.debug("REST request to delete Filelist : {}", id);
        filelistService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
