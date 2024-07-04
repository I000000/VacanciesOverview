package ParseVacancyControllerTest;

import com.example.demo.Exceptions.IncorrectParameter;
import com.example.demo.JSONParsingAdd.VacancyItem;
import com.example.demo.JSONParsingAdd.VacancyResponse;
import com.example.demo.QueryHandlers.ParseVacanciesQueryHandler;
import com.example.demo.VacanciesOverviewApplication;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = VacanciesOverviewApplication.class)
public class ParseVacanciesQueryHandlerTest {

    @InjectMocks
    private ParseVacanciesQueryHandler parseVacanciesQueryHandler;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void parseVacanciesQueryHandler_validParameters_returnsAndAddsVacancyToTheDatabaseAndOkStatus() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("perPage", 100);

        VacancyResponse vacancyResponse = new VacancyResponse();
        List<VacancyItem> items = new ArrayList<>();
        VacancyItem item = new VacancyItem(1, "Тестовая Вакансия", "Москва", 50000, 100000, "Нет опыта");
        items.add(item);
        vacancyResponse.setItems(items);

        when(restTemplate.getForObject("https://api.hh.ru/vacancies?per_page=100", VacancyResponse.class))
                .thenReturn(vacancyResponse);

        // Act
        ResponseEntity<List<Vacancy>> response = parseVacanciesQueryHandler.execute(input);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getId());
        assertEquals("Тестовая Вакансия", response.getBody().get(0).getName());
        assertEquals("Москва", response.getBody().get(0).getArea());
        assertEquals(50000, response.getBody().get(0).getSalaryFrom());
        assertEquals(100000, response.getBody().get(0).getSalaryTo());
        assertEquals("Нет опыта", response.getBody().get(0).getExperience());

        verify(vacancyRepository, times(1)).save(any(Vacancy.class));
    }

    @Test
    void parseVacanciesQueryHandler_invalidPerPage_throwsIncorrectPerPageException() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("perPage", 1000);

        // Act and Assert
        assertThrows(IncorrectParameter.class, () -> parseVacanciesQueryHandler.execute(input));
    }
}
