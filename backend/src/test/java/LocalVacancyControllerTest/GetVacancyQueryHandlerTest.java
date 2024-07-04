package LocalVacancyControllerTest;

import com.example.demo.Exceptions.VacancyNotFound;
import com.example.demo.QueryHandlers.GetVacancyQueryHandler;
import com.example.demo.VacanciesOverviewApplication;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = VacanciesOverviewApplication.class)
public class GetVacancyQueryHandlerTest {

    @InjectMocks
    private GetVacancyQueryHandler getVacancyQueryHandler;

    @Mock
    private VacancyRepository vacancyRepository;

    @Test
    public void getVacancyQueryHandlerTest_validId_returnsVacancy(){
        // Arrange
        Vacancy vacancy = new Vacancy(1, "Тестовая Вакансия", "Москва", "Нет опыта", 50000, 100000);
        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));

        // Act
        ResponseEntity<Vacancy> response = getVacancyQueryHandler.execute(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vacancy, response.getBody());
    }

    @Test
    public void getVacancyQueryHandlerTest_invalidId_throwsVacancyNotFoundException(){
        when(vacancyRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(VacancyNotFound.class, () -> {
            getVacancyQueryHandler.execute(10L);
        });
    }
}
