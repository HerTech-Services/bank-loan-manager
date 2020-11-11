package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class RejectDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RejectDTO.class);
        RejectDTO rejectDTO1 = new RejectDTO();
        rejectDTO1.setId(1L);
        RejectDTO rejectDTO2 = new RejectDTO();
        assertThat(rejectDTO1).isNotEqualTo(rejectDTO2);
        rejectDTO2.setId(rejectDTO1.getId());
        assertThat(rejectDTO1).isEqualTo(rejectDTO2);
        rejectDTO2.setId(2L);
        assertThat(rejectDTO1).isNotEqualTo(rejectDTO2);
        rejectDTO1.setId(null);
        assertThat(rejectDTO1).isNotEqualTo(rejectDTO2);
    }
}
