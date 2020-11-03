package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttachmentMapperTest {
    private AttachmentMapper attachmentMapper;

    @BeforeEach
    public void setUp() {
        attachmentMapper = new AttachmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attachmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attachmentMapper.fromId(null)).isNull();
    }
}
