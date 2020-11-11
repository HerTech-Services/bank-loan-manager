package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TypeEntiteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEntiteDTO.class);
        TypeEntiteDTO typeEntiteDTO1 = new TypeEntiteDTO();
        typeEntiteDTO1.setId(1L);
        TypeEntiteDTO typeEntiteDTO2 = new TypeEntiteDTO();
        assertThat(typeEntiteDTO1).isNotEqualTo(typeEntiteDTO2);
        typeEntiteDTO2.setId(typeEntiteDTO1.getId());
        assertThat(typeEntiteDTO1).isEqualTo(typeEntiteDTO2);
        typeEntiteDTO2.setId(2L);
        assertThat(typeEntiteDTO1).isNotEqualTo(typeEntiteDTO2);
        typeEntiteDTO1.setId(null);
        assertThat(typeEntiteDTO1).isNotEqualTo(typeEntiteDTO2);
    }
}
