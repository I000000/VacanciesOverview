import React, { useState } from 'react';
import axios from 'axios';

const ParseVacanciesPage = () => {
  const [perPage, setPerPage] = useState(10);
  const [vacancies, setVacancies] = useState([]);

  const handleParseVacancies = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/parse/vacancies/${perPage}`);
      setVacancies(response.data);
    } catch (error) {
      console.error('Error parsing vacancies:', error);
    }
  };

  return (
    <div>
      <h1>Parse Vacancies</h1>
      <div>
        <label htmlFor="perPage">Per Page: </label>
        <input
          type="number"
          id="perPage"
          value={perPage}
          onChange={(e) => setPerPage(e.target.value)}
          min="1"
          max="100"
        />
        <button onClick={handleParseVacancies}>Parse</button>
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

export default ParseVacanciesPage;