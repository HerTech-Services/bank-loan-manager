package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class FilelistEgagementTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilelistEgagementTypeDTO.class);
        FilelistEgagementTypeDTO filelistEgagementTypeDTO1 = new FilelistEgagementTypeDTO();
        filelistEgagementTypeDTO1.setId(1L);
        FilelistEgagementTypeDTO filelistEgagementTypeDTO2 = new FilelistEgagementTypeDTO();
        assertThat(filelistEgagementTypeDTO1).isNotEqualTo(filelistEgagementTypeDTO2);
        filelistEgagementTypeDTO2.setId(filelistEgagementTypeDTO1.getId());
        assertThat(filelistEgagementTypeDTO1).isEqualTo(filelistEgagementTypeDTO2);
        filelistEgagementTypeDTO2.setId(2L);
        assertThat(filelistEgagementTypeDTO1).isNotEqualTo(filelistEgagementTypeDTO2);
        filelistEgagementTypeDTO1.setId(null);
        assertThat(filelistEgagementTypeDTO1).isNotEqualTo(filelistEgagementTypeDTO2);
    }
}
