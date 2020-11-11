package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class FilelistDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilelistDTO.class);
        FilelistDTO filelistDTO1 = new FilelistDTO();
        filelistDTO1.setId(1L);
        FilelistDTO filelistDTO2 = new FilelistDTO();
        assertThat(filelistDTO1).isNotEqualTo(filelistDTO2);
        filelistDTO2.setId(filelistDTO1.getId());
        assertThat(filelistDTO1).isEqualTo(filelistDTO2);
        filelistDTO2.setId(2L);
        assertThat(filelistDTO1).isNotEqualTo(filelistDTO2);
        filelistDTO1.setId(null);
        assertThat(filelistDTO1).isNotEqualTo(filelistDTO2);
    }
}
