package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class UserPosteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPosteDTO.class);
        UserPosteDTO userPosteDTO1 = new UserPosteDTO();
        userPosteDTO1.setId(1L);
        UserPosteDTO userPosteDTO2 = new UserPosteDTO();
        assertThat(userPosteDTO1).isNotEqualTo(userPosteDTO2);
        userPosteDTO2.setId(userPosteDTO1.getId());
        assertThat(userPosteDTO1).isEqualTo(userPosteDTO2);
        userPosteDTO2.setId(2L);
        assertThat(userPosteDTO1).isNotEqualTo(userPosteDTO2);
        userPosteDTO1.setId(null);
        assertThat(userPosteDTO1).isNotEqualTo(userPosteDTO2);
    }
}
