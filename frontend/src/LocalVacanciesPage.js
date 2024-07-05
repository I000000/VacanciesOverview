import React, { useState, useEffect } from 'react';
import axios from 'axios';
import qs from 'qs';

const LocalVacanciesPage = () => {
  const [vacancies, setVacancies] = useState([]);
  const [searchParams, setSearchParams] = useState({
    name: '',
    area: '',
    experience: '',
    salary: '',
  });

  useEffect(() => {
    const fetchVacancies = async () => {
      try {
        const response = await axios.get('http://localhost:8090/local/vacancies');
        setVacancies(response.data);
      } catch (error) {
        console.error('Error fetching vacancies:', error);
      }
    };
    fetchVacancies();
  }, []);

  const handleSearch = async () => {
    try {
      const config = {
        params: searchParams,
        paramsSerializer: (params) => {
          return qs.stringify(params, { encode: false });
        },
      };
      const response = await axios.get('http://localhost:8090/local/vacancies/search', config);
      setVacancies(response.data);
    } catch (error) {
      console.error('Error searching vacancies:', error);
    }
  };

  const handleInputChange = (e) => {
    setSearchParams({
      ...searchParams,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div>
      <div className='padding__height'>
        <h2>Локальные вакансии из базы данных</h2>
      </div>
      <div className='padding__height'>
        <p style={{ fontStyle: 'italic' }}>(Заполнять все поля необязательно)</p>
      </div>
      <div className='padding__height'>
        <label htmlFor="name">Профессия: </label>
        <input
          type="text"
          id="name"
          name="name"
          value={searchParams.name}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="area">Местоположение: </label>
        <input
          type="text"
          id="area"
          name="area"
          value={searchParams.area}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="experience">Опыт работы: </label>
        <input
          type="text"
          id="experience"
          name="experience"
          value={searchParams.experience}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="salary">Минимальная зп: </label>
        <input
          type="number"
          id="salary"
          name="salary"
          value={searchParams.salary}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="salaryCurrency">Валюта: </label>
        <input
          type="text"
          id="currency"
          name="currency"
          value={searchParams.salaryCurrency}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <button onClick={handleSearch} className='btn'>Поиск</button>
      </div>
      <div className='padding__height'>
        <h2>Доступные вакансии:</h2>
      </div>
      <ul>
        {vacancies.map((vacancy) => (
          <div className='contact__item'>
            <li key={vacancy.id}>
              <div className='contact__details'>
                <h4>{vacancy.name}</h4>
                <p><i class="bi bi-geo-alt"></i> {vacancy.area}</p>
                <p><i class="bi bi-cash"></i> 
                  {vacancy.salaryFrom === 0 
                  ? (vacancy.salaryTo > 0 ? ` ${vacancy.salaryTo} ${vacancy.salaryCurrency}` : ` ??? ${vacancy.salaryCurrency}`) 
                  : ` ${vacancy.salaryFrom} ${vacancy.salaryTo > 0 ? ` — ${vacancy.salaryTo} ${vacancy.salaryCurrency}` : `${vacancy.salaryCurrency}` }`}
                </p>
                <p><i class="bi bi-hourglass-split"></i> {vacancy.experience}</p>
              </div>
            </li>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default LocalVacanciesPage;