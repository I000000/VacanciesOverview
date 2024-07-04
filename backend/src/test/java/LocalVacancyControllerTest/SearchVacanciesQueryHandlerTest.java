package LocalVacancyControllerTest;

import com.example.demo.Exceptions.IncorrectParameter;
import com.example.demo.QueryHandlers.SearchVacanciesQueryHandler;
import com.example.demo.VacanciesOverviewApplication;
import com.example.demo.Vacancy.Vacancy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = VacanciesOverviewApplication.class)
public class SearchVacanciesQueryHandlerTest {
    @InjectMocks
    private SearchVacanciesQueryHandler searchVacanciesQueryHandler;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Vacancy> query;

    @Mock
    private Root<Vacancy> root;

    @Mock
    private Predicate predicate;

    @Test
    public void testFindVacancyByName() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Тестовая Вакансия");

        Vacancy vacancy = new Vacancy(1, "Тестовая Вакансия", "Москва", "Нет опыта", 50000, 100000);
        List<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(vacancy);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Vacancy.class)).thenReturn(query);
        when(query.from(Vacancy.class)).thenReturn(root);
        when(criteriaBuilder.like(root.get("name"), "%Тестовая Вакансия%")).thenReturn(predicate);
        when(query.where(predicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery(query).getResultList()).thenReturn(vacancies);

        // Act
        ResponseEntity<List<Vacancy>> response = searchVacanciesQueryHandler.execute(input);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Тестовая Вакансия", response.getBody().get(0).getName());
    }

    @Test
    public void testThrowExceptionForInvalidExperience() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("experience", "Опыт 5 лет");

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Vacancy.class)).thenReturn(query);
        when(query.from(Vacancy.class)).thenReturn(root);
        when(criteriaBuilder.like(root.get("experience"), "%Опыт 5 лет%")).thenReturn(predicate);
        when(query.where(predicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(mock(TypedQuery.class));

        // Act and Assert
        Assertions.assertThrows(IncorrectParameter.class, () -> searchVacanciesQueryHandler.execute(input));
    }

    @Test
    public void testThrowExceptionForNegativeSalary() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("salary", -10000);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Vacancy.class)).thenReturn(query);
        when(query.from(Vacancy.class)).thenReturn(root);
        when(criteriaBuilder.like(root.get("salary"), "%-10000%")).thenReturn(predicate);
        when(query.where(predicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(mock(TypedQuery.class));

        // Act and Assert
        Assertions.assertThrows(IncorrectParameter.class, () -> searchVacanciesQueryHandler.execute(input));
    }
}
