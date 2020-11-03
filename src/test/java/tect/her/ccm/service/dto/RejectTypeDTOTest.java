package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class RejectTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RejectTypeDTO.class);
        RejectTypeDTO rejectTypeDTO1 = new RejectTypeDTO();
        rejectTypeDTO1.setId(1L);
        RejectTypeDTO rejectTypeDTO2 = new RejectTypeDTO();
        assertThat(rejectTypeDTO1).isNotEqualTo(rejectTypeDTO2);
        rejectTypeDTO2.setId(rejectTypeDTO1.getId());
        assertThat(rejectTypeDTO1).isEqualTo(rejectTypeDTO2);
        rejectTypeDTO2.setId(2L);
        assertThat(rejectTypeDTO1).isNotEqualTo(rejectTypeDTO2);
        rejectTypeDTO1.setId(null);
        assertThat(rejectTypeDTO1).isNotEqualTo(rejectTypeDTO2);
    }
}
