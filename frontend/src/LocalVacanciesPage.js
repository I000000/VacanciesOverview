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
        const response = await axios.get('http://localhost:8080/local/vacancies');
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
      const response = await axios.get('http://localhost:8080/local/vacancies/search', config);
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
      <h1>Local Vacancies</h1>
      <div>
        <label htmlFor="name">Name:</label>
        <input
          type="text"
          id="name"
          name="name"
          value={searchParams.name}
          onChange={handleInputChange}
        />
        <label htmlFor="area">Area:</label>
        <input
          type="text"
          id="area"
          name="area"
          value={searchParams.area}
          onChange={handleInputChange}
        />
        <label htmlFor="experience">Experience:</label>
        <input
          type="text"
          id="experience"
          name="experience"
          value={searchParams.experience}
          onChange={handleInputChange}
        />
        <label htmlFor="salary">Salary:</label>
        <input
          type="number"
          id="salary"
          name="salary"
          value={searchParams.salary}
          onChange={handleInputChange}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      <h2>Vacancies</h2>
      <ul>
        {vacancies.map((vacancy) => (
          <li key={vacancy.id}>
            <h3>{vacancy.name}</h3>
            <p>Area: {vacancy.area}</p>
            <p>Salary: {vacancy.salaryFrom} - {vacancy.salaryTo}</p>
            <p>Experience: {vacancy.experience}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default LocalVacanciesPage;