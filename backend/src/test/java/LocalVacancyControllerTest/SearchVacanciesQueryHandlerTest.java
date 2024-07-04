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

import java.util.*;

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
    public void searchVacanciesQueryHandler_nameIsGiven_returnsVacancyWithTheProvidedName() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Тестовая Вакансия");

        Vacancy vacancy = new Vacancy(1, "Тестовая Вакансия", "Москва", "Нет опыта", 50000, 100000, "RUR");
        Vacancy vacancyWithIncorrectName = new Vacancy(2, "Другая вакансия", "Москва", "Нет опыта", 50000, 100000, "RUR");
        List<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(vacancy);
        vacancies.add(vacancyWithIncorrectName);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Vacancy.class)).thenReturn(query);
        when(query.from(Vacancy.class)).thenReturn(root);

        when(criteriaBuilder.equal(root.get("name"), "Тестовая Вакансия")).thenReturn(predicate);

        when(query.where(predicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery(query).getResultList()).thenReturn(Collections.singletonList(vacancy));

        // Act
        ResponseEntity<List<Vacancy>> response = searchVacanciesQueryHandler.execute(input);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Тестовая Вакансия", response.getBody().get(0).getName());
    }

    @Test
    public void searchVacanciesQueryHandler_multipleParametersAreGiven_returnsVacancyWithTheMatchingParameters() {
        // Arrange
        Map<String, Object> input = new HashMap<>();
        input.put("area", "Москва");
        input.put("experience", "Более 6 лет");

        Vacancy vacancyWithIncorrectExp = new Vacancy(1, "Тестовая Вакансия", "Москва", "Нет опыта", 50000, 100000, "RUR");
        Vacancy vacancy = new Vacancy(2, "Другая вакансия", "Москва", "Более 6 лет", 50000, 100000, "RUR");
        List<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(vacancy);
        vacancies.add(vacancyWithIncorrectExp);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Vacancy.class)).thenReturn(query);
        when(query.from(Vacancy.class)).thenReturn(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("area"), input.get("area")));
        predicates.add(criteriaBuilder.equal(root.get("experience"), input.get("experience")));

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        when(finalPredicate).thenReturn(predicate);

        when(query.where(predicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery(query).getResultList()).thenReturn(Collections.singletonList(vacancy));

        // Act
        ResponseEntity<List<Vacancy>> response = searchVacanciesQueryHandler.execute(input);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Москва", response.getBody().get(0).getArea());
        assertEquals("Более 6 лет", response.getBody().get(0).getExperience());
    }


    @Test
    public void searchVacanciesQueryHandler_invalidExperienceIsGiven_throwsExceptionForInvalidExperience() {
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
    public void searchVacanciesQueryHandler_negativeSalaryIsGiven_throwsExceptionForNegativeSalary() {
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
