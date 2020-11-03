package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesMapperTest {
    private NotesMapper notesMapper;

    @BeforeEach
    public void setUp() {
        notesMapper = new NotesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notesMapper.fromId(null)).isNull();
    }
}
