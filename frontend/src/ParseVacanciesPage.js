import React, { useState } from 'react';
import axios from 'axios';
import qs from 'qs';

const ParseVacanciesPage = () => {
  const [parseParams, setParseParams] = useState({
    per_page: 10,
    text: '',
    area: '',
    metro: '',
  })
  const [vacancies, setVacancies] = useState([]);

  const handleParseVacancies = async () => {
    try {
      const config = {
        params: parseParams,
        paramsSerializer: (params) => {
          return qs.stringify(params, { encode: false });
        },
      };
      const response = await axios.get(`http://localhost:8090/parse/vacancies`, config);
      setVacancies(response.data);
    } catch (error) {
      console.error('Error parsing vacancies:', error);
    }
  };

  const handleInputChange = (e) => {
    setParseParams({
      ...parseParams,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div>
      <div className='padding__height'>
        <h2>Парсинг вакансий:</h2>
      </div>
      <div className='padding__height'>
        <label htmlFor="perPage">Количество вакансий: </label>
        <input
          type="number"
          id="perPage"
          name="perPage"
          value={parseParams.per_page}
          onChange={handleInputChange}
          min="1"
          max="100"
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="text">Профессия: </label>
        <input
          type="text"
          id="text"
          name="text"
          value={parseParams.text}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="area">Местоположение: </label>
        <input
          type="number"
          id="area"
          name="area"
          value={parseParams.area}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <label htmlFor="metro">Метро: </label>
        <input
          type="number"
          id="metro"
          name="metro"
          value={parseParams.metro}
          onChange={handleInputChange}
        />
      </div>
      <div className='padding__height'>
        <button onClick={handleParseVacancies} className='btn'>Парсинг</button>
      </div>
      <div className='padding__height'>
        <h2>Сохраненные вакансии: </h2>
      </div>
      <ul>
        {vacancies.map((vacancy) => (
          <div className='contact__item'>
            <li key={vacancy.id}>
              <div className='contact__details'>
                <h4>{vacancy.name}</h4>
                <p><i class="bi bi-geo-alt"></i> {vacancy.area}</p>
                <p><i class="bi bi-cash"></i> {vacancy.salaryFrom} — {vacancy.salaryTo}</p>
                <p><i class="bi bi-hourglass-split"></i> {vacancy.experience}</p>
              </div>
            </li>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default ParseVacanciesPage;