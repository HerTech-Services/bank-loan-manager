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
import tect.her.ccm.service.NotesService;
import tect.her.ccm.service.dto.NotesDTO;
import tect.her.ccm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tect.her.ccm.domain.Notes}.
 */
@RestController
@RequestMapping("/api")
public class NotesResource {
    private final Logger log = LoggerFactory.getLogger(NotesResource.class);

    private static final String ENTITY_NAME = "notes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotesService notesService;

    public NotesResource(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     * {@code POST  /notes} : Create a new notes.
     *
     * @param notesDTO the notesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notesDTO, or with status {@code 400 (Bad Request)} if the notes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notes")
    public ResponseEntity<NotesDTO> createNotes(@Valid @RequestBody NotesDTO notesDTO) throws URISyntaxException {
        log.debug("REST request to save Notes : {}", notesDTO);
        if (notesDTO.getId() != null) {
            throw new BadRequestAlertException("A new notes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotesDTO result = notesService.save(notesDTO);
        return ResponseEntity
            .created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notes} : Updates an existing notes.
     *
     * @param notesDTO the notesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notesDTO,
     * or with status {@code 400 (Bad Request)} if the notesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notes")
    public ResponseEntity<NotesDTO> updateNotes(@Valid @RequestBody NotesDTO notesDTO) throws URISyntaxException {
        log.debug("REST request to update Notes : {}", notesDTO);
        if (notesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotesDTO result = notesService.save(notesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notes} : get all the notes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notes in body.
     */
    @GetMapping("/notes")
    public ResponseEntity<List<NotesDTO>> getAllNotes(Pageable pageable) {
        log.debug("REST request to get a page of Notes");
        Page<NotesDTO> page = notesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notes/:id} : get the "id" notes.
     *
     * @param id the id of the notesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<NotesDTO> getNotes(@PathVariable Long id) {
        log.debug("REST request to get Notes : {}", id);
        Optional<NotesDTO> notesDTO = notesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notesDTO);
    }

    /**
     * {@code DELETE  /notes/:id} : delete the "id" notes.
     *
     * @param id the id of the notesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id) {
        log.debug("REST request to delete Notes : {}", id);
        notesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
